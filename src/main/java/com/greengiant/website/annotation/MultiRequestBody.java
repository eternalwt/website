package com.greengiant.website.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiRequestBody {
    boolean required() default false;

    boolean parseAllFields() default true;

    String value() default "";
}
