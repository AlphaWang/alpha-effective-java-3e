# Effective Java 3rd Edition

## Index
* [Introduction](README.md)
* [1 Prefix](1_prefix/README.md)
* [2 Creating and Destroying Objects](2_creating_and_destroying_objects/README.md)
   * Item 1: Consider static factory methods instead of constructors
   * Item 2: Consider a builder when faced with many constructor parameters
   * Item 3: Enforce the singleton property with a private constructor or an enum type  
   * Item 4: Enforce noninstantiability with a private constructor
   * Item 5: Perfer dependecy injection to hardwiring resources
   * Item 6: Avoid creating unnecessary objects
   * Item 7: Eliminate obsolete object references
   * Item 8: Avoid finalizers and cleaners
   * Item 9: Prefer try-with-resources to try-finally
* [3 Methods Common to All Objects](3_methods_common_to_all_objects/README.md)
   * [Item 10: Obey the general contract when overriding equals](3_methods_common_to_all_objects/item_10_obey_the_general_contract_when_overriding_equals.md)
   * [Item 11: Always override hashCode when you override equals](3_methods_common_to_all_objects/item_11_always_override_hashcode_when_you_override_equals.md)
   * [Item 12: Always override toString](3_methods_common_to_all_objects/item_12_always_override_tostring.md)
   * [Item 13: Override clone judiciously](3_methods_common_to_all_objects/item_13_override_clone_judiciously.md)
   * [Item 14: Consider implementing Comparable](3_methods_common_to_all_objects/item_14_consider_implementing_comparable.md)
* [4 Classes and Interfaces](4_classes_and_interfaces/README.md)
   * Item 15: Minimize the accessibility of classes and members
   * Item 16: In public classes, use accessor methods, not public fields
   * Item 17: Minimize mutability
   * Item 18: Favor composition over inheritance  
   * Item 19: Design and document for inheritance or else prohibit  it  
   * Item 20: Prefer interfaces to abstract classes  
   * Item 21: Design interfaces for posterity
   * Item 22: Use interfaces only to define types  
   * Item 23: Prefer class hierarchies to tagged classes  
   * Item 24: Favor static member classes over nonstatic
   * Item 25: Limit source files to a single top-level class
* [5 Generics](5_generics/README.md)
   * Item 26: Don't use raw types
   * Item 27: Eliminate unchecked warnings
   * Item 28: Prefer lists to arrays
   * Item 29: Favor generic types
   * Item 30: Favor generic methods
   * Item 31: Use bounded wildcards to increase API flexibility
   * Item 32: Combine generics and varargs judiciously
   * Item 33: Consider typesafe heterogeneous containers
* [6 Enums and Annotations](6_enums_and_annotations/README.md)
   * [Item 34: Use enums instead of int constants](6_enums_and_annotations/item_34_use_enums_instead_of_int_constants.md)
   * [Item 35: Use instance fields instead of ordinals](6_enums_and_annotations/item_35_use_instance_fields_instead_of_ordinals.md)
   * [Item 36: Use EnumSet instead of bit fields](6_enums_and_annotations/item_36_use_enumset_instead_of_bit_fields.md)
   * [Item 37: Use EnumMap instead of ordinal indexing](6_enums_and_annotations/item_37_use_enummap_instead_of_ordinal_indexing.md)
   * [Item 38: Emulate extensible enums with interfaces](6_enums_and_annotations/item_38_emulate_extensible_enums_with_interfaces.md)
   * [Item 39: Prefer annotations to naming patterns](6_enums_and_annotations/item_39_prefer_annotations_to_naming_patterns.md)
   * [Item 40: Consistently use the Override annotation](6_enums_and_annotations/item_40_consistently_use_the_override_annotation.md)
   * [Item 41: Use marker interfaces to define types](6_enums_and_annotations/item_41_use_marker_interfaces_to_define_types.md)
* [7 Lambdas and Streams](7_lambdas_and_streams/README.md)
   * [Item 42: Prefer lambdas to anonymous classes](7_lambdas_and_streams/item_42_prefer_lambdas_to_anonymous_classes.md)
   * [Item 43: Prefer method references to lambdas](7_lambdas_and_streams/item_43_prefer_method_references_to_lambdas.md)
   * [Item 44: Favor the use of standard functional interfaces](7_lambdas_and_streams/item_44_favor_the_use_of_standard_functional_interfaces.md)
   * [Item 45: Use streams judiciously](7_lambdas_and_streams/item_45_use_streams_judiciously.md)
   * [Item 46: Prefer side-effect-free functions in streams](7_lambdas_and_streams/item_46_prefer_side-effect-free_functions_in_streams.md)
   * [Item 47: Prefer Collection to Stream as a return type](7_lambdas_and_streams/item_47_prefer_collection_to_stream_as_a_return_type.md)
   * [Item 48: Use caution when making streams parallel](7_lambdas_and_streams/item_48_use_caution_when_making_streams_parallel.md)
* [8 Methods](8_methods/README.md)
   * [Item 49: Check parameters for validity](8_methods/item_49_check_parameters_for_validity.md)
   * [Item 50: Make defensive copies when needed](8_methods/item_50_make_defensive_copies_when_needed.md)
   * [Item 51: Design method signatures carefully](8_methods/item_51_design_method_signatures_carefully.md)
   * [Item 52: Use overloading judiciously](8_methods/item_52_use_overloading_judiciously.md)
   * [Item 53: Use varargs judiciously](8_methods/item_53_use_varargs_judiciously.md)
   * [Item 54: Return empty collections or arrays, not nulls](8_methods/item_54_return_empty_collections_or_arrays,_not_nulls.md)
   * [Item 55: Return optionals judiciouslly](8_methods/item_55_return_optionals_judiciouslly.md)
   * [Item 56: Write doc comments for all exposed API elements](8_methods/item_56_write_doc_comments_for_all_exposed_api_elements.md)
* [9 General Programming](9_general_programming/README.md)
   * Item 57: Minimize the scope of local variables
   * Item 58: Prefer for-each loops to traditional for loops
   * Item 59: Know and use the libraries
   * Item 60: Avoid float and double if exact answsers are required
   * Item 61: Prefer primitive types to boxed primitives
   * Item 62: Avoid strings where other types are more appropriate
   * Item 63: Beware the performance of string concatenation
   * Item 64: Refer to objects by their interfaces
   * Item 65: Prefer interfaces to reflection
   * Item 66: Use native methods judiciously
   * Item 67: Optimize judiciously
   * Item 68: Adhere to generally accepted naming conventions
* [10 Exceptions](10_exceptions/README.md)
   * [Item 69: Use exceptins only for exceptional conditions](10_exceptions/item_69_use_exceptions_only_for_exceptional_conditions.md)
   * [Item 70: Use checked exceptions for recoverable conditions and runtime exceptions for programming errors](10_exceptions/item_70_use_checked_exceptions_for_recoverable_conditions_and_runtime_exceptions_for_programming_errors.md)
   * [Item 71: Avoid unnecessary use of checked exceptions](10_exceptions/item_71_avoid_unnecessary_use_of_checked_exceptions.md)
   * [Item 72: Favor the use of standard exceptions](10_exceptions/item_72_favor_the_use_of_standard_exceptions.md)
   * [Item 73: Throw exceptions appropriate to the abstractions](10_exceptions/item_73_throw_exceptions_appropriate_to_the_abstractions.md)
   * [Item 74: Document all exceptins thrown by each method](10_exceptions/item_74_document_all_exceptins_thrown_by_each_method.md)
   * [Item 75: Include failure-capture information indetail messages](10_exceptions/item_75_include_failure-capture_information_indetail_messages.md)
   * [Item 76: Strive for failure atomicity](10_exceptions/item_76_strive_for_failure_atomicity.md)
   * [Item 77: Don't ignore exceptions](10_exceptions/item_77_dont_ignore_exceptions.md)
* [11 Concurrency](11_concurrency/README.md)
   * Item 78: Synchronize access to shared mutable data
   * Item 79: Avoid excessive synchronization
   * Item 80: Prefer executors, tasks, and streams to threads
   * Item 81: Prefer concurrency utilities to wait and notify
   * Item 82: Document thread safety
   * Item 83: Use lazy initialization judiciously
   * Item 84: Don't depend on the thread scheduler
* [12 Serialization](12_serialization/README.md)
   * [Item 85: Prefer alternatives to Java serialization](12_serialization/item_85_prefer_alternatives_to_java_serialization.md)
   * [Item 86: Implement Serialzable with great caution](12_serialization/item_86_implement_serializable_with_great_caution.md)
   * [Item 87: Condider using a custom serialized form](12_serialization/item_87_consider_using_a_custom_serialized_form.md)
   * [Item 88: Write readObject methods defensively](12_serialization/item_88_write_readobject_methods_defensively.md)
   * [Item 89: For instance control, prefer enum types to readResovle](12_serialization/item_89_for_instance_control,_prefer_enum_types_to_readresolve.md)
   * [Item 90: Consider serialization proxies instead of serialized instances](12_serialization/item_90_consider_serialization_proxies_instead_of_serialized_instances.md)

