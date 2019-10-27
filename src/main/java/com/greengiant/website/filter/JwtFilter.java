package com.greengiant.website.filter;

import com.greengiant.website.shiro.JWTToken;
import com.greengiant.website.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class JwtFilter extends BasicHttpAuthenticationFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    private static final String AUTHZ_HEADER = "x-auth-token";
    private static final String CHARSET = "UTF-8";

    /**
     * 处理未经验证的请求
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean loggedIn = false;
        if (this.isLoginAttempt(request, response)) {
            loggedIn = this.executeLogin(request, response);
        }

        if (!loggedIn) {
            this.sendChallenge(request, response);
        }

        return loggedIn;
    }

    /**
     * 请求是否已经登录（携带token）
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String token = WebUtils.toHttp(request).getHeader(AUTHZ_HEADER);
        return token != null;
    }

    /**
     * 执行登录方法(由自定义realm判断,吃掉异常返回false)
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String token = WebUtils.toHttp(request).getHeader(AUTHZ_HEADER);
        if (null == token) {
            String msg = "executeLogin method token must not be null";
            throw new IllegalStateException(msg);
        }
        //交给realm判断是否有权限,没权限返回false交给onAccessDenied
        JWTToken jwtToken = new JWTToken(token);
        try {
            this.getSubject(request, response).login(jwtToken);

            //todo 1.onSuccess为啥不通？2.放这里不好吧，如何模块化？

            boolean shouldRefresh = JWTUtil.getExpiresAt(token).before(new Date());
            if (shouldRefresh) {
                //生成新的TOKEN
                //SecurityUser user = (SecurityUser) subject.getPrincipal();
                //String newToken = JWTUtil.sign(user.getUserInfo().getId());

                String newToken = JWTUtil.createToken(JWTUtil.getUsername(token), JWTUtil.getUsername(token));
                HttpServletResponse httpResponse = WebUtils.toHttp(response);
                httpResponse.setHeader("x-auth-token", newToken);
            }

            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    /**
     * 构建未授权的请求返回,filter层的异常不受exceptionAdvice控制,这里返回401,把返回的json丢到response中
     */
    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String contentType = "application/json;charset=" + CHARSET;
//        httpResponse.setStatus(Code.UNAUTHENTICATED);
        httpResponse.setStatus(401);
        httpResponse.setContentType(contentType);
        try {
            String msg = "对不起,您无权限进行操作!";
//            RestResponse unauthentication = RestResponse.newBuilder()
//                    .setCode(401)
//                    .setMsg(msg).build();
            PrintWriter printWriter = httpResponse.getWriter();
//            printWriter.append(JSON.toJSONString(unauthentication));
            printWriter.append("token无效！");
        } catch (IOException e) {
            LOGGER.error("sendChallenge error,can not resolve httpServletResponse");
        }

        return false;
    }

    /**
     * 请求前处理,处理跨域
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse
                .setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时,option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);

        // && "jwt".equalsIgnoreCase(((UserLoginToken) token).getLoginType())
        if (token instanceof JWTToken) {
            JWTToken jwtToken = (JWTToken) token;
            boolean shouldRefresh = JWTUtil.getExpiresAt(jwtToken.getToken()).after(new Date());
            if (shouldRefresh) {
                //生成新的TOKEN
                //SecurityUser user = (SecurityUser) subject.getPrincipal();
                //String newToken = JWTUtil.sign(user.getUserInfo().getId());

                String newToken = JWTUtil.createToken(JWTUtil.getUsername(((JWTToken) token).getToken()), JWTUtil.getUsername(((JWTToken) token).getToken()));
                httpResponse.setHeader("x-auth-token", newToken);
            }
        }

        return true;
    }

//    boolean should refresh() {
//
//    }

}
