# Item 87: Consider using a custom serialized form

## to use default serialized form
 - first considering whether it is appropriate. 

The default serialized form of an object is a reasonably efficient encoding of the physical representation of the object graph rooted at the object. 
In other words, it describes the data contained in the object and in every object that is reachable from this object. 
It also describes the topology by which all of these objects are interlinked. 

The ideal serialized form of an object contains only the logical data represented by the object. 

-  The default serialized form is likely to be appropriate if an object’s physical representation is identical to its logical content. 
```java
// Good candidate for default serialized form
public class Name implements Serializable {
    /**
    * Last name. Must be non-null.
    * @serial
    */
    private final String lastName;
    /**
    * First name. Must be non-null.
    * @serial
    */
    private final String firstName;
    /**
    * Middle name, or null if there is none.
    * @serial
    */
    private final String middleName;
// ... Remainder omitted
}
```

- must provide a readObject method to ensure invariants and security. 

- public API must be documented. 
    - The presence of the @serial tag tells Javadoc to place this documentation on a special page that documents serialized forms.

-  Using the default serialized form when an object’s physical representation differs substantially from its logical data content has four disadvantages:

    • It permanently ties the exported API to the current internal representation.
    • It can consume excessive space
        - Because the serialized form is excessively large, writing it to disk or sending it across the network will be excessively slow.
    • It can consume excessive time.
    • It can cause stack overflows. 
        - Surprisingly, the minimum list size for which serialization causes a stack overflow varies from run to run (on my machine). 

 - Before deciding to make a field nontransient, convince yourself that its value is part of the logical state of the object.
If you use a custom serialized form, most or all of the instance fields should be labeled transient, as in the StringList example above.

If you are using the default serialized form and you have labeled one or more fields transient, remember that these fields will be initialized to their default values when an instance is deserialized: 
null for object reference fields, zero for numeric primitive fields, and false for boolean fields [JLS, 4.12.5]. 
If these values are unacceptable for any transient fields, you must provide a readObject method that invokes the defaultReadObject method and then restores transient fields to acceptable values (Item 88). 
 Alternatively, these fields can be lazily initialized the first time they are used (Item 83).


Whether or not you use the default serialized form, 
 - impose any synchronization on object serialization that you would impose on any other method that reads the entire state of the object. 

```java
// writeObject for synchronized class with default serialized form
private synchronized void writeObject(ObjectOutputStream s) throws IOException {
    s.defaultWriteObject();
}
```

 - declare an explicit serial version UID in every serializable class you write.
Declaring a serial version UID is simple. Just add this line to your class:
```java
    private static final long serialVersionUID = randomLongValue;
``` 
* Do not change the serial version UID unless you want to break compatibility with all existing serialized instances of a class.
 
 
## summary
think hard about what the serialized form should be. 

 - Use the default serialized form only if it is a reasonable description of the logical state of the object; 
otherwise design a custom serialized form that aptly describes the object. 
 - should allocate as much time to designing the serialized form of a class as you allocate to designing an exported method (Item 51). 
because must be preserved forever to ensure serialization compatibility. 
