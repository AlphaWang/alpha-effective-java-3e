# Item 88: Write readObject methods defensively

The casts on byte values whose high-order bit is set is a consequence of Java’s lack of byte literals combined with the unfortunate decision to make the byte type signed:

## to write readObject
readObject method must not invoke an overridable method, either directly or indirectly
```java
// target to write readObject
// Immutable class that uses defensive copying
public final class Period {
    private final Date start;
    private final Date end;
    /**
    * @param start the beginning of the period
    * @param end the end of the period; must not precede start
    * @throws IllegalArgumentException if start is after end
    * @throws NullPointerException if start or end is null
    */
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.compareTo(this.end) > 0)
        throw new IllegalArgumentException(
        start + " after " + end);
    }
    public Date start () { return new Date(start.getTime()); }
    public Date end () { return new Date(end.getTime()); }
    public String toString() { return start + " - " + end; }
    // ... Remainder omitted
}

```
1  provide a readObject method for Period that calls defaultReadObject and then checks the validity of the deserialized object. 

```java
// readObject method with validity checking - insufficient!
private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();
    // Check that our invariants are satisfied
    if (start.compareTo(end) > 0)
    throw new InvalidObjectException(start +" after "+ end);
}
```

*  It is possible to create a mutable Period instance by fabricating a byte stream that begins with a valid Period instance and then appends extra references to the private Date fields internal to the Period instance. 

The source of the problem is that Period’s readObject method is not doing enough defensive copying. 

2  every serializable immutable class containing private mutable components must defensively copy these components in its readObject method. 

```java
// readObject method with defensive copying and validity checking
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        // Defensively copy our mutable components
        start = new Date(start.getTime());
        end = new Date(end.getTime());
        // Check that our invariants are satisfied
        if (start.compareTo(end) > 0)
        throw new InvalidObjectException(start +" after "+ end);
    }
```

3   we must make the start and end fields nonfinal. 

* Alternatively, you can use the serialization proxy pattern (Item 90). 


## summary
anytime you write a readObject method, adopt the mind-set that you are writing a public constructor that must produce a valid instance regardless of what byte stream it is given. 

guidelines for writing a readObject method:
 - For classes with object reference fields that must remain private, defensively copy each object in such a field. 
    Mutable components of immutable classes fall into this category.
 - Check any invariants and throw an InvalidObjectException if a check fails.
    The checks should follow any defensive copying.
 - If an entire object graph must be validated after it is deserialized, use the
    ObjectInputValidation interface (not discussed in this book).
 - Do not invoke any overridable methods in the class, directly or indirectly.
