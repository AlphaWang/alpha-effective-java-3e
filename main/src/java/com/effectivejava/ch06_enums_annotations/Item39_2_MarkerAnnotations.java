package com.effectivejava.ch06_enums_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Item39_2_MarkerAnnotations {

    /**
     * Indicates that the annotated method is a test method.
     * Use only on parameterless static methods.
     */
    @Retention(RetentionPolicy.RUNTIME) 
    @Target(ElementType.METHOD) 
    public @interface Test {
    }

    /**
     * Here is how the Test annotation looks in practice:
     * 
     * Program containing marker annotations.
     */
    public static class Sample {
        @Test 
        public static void m1() { } // Test should pass 
        
        public static void m2() { }

        @Test 
        public static void m3() { // Test should fail
            throw new RuntimeException("Boom");
        }

        public static void m4() {
        }

        @Test 
        public void m5() {
        } // INVALID USE: nonstatic method public static void m6() { }

        @Test 
        public static void m7() { // Test should fail
            throw new RuntimeException("Crash");
        }

        public static void m8() {
        }
    }

    /** 
     * annotations don’t change the semantics of the annotated code but enable it for special treatment by tools.
     * 
     * Program to process marker annotations
     */
    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;
        
        Class<?> testClass = Sample.class; //Class.forName(args[0]);
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) { 
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(m + " failed: " + exc);
                } catch (Exception exc) {
                    System.out.println("Invalid @Test: " + m);
                }
            } 
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    } 
}
