package com.tryout.singleton.scope;

import org.junit.Test;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonScopeTest {
    @Test
    public void testSameInstance() {
        Singleton firstInstance = Singleton.getInstance();
        Singleton scondInstance = Singleton.getInstance();

        assertThat(firstInstance).isSameAs(scondInstance);
    }

    @Test
    public void testSameInstanceFromClassLoader() throws Exception{
        URL baseUrl = getBaseUrl();
        URLClassLoader classLoader = getClassLoader(baseUrl);
        Class<?> singletonClass = getSingletonClass(classLoader);
        Method getInstanceMethod = getInstanceMethod(singletonClass);

        assertThat(getInstanceMethod.invoke(null)).isSameAs(getInstanceMethod.invoke(null));
    }

    @Test
    public void testNotSameInstanceFromDifferentClassLoader() throws Exception {
        URL baseUrl = getBaseUrl();

        URLClassLoader firstClassLoader = getClassLoader(baseUrl);
        Class<?> firstSingletonClass = getSingletonClass(firstClassLoader);
        Method firstGetInstanceMethod = getInstanceMethod(firstSingletonClass);

        URLClassLoader secondClassLoader = getClassLoader(baseUrl);
        Class<?> secondSingletonClass = getSingletonClass(secondClassLoader);
        Method secondGetInstanceMethod = getInstanceMethod(secondSingletonClass);

        assertThat(firstGetInstanceMethod.invoke(null)).isNotSameAs(secondGetInstanceMethod.invoke(null));
    }

    @Test
    public void testSameFromParentClassLoader() throws Exception {
        URL baseUrl = getBaseUrl();

        URLClassLoader parent = getClassLoader(baseUrl);

        URLClassLoader firstClassLoader = getClassLoader(baseUrl, parent);
        Class<?> firstSingletonClass = getSingletonClass(firstClassLoader);
        Method firstGetInstanceMethod = getInstanceMethod(firstSingletonClass);

        URLClassLoader secondClassLoader = getClassLoader(baseUrl, parent);
        Class<?> secondSingletonClass = getSingletonClass(secondClassLoader);
        Method secondGetInstanceMethod = getInstanceMethod(secondSingletonClass);

        assertThat(firstGetInstanceMethod.invoke(null)).isSameAs(secondGetInstanceMethod.invoke(null));
    }

    private static URL getBaseUrl() {
        return SingletonScopeTest.class.getResource("./../../../../../classes/");
    }

    private static URLClassLoader getClassLoader(URL baseUrl) {
        return getClassLoader(baseUrl, null);
    }

    private static URLClassLoader getClassLoader(URL baseUrl, ClassLoader parent) {
        return new URLClassLoader(new URL[] {baseUrl}, parent);
    }

    private static Class<?> getSingletonClass(URLClassLoader classLoader) throws Exception{
        return classLoader.loadClass("com.tryout.singleton.scope.Singleton");
    }

    private static Method getInstanceMethod(Class<?> clazz) throws Exception{
        return clazz.getMethod("getInstance", null);
    }
}
