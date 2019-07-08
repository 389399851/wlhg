package com.yd.taozi.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by xiaotaozi on 2019/7/1.
 */
@ControllerAdvice
public class MyException {
    @ExceptionHandler(value =ArithmeticException.class)
    public String pao(){
        return "index";
    }
}
