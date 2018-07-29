# Item 85: Prefer alternatives to Java serialization

## do not use Java serialization

A fundamental problem with serialization is that its attack surface is too big to protect, and constantly growing:

In the process of deserializing a byte stream, 'readObject' method can execute code from any of these types, so this may be part of the attack surface.
Deserialization of untrusted streams can result in remote code execution (RCE), denial-of-service (DoS), and a range of other exploits. 

The best way to avoid serialization exploits is never to deserialize anything.

## alternatives instead of Java serialization

There are other mechanisms for translating between objects and byte sequences that avoid many of the dangers of Java serialization, while offering numerous advantages, such as cross-platform support, high performance, a large ecosystem of tools, and a broad community of expertise. 

 1. JSON
    - text-based and human-readable
    - exclusively a data representation
 2. Protocol Buffers (protobuf)
    - binary and substantially more efficient
    - offers schemas (types) to document and enforce appropriate usage.

## when you can’t avoid Java serialization entirely

 - never accept RMI traffic from untrusted sources. 
 - use the object deserialization filtering added in Java 9 and backported to earlier releases (java.io.ObjectInputFilter).
 - use whitelisting (prefer whitelist to blacklist)
 -care to write a serializable class that is correct, safe, and efficient. 


## summary

 - serialization is dangerous and should be avoided. 
 - use a cross-platform structured-data representation such as JSON or protobuf instead. 
 - or do not deserialize untrusted data. 
 - If you must do so
    - use object deserialization filtering, and Avoid writing serializable classes. 
    - exercise great caution.