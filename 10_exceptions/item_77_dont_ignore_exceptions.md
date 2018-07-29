# Item 77: Don't ignore exceptions

### Don't ignore exceptions

Like this...
``` Java
// Empty catch block ignores exception - Highly suspect!
try {
...
} catch (SomeException e){
}
```
An empty catch block defeats the purpose of exceptions, which is to force you to handle exceptional conditions.


**If you want to ignore...**
If you choose to ignore an exception, the catch block should contain a comment explaining why it is appropriate to do so,  
and the variable should be named ignored.

``` Java
Future<Integer> f = exec.submit(planarMap::chromaticNumber);
int numColors = 4; // Default; guaranteed sufficient for any map
try {
  numColors = f.get(1L, TimeUnit.SECONDS);
} catch (TimeoutException | ExecutionException ignored) {
  // Use default: minimal coloring is desirable, not required
}
```

Whether an exception represents a predictable exceptional condition or a programming error,  
ignoring it with an empty catch block will result in a program that continues silently in the face of error.  
**The program might then fail at an arbitrary time in the future**.

### Summary
_So, Don't ignore exceptions._

[< Before](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/item_76_strive_for_failure_atomicity.md)