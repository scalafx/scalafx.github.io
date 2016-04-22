---
layout: news_item
title: Bindings with arbitrary expressions
date: 2016-04-22 16:59:59 -0400
author: jpsacha
categories: [examples]
---

[ScalaFX][1] v.8.0.92-R10 brings ability to create bindings using custom expressions. For instance, you can binding expression that converts strings to lower case:

```scala
import scalafx.beans.property.StringProperty

val a = new StringProperty()
val b = Bindings.createStringBinding(
          () => Option(a.value).getOrElse("").toLowerCase(), a)

a.value = "HEllO"
println("a: " + a.value)
println("b: " + b.value)
```

Running the above will result in output:

```
a: HEllO
b: hello
```

[1]: http://scalafx.org

