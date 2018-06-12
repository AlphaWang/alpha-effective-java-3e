package com.effectivejava.ch06_enums_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Item39_5_RepeatableAnnotations {

    /**
     * Repeatable annotation type
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    // This meta-annotation takes a single parameter, 
    // which is the class object of a containing annotation type, 
    // whose sole parameter is an array of the annotation type 
    @Repeatable(ExceptionTestContainer.class)
    public @interface ExceptionTest {
        Class<? extends Exception> value();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionTestContainer {
        ExceptionTest[] value();
    }

    
    public static class Sample4 {

        /**
         *  Code containing a repeated annotation
         */
        @ExceptionTest(IndexOutOfBoundsException.class) 
        @ExceptionTest(NullPointerException.class) 
        public static void doublyBad() {
            List<String> list = new ArrayList<>();
            // The spec permits this method to throw either
            // IndexOutOfBoundsException or NullPointerException
            list.addAll(5, null);
        }
    }

    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;

        Class<?> testClass = Sample4.class; //Class.forName(args[0]);
        for (Method m : testClass.getDeclaredMethods()) {
            // Processing repeatable annotations
            if (m.isAnnotationPresent(ExceptionTest.class)
                || m.isAnnotationPresent(ExceptionTestContainer.class)) {
                
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception%n", m);
                } catch (Throwable wrappedExc) {
                    Throwable exc = wrappedExc.getCause(); 
                    int oldPassed = passed; 
                    
                    ExceptionTest[] excTests = m.getAnnotationsByType(ExceptionTest.class);
                    
                    for (ExceptionTest excTest : excTests) {
                        if (excTest.value().isInstance(exc)) { 
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
