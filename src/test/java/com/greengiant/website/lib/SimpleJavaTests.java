package com.greengiant.website.lib;

import com.google.common.collect.Lists;
import com.greengiant.infrastructure.utils.ZipUtil;
import com.greengiant.website.model.Student;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class SimpleJavaTests {

    @Test
    public void testDate() {
        Date dt1 = new Date();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date dt2 = new Date();
        int compareTo = dt1.compareTo(dt2);
        System.out.println(compareTo);
        System.out.println(dt1.before(dt2));
    }

    @Test
    public void testDateStrToMills() throws ParseException {
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2008-07-10 19:20:00");
        long tm = date.getTime();
    }

    @Test
    public void beanToMap() {
        Student stu = new Student();
        stu.setName("li");
        stu.setAge(18);
        stu.setSex(true);
        Map<String, Object> mp = this.beanToMap(stu);
    }

    private <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();

        Class<?> cls = bean.getClass();
        // getDeclaredFields()返回Class中所有的字段，包括私有字段；getFields()只返回公有字段
        Field[] allFields = cls.getDeclaredFields();
        if (allFields.length > 0) {
            for (Field field : allFields) {
                field.setAccessible(true);
                try {
                    map.put(field.getName(), field.get(bean));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return map;
    }

    @Test
    public void testMapToBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "aaa");
        map.put("age", 2);
        map.put("sex", true);

        Student stu = new Student();
        stu = this.mapToBean(map, stu);
        System.out.println(stu == null);
    }

    private <T> T mapToBean(Map<String, Object> map, T bean) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Class<?> cls = bean.getClass();
        Field[] allFields = cls.getDeclaredFields();
        if (allFields.length > 0) {
            for (Field field : allFields) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if (entry.getKey().equals(field.getName())) {
                        // 设置bean的字段
                        boolean isAccess = field.canAccess(bean);
                        field.setAccessible(true);
                        try {
                            field.set(bean, entry.getValue());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        field.setAccessible(isAccess);
                    }
                }
            }
        }

        return bean;
    }

    @Test
    public void testMyZip() throws IOException {
        ZipUtil.zipDirectory("d:\\ziptest", "d:\\a.zip");
    }


    class User{
        private String addr;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }

    @Test
    public void testFlatMap() {
        List<User> uList = Lists.newArrayList();
        User u1 = new User();
        u1.setAddr("a1;a2;a3;a4;a5");

        User u2 = new User();
        u2.setAddr("b1;b2;b3;b4;b5");

        uList.add(u1);
        uList.add(u2);

        List<String> addrList = uList.stream().map(User::getAddr)
                .flatMap(x->Arrays.stream(x.split(";")))
                .collect(Collectors.toList());
        //或者
        List<String> ridStrList = uList.stream().map(User::getAddr).map(x -> x.split(";"))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        System.out.println(addrList);
        System.out.println(ridStrList);
    }

    @Test
    public void testNullPointer() {
        Integer i = null;
        System.out.println(i.toString());
    }

}