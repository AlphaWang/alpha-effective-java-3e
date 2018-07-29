# Item 14: Consider implementing Comparable

* Sorting an array of objects that implement Comparable is as simple as this: Arrays.sort(a);
* By implementing Comparable, you allow your class to interoperate with all of the many generic algorithms and collection implementations that depend on this interface. You gain a tremendous amount of power for a small amount of effort.
* Virtually all of the value classes in the Java platform libraries, as well as all enum types, implement Comparable
* Compares this object with the specified object for order. Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object. Throws ClassCastException if the specified object’s type prevents it from being compared to this object.

* The general contract of the compareTo method is similar to that of equals
* In the following description, the notation sgn(expression) designates the mathematical signum function, which is defined to return -1, 0, or 1, according to whether the value of expression is negative, zero, or positive.
	* The implementor must ensure that sgn(x.compareTo(y)) == - sgn(y. compareTo(x)) for all x and y. (This implies that x.compareTo(y) must throw an exception if and only if y.compareTo(x) throws an exception.) 
	* The implementor must also ensure that the relation is transitive: (x. compareTo(y) > 0 && y.compareTo(z) > 0) implies x.compareTo(z) > 0. 
	* Finally, the implementor must ensure that x.compareTo(y) == 0 implies that sgn(x.compareTo(z)) == sgn(y.compareTo(z)), for all z. 
	* It is strongly recommended, but not required, that (x.compareTo(y) == 0) == (x.equals(y)). Generally speaking, any class that implements the Comparable interface and violates this condition should clearly indicate this fact. The recommended language is “Note: This class has a natural ordering that is inconsistent with equals.”

* compareTo doesn’t have to work across objects of different types: when confronted with objects of different types, compareTo is permitted to throw ClassCastException. 
* a class that violates the compareTo contract can break other classes that depend on comparison. e.g.) TreeSet, TreeMap, Arrays, Collections.
* **In Java 7, static compare methods were added to all of Java’s boxed primitive classes. Use of the relational operators < and > in compareTo methods is verbose and error-prone and no longer recommended.**
* If a class has multiple significant fields, the order in which you compare them is critical. Start with the most significant field and work your way down. 