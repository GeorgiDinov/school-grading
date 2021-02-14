package com.georgidinov.roiti.schoolgrading.config.async;


import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {

    //== fields ==
    private final AsyncExceptionHandler asyncExceptionHandler;

    @Autowired
    public AsyncConfig(AsyncExceptionHandler asyncExceptionHandler) {
        this.asyncExceptionHandler = asyncExceptionHandler;
    }


    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncExceptionHandler;
    }
}