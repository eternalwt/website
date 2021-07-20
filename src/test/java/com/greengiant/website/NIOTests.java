package com.greengiant.website;

import org.junit.Test;

import java.util.Date;

import static java.lang.Thread.sleep;

public class NIOTests {

    // https://www.tutorialspoint.com/java_nio/java_nio_channels.htm
    // https://javapapers.com/java/java-nio-channel/
    // http://tutorials.jenkov.com/java-nio/index.html

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

}