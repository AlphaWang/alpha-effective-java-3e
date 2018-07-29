# Item 73: Throw exceptions appropriate to the abstractions

### Situation:
It is disconcerting when a method throws an exception that has no apparent connection to the task that it performs.  
When a method propagates an exception thrown by a lower-level abstraction, It pollutes(make dirty) the API of the higher layer with implementation details.  
If the implementation of the higher layer changes in a later release,  
the exceptions it throws will change too, potentially breaking existing client programs.  

### How to avoid:

## 1. Exception Translation
Higher layers should **catch lower-level exceptions** and,
in their place,
throw exceptions that can **be explained in terms of the higher-level abstraction**.
``` java
try {
  ... // Use lower-level abstraction to do our bidding
} catch (LowerLevelException e) {
  throw new Higher LevelException(...);
}
```

### For Example,
_AbstractSequentialList_ class, which is a _skeletal implementation_ of the _List_ interface,  
exception translation is mandated by the specification of the get method in the _List<E> interface.  
However, ListIterator.next() method can throw _NoSearchElementException_.  
Therefore, _AbstractSequentialList_ catch the _NoSearchElementException_(lower-level exception) and,  
throw the _IndexOutOfBoundsException_(high-level exception).

``` java
/**
 * Returns the element at the specified position in this list.
 * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
 */
public E get(int index) {
  ListIterator<E> i = listIterator(index);
  try {
    return i.next();
  } catch (NoSearchElementException e) {
    throw new IndexOutOfBoundsException("Index:" + index);
  }
}
```


*****

## 2. Exception Chaining
_**exception chaining**_ is a special form of exception translation
Used where **the lower-level exception might be helpful to someone debugging** the problem that caused the higher-level exception.

``` java
// Exception Chaining
try {
  ... // use lower-level abstraction to do our bidding
} catch (LowerLevelException cause) {
  throw new HigherLevelException(cause);
}
```
The higher-level exception's constructor passes the cause to a _chaining-aware_ superclass constructor,
so it is ultimately passed to one of _Throwable's chaining-aware_ constructors. Such as _Throwable(Throwable);

``` java
// Exception with chaining-aware constructor
class HigherLevelException extends Exception {
  HigherLevelException(Throwable cause) {
    super(cause);
  }
}
```
- Most standard exceptions have chaining-aware constructors.
- If a exception doesn't have chaining-aware constructors, you can set the cause using Throwable's initCause method.

### Advantages :
- Exception chaining let you access the cause programmatically (with getCause)
- It integrates the cause's stack trace into that of the higher-level exception.

### Caution :
While exception translation is superrior to mindless propagation of exceptions from lower layers,
it **should not be overused.**

## Summary
if it isn't feasible to prevent or to handle exceptions from lower layers, use exception translation.  
unless the lower-level method happens to guarantee that all of its exceptions are appropriate to the higher level.  
Chaining provides the best of both worlds:  
it allows you to throw an appropriate higher-level exception while capturing the underlying cause for failure analysis.  

[< Before](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/item_72_favor_the_use_of_standard_exceptions.md)
[Next >](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/item_74_document_all_exceptins_thrown_by_each_method.md)