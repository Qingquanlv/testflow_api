package com.testflow.apitest.business;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@ParameterizedTest
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(RangeArgumentsProvider.class)
public @interface TestFlowTest {
    String path() default "";

}
