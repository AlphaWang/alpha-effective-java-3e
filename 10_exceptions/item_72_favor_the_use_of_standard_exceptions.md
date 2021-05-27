# Item 72: Favor the use of standard exceptions

## Reuse standard exception
Exceptions are no exception to the rule that code reuse is a good thing.  
Java libraries provide a set of exceptions that covers most of the exception-throwing needs of most APIs.

### _Benefit_ of reuse standard exception
- Reusing standard exceptions makes your API easier to learn and use.
  - because it matches the established conventions that programmers are already familiar with.
- Easier to read because they aren't cluttered with unfamiliar exceptions.
- Fewer exception classes means a smaller memory footprint and less time spent loading classes.

### Commonly reused exception types:
| Exception   | Occasion for Use  |
|---|---|
| IllegalArgumentException   | Non-null parameter value is inappropriate |
| IllegalStageException  | Object state is inappropriate for method invocation  |
| NullPointerException  | Parameter value is null where prohibited  |
| IndexOutOfBoundsException  | Index parameter value is out of range  |
| ConcurrentModificationException  | Concurrent modification of an object has been detected where it is prohibited  |
| UnsupportedOperationException  | Object does not support method  |

### Rule for reuse:
- Reuse when the conditions under which you would throw it are consistent with the exceptions documentation.
  - Don't use just on name.
- Feel free to subclass a standard exception if you want to add more detail.
- Remember that exceptions are serializable because Throwable class implements Serializable. (Chapter 12)
