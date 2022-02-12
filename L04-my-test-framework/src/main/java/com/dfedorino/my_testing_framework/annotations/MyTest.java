package com.dfedorino.my_testing_framework.annotations;

import java.io.Serial;
import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyTest {
    class None extends Throwable {
        @Serial
        private static final long serialVersionUID = 1L;

        private None() {}
    }

    Class<? extends Throwable> expected() default MyTest.None.class;
}
