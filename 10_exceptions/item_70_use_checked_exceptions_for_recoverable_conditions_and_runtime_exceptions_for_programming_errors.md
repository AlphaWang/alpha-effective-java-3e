# Item 70: Use checked exceptions for recoverable conditions and runtime exceptions for programming errors

### Java provides three kinds of throwables :
## Checked exceptions
Use checked exceptions for conditions from which the caller can reasonably be expected to recover.  
By throwing a checked exception, you force the caller to handle the exception in a catch clause or to propagate it outward.  
Each checked exception that a method is declared to throw is therefore a potent indication to the API user that the associated condition is *a possible outcome of invoking* the method.
> Example : IOException, SQLException, ClassNotFoundException, NoSearchMethodException ...

## Runtime exceptions
Use Runtime exceptions to indicate programming errors.  
The great majority of runtime exceptions indicate _precondition violations.  
All of the unchecked throwables you implement should subclass *RuntimeException*.  
> Example : ArrayIndexOutOfBoundsException, NullPointerException, ClassCastException ...

## Errors
Errors are reserved for use by the JVM to indicate resource deficiencies, invariant failures, or other conditions that make it impossible to continue execution.  
It's best not to implement any new *Error* subclasses.

### Caution!
It is possible to define a throwable that is not a subclass of Exception, RuntimeException or Error.  
When should we use such a beast?
> Never!
They have no benefits over ordinary checked exceptions and would serve merely to confuse the user of your API.


## Summary
Throw checked exceptions for recoverable conditions and unchecked exceptions for programming errors.  
When in doubt, throw unchecked exceptions.  
Don't define any throwables that are neither checked exception nor runtime exceptions.  
Provide methods on you checked exceptions to aid(help) in recovery. (See Item 75).

***


[< Before](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/item_69_use_exceptions_only_for_exceptional_conditions.md)
[Next >](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/item_71_avoid_unnecessary_use_of_checked_exceptions.md)