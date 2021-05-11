package com.greengiant.website.resolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.greengiant.website.annotation.MultiRequestBody;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultiRequestBodyResolver implements HandlerMethodArgumentResolver {
    private static final String JSONBODY_ATTRIBUTE = "JSON_REQUEST_BODY";

    public MultiRequestBodyResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MultiRequestBody.class);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String jsonBody = this.getRequestBody(webRequest);
        JSONObject jsonObject = JSON.parseObject(jsonBody);
        MultiRequestBody parameterAnnotation = (MultiRequestBody)parameter.getParameterAnnotation(MultiRequestBody.class);
        String key = parameterAnnotation.value();
        Object value = null;
        if (jsonObject != null) {
//            if (StringUtils.isNotEmpty(key)) {
            if (key != null && !key.isEmpty()) {
                value = jsonObject.get(key);
                if (value == null && parameterAnnotation.required()) {
                    throw new IllegalArgumentException(String.format("required param %s is not present", key));
                }
            } else {
                key = parameter.getParameterName();
                value = jsonObject.get(key);
            }
        }

        Class<?> parameterType = parameter.getParameterType();
        int i;
        if (value != null) {
            if (parameterType.isPrimitive()) {
                return this.parsePrimitive(parameterType.getName(), value);
            } else if (this.isBasicDataTypes(parameterType)) {
                return this.parseBasicTypeWrapper(parameterType, value);
            } else if (parameterType == String.class) {
                return value.toString();
            } else if (parameterType != List.class) {
                return JSON.parseObject(value.toString(), parameterType);
            } else {
                String typeName = parameter.getGenericParameterType().getTypeName();
                String className = typeName.substring(typeName.indexOf("<") + 1, typeName.lastIndexOf(">"));
                Class<?> clazz = Class.forName(className);
                List parseList = new ArrayList();
                List<?> list = (List)JSON.parseObject(value.toString(), List.class);

                for(i = 0; i < list.size(); ++i) {
                    Object obj = list.get(i);
                    String objStr = obj.toString();
                    parseList.add(JSON.parseObject(objStr, clazz));
                }

                return parseList;
            }
        } else if (this.isBasicDataTypes(parameterType)) {
            if (parameterAnnotation.required()) {
                throw new IllegalArgumentException(String.format("required param %s is not present", key));
            } else {
                return null;
            }
        } else if (!parameterAnnotation.parseAllFields()) {
            if (parameterAnnotation.required()) {
                throw new IllegalArgumentException(String.format("required param %s is not present", key));
            } else {
                return null;
            }
        } else {
            Object result;
            try {
                result = JSON.parseObject(jsonObject.toString(), parameterType);
            } catch (JSONException var19) {
                result = null;
            }

            if (!parameterAnnotation.required()) {
                return result;
            } else {
                boolean haveValue = false;
                Field[] declaredFields = parameterType.getDeclaredFields();
                Field[] var14 = declaredFields;
                int var15 = declaredFields.length;

                for(i = 0; i < var15; ++i) {
                    Field field = var14[i];
                    field.setAccessible(true);
                    if (field.get(result) != null) {
                        haveValue = true;
                        break;
                    }
                }

                if (!haveValue) {
                    throw new IllegalArgumentException(String.format("required param %s is not present", key));
                } else {
                    return result;
                }
            }
        }
    }

    private Object parsePrimitive(String parameterTypeName, Object value) {
        String booleanTypeName = "boolean";
        if ("boolean".equals(parameterTypeName)) {
            return Boolean.valueOf(value.toString());
        } else {
            String intTypeName = "int";
            if ("int".equals(parameterTypeName)) {
                return Integer.valueOf(value.toString());
            } else {
                String charTypeName = "char";
                if ("char".equals(parameterTypeName)) {
                    return value.toString().charAt(0);
                } else {
                    String shortTypeName = "short";
                    if ("short".equals(parameterTypeName)) {
                        return Short.valueOf(value.toString());
                    } else {
                        String longTypeName = "long";
                        if ("long".equals(parameterTypeName)) {
                            return Long.valueOf(value.toString());
                        } else {
                            String floatTypeName = "float";
                            if ("float".equals(parameterTypeName)) {
                                return Float.valueOf(value.toString());
                            } else {
                                String doubleTypeName = "double";
                                if ("double".equals(parameterTypeName)) {
                                    return Double.valueOf(value.toString());
                                } else {
                                    String byteTypeName = "byte";
                                    return "byte".equals(parameterTypeName) ? Byte.valueOf(value.toString()) : null;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Object parseBasicTypeWrapper(Class<?> parameterType, Object value) {
        if (Number.class.isAssignableFrom(parameterType)) {
            Number number = (Number)value;
            if (parameterType == Integer.class) {
                return number.intValue();
            }

            if (parameterType == Short.class) {
                return number.shortValue();
            }

            if (parameterType == Long.class) {
                return number.longValue();
            }

            if (parameterType == Float.class) {
                return number.floatValue();
            }

            if (parameterType == Double.class) {
                return number.doubleValue();
            }

            if (parameterType == Byte.class) {
                return number.byteValue();
            }
        } else {
            if (parameterType == Boolean.class) {
                return value.toString();
            }

            if (parameterType == Character.class) {
                return value.toString().charAt(0);
            }
        }

        return null;
    }

    private boolean isBasicDataTypes(Class clazz) {
        Set<Class> classSet = new HashSet();
        classSet.add(Integer.class);
        classSet.add(Long.class);
        classSet.add(Short.class);
        classSet.add(Float.class);
        classSet.add(Double.class);
        classSet.add(Boolean.class);
        classSet.add(Byte.class);
        classSet.add(Character.class);
        return classSet.contains(clazz);
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = (HttpServletRequest)webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = (String)webRequest.getAttribute("JSON_REQUEST_BODY", 0);
        if (jsonBody == null) {
            try {
                jsonBody = IOUtils.toString(servletRequest.getReader());
                webRequest.setAttribute("JSON_REQUEST_BODY", jsonBody, 0);
            } catch (IOException var5) {
                throw new RuntimeException(var5);
            }
        }

        return jsonBody;
    }
}