package com.effectivejava.ch06_enums_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Item39_3_AnnotationsParam {
    /**
     * Annotation type with a parameter
     * 
     * Indicates that the annotated method is a test method that
     * must throw the designated exception to succeed.
     * 
     * This usage is an example of a `bounded type token` (Item 33).
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionTest {
        Class<? extends Throwable> value();
    }

    /**
     * Program containing annotations with a parameter
     */
    public static class Sample2 {
        
        @ExceptionTest(ArithmeticException.class)
        public static void m1() {  // Test should pass
            int i = 0;
            i = i / i; 
        }
        
        @ExceptionTest(ArithmeticException.class)
        public static void m2() {  // Should fail (wrong exception)
            int[] a = new int[0];
            int i = a[1];
        }
        
        @ExceptionTest(ArithmeticException.class)
        public static void m3() { }  // Should fail (no exception)
    }

    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;

        Class<?> testClass = Sample2.class; //Class.forName(args[0]);
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception%n", m);
                } catch (InvocationTargetException wrappedEx) {
                    Throwable exc = wrappedEx.getCause();
                    Class<? extends Throwable> excType = m.getAnnotation(ExceptionTest.class).value();
                    if (excType.isInstance(exc)) {
                        passed++;
                    } else {
                        System.out.printf("Test %s failed: expected %s, got %s%n", m, excType.getName(), exc);
                    }
                } catch (Exception exc) {
                    System.out.println("Invalid @Test: " + m);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }

}
