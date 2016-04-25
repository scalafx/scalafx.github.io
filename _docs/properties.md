---
layout: docs
title: Properties
permalink: /docs/properties/
---

### Example code


The source code for this tutorial can be found in the [ScalaFX Tutorials](https://github.com/scalafx/ScalaFX-Tutorials) project [properties](https://github.com/scalafx/ScalaFX-Tutorials/tree/master/properties).

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
      println(
        s"Value of property '$name' is changing from $oldValue to $newValue")
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

The complete code is in [Properties101](https://github.com/scalafx/ScalaFX-Tutorials/blob/master/properties/src/main/scala/org/scalafx/tutorials/properties/Properties101.scala) example.

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
When value of `c` change the value of `d` will change too. When `d` changes, `c` will change too.

Binding are one of the most important features of JavaFX. ScalaFX makes binding notation much more expressive.

### Binding Expressions

A property can also bind an expression created from other properties. Consider a formula for computing and area of a triangle: `area = (base * height) / 2`

Assume that we want to recompute value of the area every time `base` or `height` changes. First define properties:

```scala
  val base   = DoubleProperty(15)
  val height = DoubleProperty(10)
  val area   = DoubleProperty(0)

```

ScalaFX overloads arithmetic operators, `+`, `-`, `*` and `/`, for numeric properties, so they can be used to build binding expressions. Binding expressions in turn can be bound to a property:

```scala
area <== base * height / 2

```
Here `base * height / 2` is a binding expression. A binding expression needs to start with a property (or binding expression), but it can also have regular numbers or variables (as long as they are not at the very beginning).

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

```

When you run it you will get following output:

```
base = 15.0, height = 10.0, area =  75.0

Setting base to 20
base = 20.0, height = 10.0, area = 100.0

Setting height to 5
base = 20.0, height =  5.0, area =  50.0
```


The complete code is in [BindingExpressions](https://github.com/scalafx/ScalaFX-Tutorials/blob/master/properties/src/main/scala/org/scalafx/tutorials/properties/BindingExpressions.scala) example.

You can also use `min` and `max` binding expressions. For comparison there are binding expressions expressions `<`, `<=`, `>`, `>=`, `===` (equals), `=!=` (not equals) that create boolean binding expressions. You can also use `&&`, `||`, and `!` in boolean binding expressions.

### when (...) choose ... otherwise ...

A ternary binding expression has a general form:

```
  when(cond) choose(value1) otherwise(value2)
```
you can think about it as a `if(cond) then(value1) else(value2)` expressions, though `if`/`then`/`else` are keywords in Scala and `when`/`choose`/`otherwise` are used instead. For instance, if we want to change a color of a `Rectangle` when a cursor is positioned above it, we can use `when`/`choose`/`otherwise` to select its color. A `Rectangle` has a property `hoover` that is `true` when a mouse is above the rectangle. Color of a rectangle is determined by the value of the property `fill`. Let's bind `fill` to an `when`/`choose`/`otherwise` expression that depending on the value of `hoover` property will change its color to `Green` or `Red`:

```scala
new Rectangle {
  fill <== when (hover) choose Green otherwise Red
}
```

In general, the condition can be any Boolean binding expression (here we just have a property `hoover`). The complete code is in [WhenChooseOtherwiseExpression](https://github.com/scalafx/ScalaFX-Tutorials/blob/master/properties/src/main/scala/org/scalafx/tutorials/properties/WhenChooseOtherwiseExpression.scala) example.

### There is more...

Binding expressions can be created also for String and object properties. If you have some very specific need you can create a custom binding expression. Let say that you want the binding expression to convert source string to upper case. Custom binding is create using `Bindings.createStringBinding(func: () => String, dependencies: Observable*)`.

```
val a = new StringProperty()
val b = Bindings.createStringBinding(
          () => Option(a.value).getOrElse("").toLowerCase(),
          a
        )

a.value = "Hello"
println(s"Setting `a` to ${a.value}, `b` = ${b.value}")
```
Note, we use `Option` to guard against `NullPointerException`. The complete code is in [CustomStringBinding](https://github.com/scalafx/ScalaFX-Tutorials/blob/master/properties/src/main/scala/org/scalafx/tutorials/properties/CustomStringBinding.scala) example.

There are many additional example in [ProScalaFX](https://github.com/scalafx/ProScalaFX) project, [Chapter 3](https://github.com/scalafx/ProScalaFX/tree/master/src/proscalafx/ch03).