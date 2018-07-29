# Item 71: Avoid unnecessary use of checked exceptions

Many Java programmers *dislike* checked exceptions, but used properly, they can improve APIs and programs.

**Advantages:**
- Checked exception force programmers to deal with problems, enhancing reliability.
- Use when the exceptional condition cannot be prevented by proper use of the API

**Shortcomings:**
- If overuse of checked exceptions in APIs can make them far less pleasant to use.

**Bad practice:**
Is this the best that can be done?
``` java
} catch (TheCheckedException e) {
  throw new AssertionError(); // Can't happen!
}
```
Or this?
``` java
} catch (TheCheckedException e) {
  e.printStackTrace();
  System.exit(1);
}
```

### Turn a checked exception into an unchecked exception  
If the programmer can do no better, an unchecked exception is called for.  
The easiest way to eliminate a checked exception is to return an _optional_ of the desired result type (Item 55).  
The disadvantage of this technique is that the method can't return any additional information detailing.  

- from this:
``` java
// Invocation with checked exception
try {
  obj.action(args);
} catch (TheCheckedException e) {
  ... // Handle exceptional condition
}
```
- Refactoring into this: using return an empty Optional
``` java
// Invocation with state-testing method and unchecked exception
if (obj.actionPermitted(args)) {
  obj.action(args);
} else {
  ... // Handle exceptional condition
}
```
- Refactoring more simply: if the programmer knows the call will succeed, or is content to let the thread terminate if it fails.
``` java
obj.action(args);
```
- This refactoring is no always appropriate, but where it is, it can make an API more pleasant to use.

## Summary
When used sparingly, checked exceptions can increase the reliability of programs;  
When overused, they make APIs painful to use.  
If callers won't be able to recover from failures, throw unchecked exceptions.  

[< Before](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/item_70_use_checked_exceptions_for_recoverable_conditions_and_runtime_exceptions_for_programming_errors.md)
[Next >](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/item_72_favor_the_use_of_standard_exceptions.md)
