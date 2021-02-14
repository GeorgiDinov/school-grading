package com.georgidinov.roiti.schoolgrading.config.async;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @SneakyThrows
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... args) {
        log.error("Async Error Occurred In method with name = {} error message = {}", method.getName(), ex.getMessage());
    }
}