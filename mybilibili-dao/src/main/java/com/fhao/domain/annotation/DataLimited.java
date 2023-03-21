package com.fhao.domain.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * author: FHao
 * create time: 2023-03-21 09:45
 * description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Component
public @interface DataLimited {
}
