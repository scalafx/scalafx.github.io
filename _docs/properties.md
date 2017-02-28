---
layout: docs
title: Properties
permalink: /docs/properties/
---

### Example code


The source code for this tutorial can be found in the the [ScalaFX Tutorials](https://github.com/scalafx/ScalaFX-Tutorials) project [properties](https://github.com/scalafx/ScalaFX-Tutorials/tree/master/properties).

### ScalaFX Properties

A property holds a value whose changes can be observed. Properties can be bound together - when one changes, the other changes too. You can also create binding expressions - when one of the component properties changes, the value of the whole expression changes too.

There are predefined properties for the basic types: `BooleanProperty`, `DoubleProperty`, `FloatProperty`, `IntegerProperty`, `LongProperty`, and `StringProperty`. There is also a generic property that can hold any object: `ObjectProperty`.

You quickly create a property with a factory method:

```scala
val speed = DoubleProperty(55)
```

Or using a constructor:

```scala
val speed = new DoubleProperty(this, "speed", 55)
```

The value of a property can be accessed using the `value` method or the `()` operator. The following two statements are equivalent:

```scala
speed()
speed.value
```

### Adding Listeners

The value of a property can be observed by adding a listener. The listener is added by passing a closure to the `onChange` method:

```scala
speed.onChange { (source, oldValue, newValue) => doSomething() }

```
The first argument to the closure is a reference to the changed property, the other two are the old and the new value. 

Let's create a property with a listener that prints the old and new values, then change the property's value a few times to see what happens:

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

The complete code is in the [Properties101](https://github.com/scalafx/ScalaFX-Tutorials/blob/master/properties/src/main/scala/org/scalafx/tutorials/properties/Properties101.scala) example.

### Removing Listeners

A subscription handle lets you remove a listener.
A subscription is created for every listener added to a property.
When you no longer need to the listen, you "cancel" the subscription:

```scala
val prop = DoubleProperty(0)

val subscription = prop.onChange { (_, _, newValue) =>
             println(s"Property changed value to $newValue")
           }

prop.value = 1
subscription.cancel()

// Listener will not be notified about this change
prop.value = 2
```


### Property Binding

You can make a property change its value when another propery changes by binding them together. The binding can be unidirectional:

```scala
a <== b
```

when the value of property `b` changes, the value of property `a` will change to the same value, but not the other way around. 

Bindings can also be bidirectional:

```scala
 c <==> d
```
When the value of `c` changes, the value of `d` will change too. When `d` changes, `c` will change too.

Binding are one of the most important features of JavaFX. ScalaFX makes binding notation much more expressive.

### Binding Expressions

A property can also bind an expression created from other properties. Consider a formula for computing the area of a triangle: `area = (base * height) / 2`

Assume that we want to recompute the value of the area every time `base` or `height` changes. First define properties:

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


The complete code is in the [BindingExpressions](https://github.com/scalafx/ScalaFX-Tutorials/blob/master/properties/src/main/scala/org/scalafx/tutorials/properties/BindingExpressions.scala) example.

You can also use `min` and `max` in binding expressions. For comparison, there are the binding expression operators `<`, `<=`, `>`, `>=`, `===` (equals), `=!=` (not equals) that create boolean binding expressions. You can also use `&&`, `||`, and `!` in boolean binding expressions.

### when (...) choose ... otherwise ...

A ternary binding expression has the general form:

```
  when(cond) choose(value1) otherwise(value2)
```
you can think about it as a `if(cond) then(value1) else(value2)` expression, though `if`/`then`/`else` are keywords in Scala, so `when`/`choose`/`otherwise` are used instead. For instance, if we want to change the color of a `Rectangle` when the cursor is positioned above it, we can use `when`/`choose`/`otherwise` to select its color. The `Rectangle` has a `hover` property that is `true` when the cursor is above the rectangle. The color of the rectangle is determined by the value of the `fill` property. Let's bind `fill` to an `when`/`choose`/`otherwise` expression that, depending on the value of `hover` property, will change its color to `Green` or `Red`:

```scala
new Rectangle {
  fill <== when (hover) choose Green otherwise Red
}
```

In general, the condition can be any Boolean binding expression (here we just have a property `hover`). The complete code is in the [WhenChooseOtherwiseExpression](https://github.com/scalafx/ScalaFX-Tutorials/blob/master/properties/src/main/scala/org/scalafx/tutorials/properties/WhenChooseOtherwiseExpression.scala) example.

### There is more...

Binding expressions can also be created for String and object properties. If you have some very specific need you can create a custom binding expression. Let's say that you want the binding expression to convert the source string to upper case. A custom binding is created using `Bindings.createStringBinding(func: () => String, dependencies: Observable*)`.

```
val a = new StringProperty()
val b = Bindings.createStringBinding(
          () => Option(a.value).getOrElse("").toLowerCase(),
          a
        )

a.value = "Hello"
println(s"Setting `a` to ${a.value}, `b` = ${b.value}")
```
Note, we use `Option` to guard against `NullPointerException`. The complete code is in the [CustomStringBinding](https://github.com/scalafx/ScalaFX-Tutorials/blob/master/properties/src/main/scala/org/scalafx/tutorials/properties/CustomStringBinding.scala) example.

There are many additional examples in the [ProScalaFX](https://github.com/scalafx/ProScalaFX) project, [Chapter 3](https://github.com/scalafx/ProScalaFX/tree/master/src/proscalafx/ch03).
