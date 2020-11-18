package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AspectHandler implements InvocationHandler {
    private final Target target;
    private Aspect aspect;
    public AspectHandler(Target target) {
        this.target = target;
        aspect = new AspectImpl();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        aspect.before();
        method.invoke(target, args);
        aspect.after();
        return null;
    }
}
