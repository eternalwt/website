package com.greengiant.website;

import com.google.common.collect.Lists;
import com.greengiant.website.model.Student;
import com.greengiant.website.utils.ZipUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class SimpleJavaTest {

    // todo 开始在我的代码中引入guava

    @Test
    public void testInteger() {
        Integer aaa = 1;
        Assert.assertTrue(aaa == 1);
    }

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
    public void beanToMap() {
        // todo
        Student stu = new Student();
        stu.setName("li");
        stu.setAge(18);
        stu.setSex(true);
        this.beanToMap(stu);
    }

    private <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();

        Class<T> cls = (Class<T>) bean.getClass();// todo Class<T> 去掉再debug一下
        Field[] allFields = cls.getDeclaredFields();// todo getFields为什么不行？【看源码】
        if (allFields != null && allFields.length > 0) {
            for (int i = 0; i < allFields.length; i++) {
                allFields[i].setAccessible(true);
                try {
                    map.put(allFields[i].getName(), allFields[i].get(bean));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        return map;
    }

    @Test
    public void testMapToBean() {
        // todo 0.考虑泛型相关的问题；1.再看bean的定义；2.异常处理；3.
        Map<String, Object> map = new HashMap<>();
        map.put("name", "aaa");
        map.put("age", 2);
        map.put("sex", true);

        Student stu = new Student();
        stu = this.mapToBean(map, stu);
    }

    private <T> T mapToBean(Map<String, Object> map, T bean) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        // todo 0.考虑泛型相关的问题；1.再看bean的定义；2.异常处理；3.不要返回值也是可以的把，考虑下设计
        Class<T> cls = (Class<T>) bean.getClass();// todo Class<T> 去掉再debug一下
        Field[] allFields = cls.getDeclaredFields();// todo getFields为什么不行？【看源码】
//        Arrays.stream(allFields).iterator()
        if (allFields != null && allFields.length > 0) {
            for (int i = 0; i < allFields.length; i++) {
                for (Map.Entry entry : map.entrySet()) {
                    if (entry.getKey().equals(allFields[i].getName())) {
                        // todo 设置bean的字段
                        boolean isAccess = allFields[i].isAccessible();
                        allFields[i].setAccessible(true);
                        try {
                            allFields[i].set(bean, entry.getValue());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        allFields[i].setAccessible(isAccess);
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

        List<String> addrList = uList.stream().map(x -> x.getAddr())
                .flatMap(x->Arrays.stream(x.split(";")))
                .collect(Collectors.toList());
        //或者
        List<String> ridStrList = uList.stream().map(x -> x.getAddr()).map(x -> x.split(";"))
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