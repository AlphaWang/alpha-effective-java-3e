# Item 69: Use exceptions only for exceptional conditions

## Use exception only for exceptional conditions

**Some example :**
``` java
// Horrible abuse of exceptions. Don't ever do this!
try {
  int i = 0;
  while(true)
    range[i++].climb();
} catch (ArrayIndexOutOfBoundsException e) {
}
```

What does this code do?
- The infinite loop terminates by throwing, catching, and ignoring an *ArrayIndexOutOfBoundsException*.


**Standard idiom :**
``` java
for (Mountain m : range)
  m.climb();
```

**Wrong point :**
Let's inspection! Why should anyone use the exception-based loop?
- It's caused a misguided attempt to improve performance based on the faulty reasoning like below.
  - VM checks the bounds of all array accesses, the normal loop termination test is redundant and should be avoided

However,
- JVM implementations optimize. In fact, the exception-based idiom is far slower than the standard one. (On writer's machine.)
- Exception-based loop obfuscate(confusing and difficult to understand) the purpose of the code and reduce its performance.

### Conclusion :
_**Exceptions are, as their name implies, to be used only for exceptional conditions; they should never be used for ordinary control flow.**_
- Even if the performance advantage is real, it may not remain in the face of steadily improving platform implementations.
- Overly clever techniques can make the subtle bugs and maintenance headaches. .


---


**This principle also has implications for API design.**
### A well-designed API must not force its clients to use exceptions for ordinary control flow.

``` java
for (Iterator<Foo> i = collection.iterator(); i.hasNext(); ){
  Foo foo = i.next();
  ...
}
```
A well designed point :
- The Iterator interface has the stage-dependent method _next_ and the corresponding stage-testing method _hasNext_.
- This enables the standard idiom for iterating over a collection with a traditional for loop (as well as the for-each loop, where the _hasNext_ method is used internally.)

``` java
// Do not use this hideous code for iteration over a collection!
try {
  Iterator<Foo> i = collection.iterator();
  while(true) {
    Foo foo = i.next();
    ...
  }
} catch (NoSuchElementException e){
}
```

## Summary
Exceptions are designed for exceptional conditions.  
Don't use them for ordinary control flow, and don't write APIs that force others to do so.

[< Before](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/Chapter10_Exceptions.md)
[Next >](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/item_70_use_checked_exceptions_for_recoverable_conditions_and_runtime_exceptions_for_programming_errors.md)