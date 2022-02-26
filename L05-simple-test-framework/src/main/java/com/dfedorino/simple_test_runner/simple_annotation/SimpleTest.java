package com.dfedorino.simple_test_runner.simple_annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SimpleTest {}
