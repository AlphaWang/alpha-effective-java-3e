# Item 51: Design method signatures carefully

<h3>Choose method names carefully</h3>

> <b>Names should always obey the standard naming conventions</b><br>
> * Choose name that are understandable & consistent with other name in the same package
> * Choose name consistent with the broader consensus.<br>

<i><u>Avoid long method name</u></i><br>
<b>Look to the Java library APIs for guidance<br></b>

<h3>Don't go overboard in providing convenience methods</h3>

<h3>Avoid long parameter lists</h3>
> Long sequences of identically typed parameters are especially harmful.

<h3>For parameter types, favor interfaces over classes</h3>
> public void xxx(ArrayList list) => public void xxx(List list);
> <br>List, Map, Set

<h3>Prefer two-element enum types to boolean parameter</h3>


<a href="./item_52_use_overloading_judiciously.md">next item</a>