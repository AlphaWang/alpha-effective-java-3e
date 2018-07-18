# Item 10: Obey the general contract when overriding equals


Overriding  the equals method is simple but it can be dangerous.

* When do not need override the equals method.
	* Each instance is unique.
	* No need logical equality test.
	* Super class has overridden equals and it is appropriate.
	* The class is private or package-private, and the equals will not be invoked.
	* Logical equality is the same as object identity. e.g) Enum types, The object can get from static factory method. 

* Appropriate cases for override the equals.
	* When need logical equality test. 
	* Generally for value classes. e.g) Integer or String
* Overridden equals enables instances to serve as map keys or set elements with predictable, desirable behavior.


* General contract for overriding equals.
	* Reflexive: For any non-null reference value x, x.equals(x) must return true. 
	* Symmetric: For any non-null reference values x and y, x.equals(y) must return true if and only if y.equals(x) returns true. 
	* Transitive: For any non-null reference values x, y, z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) must return true. 
	* Consistent: For any non-null reference values x and y, multiple invocations of x.equals(y) must consistently return true or consistently return false, provided no information used in equals comparisons is modified. 
	* Non-nullity: For any non-null reference value x, x.equals(null) must return false.
* Once you’ve violated the equals contract, you simply don’t know how other objects will behave when confronted with your object
* There is no way to extend an instantiable class and add a value component while preserving the equals contract.
* Do not write an equals method that depends on unreliable resources. 
* equals methods should perform only deterministic computations on memoryresident objects.


* recipe for a high-quality equals method: 
	* Use the == operator to check if the argument is a reference to this object. 
	* Use the instanceof operator to check if the argument has the correct type.
	* Cast the argument to the correct type. 
	* For each “significant” field in the class, check if that field of the argument matches the corresponding field of this object. 
	* When you are finished writing your equals method, ask yourself three questions: Is it symmetric? Is it transitive? Is it consistent?

* extra things to consider
	* Always override hashCode when you override equals.
	* Don’t try to be too clever. 
	* Don’t substitute another type for Object in the equals declaration. => prevent this using @override annotation.