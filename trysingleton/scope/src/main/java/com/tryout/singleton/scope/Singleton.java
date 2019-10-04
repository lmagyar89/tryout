package com.tryout.singleton.scope;

public class Singleton {
    private static final Singleton instance;

    static {
        instance = new Singleton();
    }

    private Singleton() {
        System.out.println("Creating new Singleton instance");
        System.out.println("Class loader is: " + getClass().getClassLoader());
    }

    public static Singleton getInstance() {
        System.out.println("Singleton getInstance");
        return instance;
    }

    public void doSomeOperation() {
        System.out.println("Doing some sout");
    }
}
