---
layout: docs
title: Properties
permalink: /docs/getting_started_properties/
---

### Example code


The source code for this tutorial can be found in the project [???](???)

### ScalaFX Properties

A property holds a value which changes can be observed. Properties can be bound together - when one changes the other changes too. You can also create binding expressions - when on of the component properties changes the value of whole expression changes too.

There are predefined properties for basic types `BooleanProperty`, `DoubleProperty`, `FloatProperty`, `IntegerProperty`, `LongProperty`, and `StringProperty`. There is also a a generic property that can hold any object: `ObjectProperty`.

You quickly create property with a factory method:

```scala
val speed = DoubleProperty(55)
```

Or using a constructor

```scala
val speed = new DoubleProperty(this, "speed", 55)
```

Value if a property can be accessed using `value` method or operator `()`. Following is equivalent:

```scala
speed()
speed.value
``` 

Value of property can be observed adding a listener. The listener is added passing a closure to `onChange` method:

```scala
speed.onChange { (source, oldValue, newValue) => doSomething() }

```
The first argument to the closure is a reference of the property that changes, the other two are the old and the new value. 

Let's create a property with a listener that prints the old and new values then change the property value a couple of times to see what happens:

```scala
  val speed = new DoubleProperty(this, "speed", 55) {
    onChange { (_, oldValue, newValue) =>
      println(s"Value of property '$name' is changing from $oldValue to $newValue")
    }
  }

  speed() = 60
  speed() = 75
  speed.value = 25
```

When you execute that code you will see:

```
Value of property 'speed' is changing from 55.0 to 60.0
Value of property 'speed' is changing from 60.0 to 75.0
Value of property 'speed' is changing from 75.0 to 25.0
```

The complete code for this example is [here???](???)

### Property Binding

You can make a property to change its value when another propery changes, you can bind them together. The binding can be unidirectional:

```scala
a <== b
```

when value of property `b` is changing value of property `a` will change to the same value, but not the other way around. 

Binding can be also bidirectional:

```scala
 c <==> d
```
When value of `c` change the value of `d` will chage too. When `d` changes, `c` will chage too.

Binding are one of the most important features of JavaFX. ScalaFX makes binding notation much more expressive.

### Binding Expressions

A property can also bind an expression created from other properties to a property. Consider a formula for computing and area of a triangle: `area = (base * height) / 2` 

Assume that we want to recompute value of the area every time `base` or `height` changes. First define properties:

```scala
  val base   = DoubleProperty(15)
  val height = DoubleProperty(10)
  val area   = DoubleProperty(0)

```

ScalaFX overloads arithmetic operators for numeric properties. They are used build binding expressions. Binding expressions in turn can be bound to a property:

```scala
area <== base * height / 2

```
Here `base * height / 2` is a binding expression. A binding expresson needs to start with a property (or binding expression), but it can also have regular numbers or variables (as long as they are not at the very beginning).  

Here is an example:

```scala
  val base   = DoubleProperty(15)
  val height = DoubleProperty(10)
  val area   = DoubleProperty(0)

  area <== base * height / 2

  printValues()

  println("Setting base to " + 20)
  base() = 20

  printValues()

  println("Setting height to " + 5)
  height() = 5

  printValues()

  def printValues(): Unit = {
    println(f"base = ${base()}%4.1f, height = ${height()}%4.1f, area = ${area()}%5.1f\n")
  }

```

When you run it you will get following output:

```
base = 15.0, height = 10.0, area =  75.0

Setting base to 20
base = 20.0, height = 10.0, area = 100.0

Setting height to 5
base = 20.0, height =  5.0, area =  50.0
```


The complete code for this example is [here???](???)

You can also create binding expressions from `Boolean` properties using operators like `===`, `=!=`, `&&`, `||`, and `!`

### when (...) choose ... otherwise ...

You create a conditional expression that assigns a value to a propery. For instance, if we want to change a color of a `Rectangle` when a cursor is positioned above it. A `Rectangle` has a property `hoover` that is `true` when a mouse is above the rectangle. Color of a rectangle is deternined by the value of the property `fill`. Let's bind `fill` to an expression that depending on the value of `hoover` property will chnage its color to `Green` or `Red`:

```scala
fill <== when (hover) choose Green otherwise Red
```

In general, the condition can be any Boolean binding expresson (here we just have a property `hoover`). The choices can be binding expressions too.

### There is more

There is much more to the 