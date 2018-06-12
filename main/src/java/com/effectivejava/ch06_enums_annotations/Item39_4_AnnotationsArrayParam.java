package com.effectivejava.ch06_enums_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Item39_4_AnnotationsArrayParam {
    /**
     * Annotation type with an array parameter
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionTest {
        Class<? extends Throwable>[] value();
    }

    public static class Sample3 {

        /**
         *  Code containing an annotation with an array parameter
         */
        @ExceptionTest({ IndexOutOfBoundsException.class,NullPointerException.class }) 
        public static void doublyBad() {
            List<String> list = new ArrayList<>();
            // The spec permits this method to throw either
            // IndexOutOfBoundsException or NullPointerException
            list.addAll(5, null);
        }

        /**
         * All of the previous ExceptionTest annotations are still valid 
         * with the new array-parameter version of ExceptionTest 
         * and result in single-element arrays.
         */
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

        Class<?> testClass = Sample3.class; //Class.forName(args[0]);
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception%n", m);
                } catch (Throwable wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    int oldPassed = passed;
                    Class<? extends Throwable>[] excTypes = m.getAnnotation(ExceptionTest.class).value();
                    for (Class<? extends Throwable> excType : excTypes) {
                        if (excType.isInstance(exc)) {
                            passed++;
                            break; }
                    }
                    if (passed == oldPassed)
                        System.out.printf("Test %s failed: %s %n", m, exc);
                } }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }

}
