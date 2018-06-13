# Item 41: Use marker interfaces to define types

## Marker interface 

A marker interface is an interface that contains no method declarations but merely designates (or “marks”) a class that implements the interface as having some property.  
e.g. `Serializable` 

## Marker interfaces vs. marker annotations
   
Q: marker annotations (Item 39) make marker interfaces obsolete?   
A: NO!  
 
- marker interfaces define a type that is implemented by instances of the marked class; marker annotations do not.  
    > The existence of a marker interface type allows you to catch errors at compile time that you couldn’t catch until runtime if you used a marker annotation. 
- marker interfaces can be targeted more precisely.
    > If an annotation type is declared with target `ElementType.TYPE`, it can be applied to **any** class or interface.
- The chief advantage of marker annotations over marker interfaces is that they are part of the larger annotation facility. 


## Summary 
marker interfaces and marker annotations both have their uses.  
So when should you use a marker annotation and when should you use a marker interface? 

- Clearly you must use an annotation if the marker applies to any program element other than a class or interface, because only classes and interfaces can be made to implement or extend an interface. 
- If the marker applies only to classes and interfaces, ask yourself the question “Might I want to write one or more methods that accept only objects that have this marking?” If so, you should use a marker interface in preference to an annotation. 
- If you can convince yourself that you’ll never want to write a method that accepts only objects with the marking, then you’re probably better off using a marker annotation. 
- If the marking is part of a framework that makes heavy use of annotations, then a marker annotation is the clear choice.