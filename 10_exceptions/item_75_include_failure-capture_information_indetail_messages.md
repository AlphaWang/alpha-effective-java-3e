# Item 75: Include failure-capture information in detail messages

## The detail message of an exception should capture the _failure_ for subsequent analysis
To capture a failure, the detail message of an exception **should contain the values of _all parameters and fields_ that contributed to the exception**.

### In detail..
_**When a program fails due to an uncaught exception,**_
- The system automatically prints out the exception's stack tract
- The stack tract contains the exception's _string representation_, the result of invoking its *toString* method.

``` java
    // class Throwable
    // : return the exception's class name followed by its detail message.
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
```
- Frequently this is the only information that programmers or site reliability will have when investigating a software failure.
- Therefore, it is important that the exception's _toString_ method return as much information as possible concerning the cause of the failure.

### For example,
IndexOutOfBoundsException should contain the lower bound, the upper bound, and the index value that failed to lie between the bounds.  
If IndexOutOfBoundsException print these detail message, it would be helpful to find the cause more easily.

This is an example what defined constructors with this required information instead of a string detail message in IndexOutOfBoundsException.
``` java
/** Constructs an IndexOutOfBoundsException.
 * @param lowerBound the lowest legal index value
 * @param upperBound the highest legal index value plus one
 * @param index the actual index value
 */
public IndexOutOfBoundsException(int lowerBound, int upperBound, int index) {
  // Generate a detail message that captures the failure
  super(String.format("Lower bound: %d, Upper bound: %d, Index: %d", lowerBound, upperBound, index));

  // Save failure information for programmatic access
  this.lowerBound = lowerBound;
  this.upperBound = upperBound;
  this.index = index;
}
```
- As of Java 9, IndexOutOfBoundsException finally acquired a constructor that takes an int valued _index_ parameter.
   - Java 8 : https://docs.oracle.com/javase/8/docs/api/java/lang/IndexOutOfBoundsException.html
   - Java 9 : https://docs.oracle.com/javase/9/docs/api/java/lang/IndexOutOfBoundsException.html
- More generally, the Java libraries don't make heavy use of this idiom, but it is highly recommended.
   - It makes it hard for the programmer not to capture the failure.

### _Caution!_
- Do not include passwords, encryption keys, and the like in detail messages. (security_sensitive information)
- Do not include a lot of prose(=too much information) in detail message. (The stack trace and source code is enough for analyzing.)
- Do not confused with a user-level error message.
   - The user-level message must be intelligible to end users.
   - The detail message is primarily for the benefit of programmers or site reliability when analyzing a failure.

## Summary
Include failure-capture information in detail messages. Even for unchecked exceptions.
