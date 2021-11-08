package com.greengiant.website.lib;

import com.greengiant.infrastructure.utils.IdCardVerification;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class IDCardTests {

    @Test
    public void testIDCardValidate() throws ParseException {
        System.out.println(IdCardVerification.IDCardValidate("830000195505060111"));
    }

}
