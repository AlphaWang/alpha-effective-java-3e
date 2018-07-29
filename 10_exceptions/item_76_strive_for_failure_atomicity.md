# Item 76: Strive for failure atomicity

## A failed method invocation should leave the object in the state that it was in prior to the invocation.

### _How to achieve this effect!_

### 1-1. The simplest way : _Do design immutable objects(Item 17)._

```
The rules to class immutable.
1.Don’t provide methods that modify the object’s state
2.Ensure that the class can’t be extended
3.Make all fields final
4.Make all fields private
5.Ensure exclusive access to any mutable components
```
- The state of each object is consistent when it is created and can't be modified thereafter.

### 1-2. The most common way (to achieving failure atomicity) : _For mutable objects, Check parameters for validity before performing the operation(Item49)._

``` java
public Object pop() {
  if (size == 0)
    throw new EmptyStackException(); // Check parameter
  Object result = elements[--size];
  elements[size] = null; // Eliminate obsolete reference
  return result;
}
```

### 2. A closely related approach : _Order the computation so that any part that may fail takes place before any part that modified the object._

For example, _TreeMap_
_TreeMap_'s elements are sorted according to some ordering.
In order to add an element to a TreeMap, the element must be of a type that can be compared using the TreeMap's ordering.

``` java
    public V put(K key, V value) {
        Entry<K,V> t = root;
        if (t == null) {
            compare(key, key); // type (and possibly null) check

            root = new Entry<>(key, value, null);
            size = 1;
            modCount++;
            return null;
        }
        ...
    }
```
- Attempting to add an incorrectly typed element will naturally fail with a _ClassCastException_ as a result of searching for the element in the tree.

### 3. Perform the operation on a temporary copy of the object, and to replace the contents of the object with the temporary copy once the operation is complete.
- For example : Sorting functions case, This is done for performance, but as an added benefit, it ensures that the input list will be untouched if the soft fails.

``` java
    default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }
```

### 4. Far Less common approach : Write _recovery code_ that intercepts a failure that occurs in the midst of an operation.
- The object to roll back its state to the point before the operation began. This approach is used mainly for durable (disk-based) data structures.

*****

_However,_
It is **not always achievable** even if while failure atomicity is generally desirable.
For example,
- If two threads attempt to modify the same object concurrently without proper synchronization, the object may be left in an inconsistent state.
  It would therefore be wrong to assume that an object was still usable after catching a ConcurrentModificationException.
- _Errors_ are unrecoverable, so you need not even attempt to preserve failure atomicity when throwing AssertionError.



## Summary
In summary,
Any generated exception that is part of a method's specification **should leave the object in the same state** it was in prior to the method invocation.  
Where this rule is violated, **the API documentation should clearly indicate** what state the object will be left in.


[< Before](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/item_75_include_failure-capture_information_indetail_messages.md)
[Next >](http://gitlab.coupang.net/allie/effective-java/blob/master/10_exceptions/item_77_dont_ignore_exceptions.md)