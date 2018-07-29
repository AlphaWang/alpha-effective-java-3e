# Item 90: Consider serialization proxies instead of serialized instances

## serialization proxy pattern.

## way to write

1. design a private static nested class that concisely represents the logical state of an instance of the enclosing class. 
- It should have a single constructor, whose parameter type is the enclosing class. 
- This constructor merely copies the data from its argument: 

```java
// Serialization proxy for Period class
private static class SerializationProxy implements Serializable {
    private final Date start;
    private final Date end;
    SerializationProxy(Period p) {
        this.start = p.start;
        this.end = p.end;
    }
    private static final long serialVersionUID = 234098243823485285L;
}
```
2. add the following writeReplace method to the enclosing class. 
 - To guarantee that such an attack would fail, merely add this readObject method to the enclosing class.
 
```java
// writeReplace method for the serialization proxy pattern
private Object writeReplace() {
    return new SerializationProxy(this);
}
```

```java
// readObject method for the serialization proxy pattern
private void readObject(ObjectInputStream stream) throws InvalidObjectException {
    throw new InvalidObjectException("Proxy required");
}
```

3.provide a readResolve method on the SerializationProxy class that returns a logically equivalent instance of the enclosing class.
```java
// readResolve method for Period.SerializationProxy
private Object readResolve() {
    return new Period(start, end); // Uses public constructor
}
```

## note
- You don’t have to figure out which fields might be compromised by devious serialization attacks, nor do you have to explicitly perform validity checking as part of deserialization.
- The serialization proxy pattern's limitations. 
 1. It is not compatible with classes that are extendable by their users or some classes whose object graphs contain circularities.
 2. the added power and safety of the serialization proxy pattern are not free.

## In Summary
consider the serialization proxy pattern whenever you find yourself having to write a readObject or writeObject method on a class that is not extendable by its clients. 
