# Item 74: Document all exceptions thrown by each method

## Documentation of the exceptions thrown by a method
- **Always declare checked exceptions individually, and document precisely the conditions under which each one is thrown using the Javadoc @throws tag.**
   - Don't take the shortcut of declaring that a method throws some superclass of multiple exception classes that it can throw.
      - Don't declare that a public method throws Exception or, worse, throws Throwable.
      - It is denying any guidance to the method's user concerning the exceptions it is capable of throwing.

- Declare the unchecked exceptions that a method is capable of throwing.
   - A well-documented list of the unchecked exceptions that a method can throw effectively describes the _preconditions_ for its successful execution.

- Declare the unchecked exceptions that s method in interfaces document.
   - This documentation forms a part of the interface's _general contract_ and enables common behavior among multiple implementations of the interface.

- **Use the Javadoc @throws tag to document each exception that a method can throw, but do _not_use the throws keyword on unchecked exceptions.**
   - It is important that programmers using your API are aware of which exceptions are checked and which are unchecked.

- **If an exception is thrown by many methods in a class for the same reason, you can document the exception in the class's documentation comment** rather than documenting it individually for each method.
   - Common Example

``` java
/**
 * Thrown when an application attempts to use {@code null} in a
 * case where an object is required.
 * ...
 */
public
class NullPointerException extends RuntimeException {
  ...
}
```

## Summary
- Write a document every exception that can be thrown by each method.
- Write a document for unchecked as well as checked exceptions
- Write a document for abstract as well as concrete methods
- The document should take the form of @throws tags in doc comments
- Declare each checked exception individually in a method's throws clause
- Do not declare unchecked exceptions in a method's throws clause

