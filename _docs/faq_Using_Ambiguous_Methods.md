---
layout: docs
title: Using Ambiguous Methods
prev_section: faq_Using_unwrapped_JavaFX_components
next_section: contributing
permalink: /docs/faq_Using_Ambiguous_Methods/
---

### **How to use methods that have the same signature?**


In Java there is a distinction between primitive types and their wrappers. For instance, `int` is a primitive type and `Integer` is its wrapper. In Scala they are expressed by the same type: `Int`.  Using ScalaFX classes that are parametrized with type corresponding to Java wrapper type, like `Integer`, can lead to compilation errors.

Assume here that we are using a ListView that contains `Int` items, so we will have
{% highlight scala %}
val listView = new ListView[Int] { ... }
{% endhighlight %}

Consider method `scrollTo`. It has two variants `scrollTo(Int index)`, that scrolls to item with given index, and `scrollTo(T object)`, that scrolls to given item on the list. Since `T` is `Int` we have two methods with the same signature. If we try to use `scrollTo`:

{% highlight scala %}
listView.scrollTo(5)
{% endhighlight %}

we will get compilation error:
    
    Error:(160, 16) ambiguous reference to overloaded definition,
        both method scrollTo in class ListView of type (o: Int)Unit
        and  method scrollTo in class ListView of type (index: Int)Unit
        match argument types (Int)
            listView.scrollTo(5)
                     ^

A work around is to use parameter names to disambiguate between invocations:

{% highlight scala %}
listView.scrollTo(index = 5)
{% endhighlight %}

Now the code will compile fine, and we will scroll by the list index (rather than item value).
