# Item 39: Prefer annotations to naming patterns

## Naming patterns

Historically, it was common to use naming patterns to indicate that some program elements demanded special treatment by a tool or framework.   
For example, prior to release 4, the JUnit testing framework required its users to designate test meth- ods by beginning their names with the characters test.

[Example](../main/src/java/com/effectivejava/ch06_enums_annotations/Item39_1_NameingPattern.java)

```java
    public static class Tester3 {

        protected void setUp() throws Exception {
        }

        protected void tearDown() throws Exception {
        }
        
        public final void testEquals() {
            //TODO
        }

        public final void tsetSafetyOverride() {
            //TODO
        }
    }
```

**Disadvantages**
- First, typographical errors result in silent failures.   
(`tsetSafetyOverride` in the example)
- Second, there is no way to ensure that they are used only on appropriate program elements.   
(class `TestSafetyMechanisms` doesn't work)
- Third, they provide no good way to associate parameter values with program elements.  
(suppose you want to support a category of test that succeeds only if it throws a particular exception.)

## Annotations
Annotations don’t change the semantics of the annotated code but enable it for special treatment by tools.
Annotations solve all of these problems nicely.

1) no typo.
2) @Target
3) associate parameter values with program elements.

### Marker annotation type
[Marker annotation type](../main/src/java/com/effectivejava/ch06_enums_annotations/Item39_2_MarkerAnnotations.java)   
`Method.isAnnotationPresent(Test.class)`

### Annotation type with a parameter
[Annotation type with a parameter](../main/src/java/com/effectivejava/ch06_enums_annotations/Item39_3_AnnotationsParam.java)  
`Method.getAnnotation(ExceptionTest.class).value()`

### Annotation type with an array parameter
[Annotation type with an array parameter](../main/src/java/com/effectivejava/ch06_enums_annotations/Item39_4_AnnotationsArrayParam.java)     

### Repeatable annotation type
[Repeatable Annotation type](../main/src/java/com/effectivejava/ch06_enums_annotations/Item39_5_RepeatableAnnotations.java)     
To detect repeated and non-repeated annotations with isAnnotationPresent, you much check for both the annotation type and its containing annotation type.   
`m.isAnnotationPresent(ExceptionTest.class)
|| m.isAnnotationPresent(ExceptionTestContainer.class)`


## Summary

- There is simply no reason to use naming patterns when you can use annotations instead.
- all programmers should use the predefined annotation types that Java provides (Items 40, 27). 
- Also, consider using the annotations provided by your IDE or static analysis tools.   
Such annotations can improve the quality of the diagnostic information provided by these tools.






