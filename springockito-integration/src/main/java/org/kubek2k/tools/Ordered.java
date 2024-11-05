package org.kubek2k.tools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Ordered {

    int FIRST = Integer.MIN_VALUE;
    int LAST = Integer.MAX_VALUE;
    int DEFAULT = 0;

    int value() default DEFAULT;

}
