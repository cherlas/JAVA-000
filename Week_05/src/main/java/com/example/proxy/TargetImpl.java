package com.example.proxy;

public class TargetImpl implements Target{
    @Override
    public void proxyRunner() {
        System.out.println("run method");
    }
}
