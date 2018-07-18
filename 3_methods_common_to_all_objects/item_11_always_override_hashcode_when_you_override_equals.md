# Item 11: Always override hashCode when you override equals

* Always override hashCode when you override equals. If not, hashcode based collection will not work properly.

    * When the hashCode method is invoked on an object repeatedly during an execution of an application, it must consistently return the same value, provided no information used in equals comparisons is modified. This value need not remain consistent from one execution of an application to another. 
    * **If two objects are equal according to the equals(Object) method, then calling hashCode on the two objects must produce the same integer result.**
    * If two objects are unequal according to the equals(Object) method, it is not required that calling hashCode on each of the objects must produce distinct results. However, the programmer should be aware that producing distinct results for unequal objects may improve the performance of hash tables.

* Then, How to override the hashCode?

    * Declare an int variable named result, and initialize it to the hash code c for the first significant field in your object, as computed in step 2.a. (Recall from Item 10 that a significant field is a field that affects equals comparisons.) 
    * For every remaining significant field f in your object, do the following: 
        * Compute an int hash code c for the field: 
            * If the field is of a primitive type, compute Type.hashCode(f), where Type is the boxed primitive class corresponding to f’s type. 
            * If the field is an object reference and this class’s equals method compares the field by recursively invoking equals, recursively invoke hashCode on the field. If a more complex comparison is required, compute a “canonical representation” for this field and invoke hashCode on the canonical representation. If the value of the field is null, use 0 (or some other constant, but 0 is traditional). 
            * If the field is an array, treat it as if each significant element were a separate field. That is, compute a hash code for each significant element by applying these rules recursively, and combine the values per step 2.b. If the array has no significant elements, use a constant, preferably not 0. If all elements are significant, use Arrays.hashCode. 
        * Combine the hash code c computed in step 2.a into result as follows: result = 31 * result + c; 
     * Return result.

    * You may exclude derived fields from the hash code computation. In other words, you may ignore any field whose value can be computed from fields included in the computation. 
    * You must exclude any fields that are not used in equals comparisons, or you risk violating the second provision of the hashCode contract.
    * If a class is immutable and the cost of computing the hash code is significant, you might consider caching the hash code in the object rather than recalculating it each time it is requested. 
    * Do not be tempted to exclude significant fields from the hash code computation to improve performance.
    * Don’t provide a detailed specification for the value returned by hashCode, so clients can’t reasonably depend on it; this gives you the flexibility to change it.

* Use utils about this.

    * Lombok @EqualsAndHashCode
    * Objects.hash
    * com.google.common.hash.Hashing