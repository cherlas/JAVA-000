package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class CustomAop {
    public static void main(String[] args) {
        TargetImpl targetImpl = new TargetImpl();
        InvocationHandler invocationHandler = new AspectHandler(targetImpl);
        Target target = (Target) Proxy.newProxyInstance(CustomAop.class.getClassLoader(), new Class[]{Target.class}, invocationHandler);
        target.proxyRunner();
    }
}
