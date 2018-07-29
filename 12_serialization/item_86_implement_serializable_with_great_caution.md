# Item 86: Implement Serializable with great caution

## Serializable's cost 

1. decreases the flexibility to change a class’s implementation once it has been released.
    - If you opt to make a class serializable, you should carefully design a high-quality serialized form that you’re willing to live with for the long haul
    - If you fail to declare a serial version UID, compatibility will be broken, resulting in an InvalidClassException at runtime.
2. increases the likelihood of bugs and security holes
3. increases the testing burden associated with releasing a new version of a class.

## consider point
 - Implementing Serializable is not a decision to be undertaken lightly and you should weigh the costs against the benefits. 
 - Classes designed for inheritance should rarely implement Serializable, and interfaces should rarely extend it. 

## To implement a class with instance fields that is both serializable and extendable.
 - prevent subclasses from overriding the finalize method by overriding finalize and declaring it final. 
 - if the class has invariants that would be violated if its instance fields were initialized to their default values, you must add this readObjectNoData method like below:

```java
// readObjectNoData for stateful extendable serializable classes
private void readObjectNoData() throws InvalidObjectException {
    throw new InvalidObjectException("Stream data required");
}
```


## serializable of inner class
Inner classes should not implement Serializable 
    - because how these fields correspond to the class definition is unspecified.
but A static member class can implement Serializable.


## summary

the ease of implementing Serializable is specious. 
but Unless a class is to be used only in a protected environment where versions will never have to interoperate and servers will never be exposed to untrusted data, 
implementing Serializable is a serious commitment that should be made with great care. 
and Extra caution is warranted if a class permits inheritance.