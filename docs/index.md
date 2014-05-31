---
layout: docs
title: What is ScalaFX?
next_section: quickstart
permalink: /docs/home/
---

ScalaFX is a UI DSL written within the Scala Language that sits on top of JavaFX 2 and 8 (not to be confused with [Ingo Maier’s](http://people.epfl.ch/ingo.maier) great work on [Functional Reactive Programming](http://lamp.epfl.ch/~imaier/pub/DeprecatingObserversTR2010.pdf) for Swing). 
This means that every ScalaFX application is also a valid Scala application. By extension it supports full interoperability with Java and can run anywhere the Java Virtual Machine (JVM) and JavaFX2 or JavaFX8 are supported.

Some of the features of ScalaFX include:

###A Programmer-Friendly Object-Literal-Like Syntax

ScalaFX uses a simple, hierarchical pattern for creating new objects and building up the scene graph. Here is a simple example that creates a new stage with a rectangle that changes color based on mouse events:

{% highlight scala %}
stage = new JFXApp.PrimaryStage {
  title = "Hello Stage"
  width = 600
  height = 450
  scene = new Scene {
    fill = Color.LIGHTGREEN
    content = new Rectangle {
      x = 25
      y = 40
      width = 100
      height = 100
      fill <== when (hover) choose Color.GREEN otherwise Color.RED
    }
  }
}
{% endhighlight %}

Unlike the builders you find in the core JavaFX APIs, the ScalaFX object declaration syntax uses the normal object API. This means that you can use the same operators and convenient syntax to create and modify your scene graph. Also, anything that is permissible in a Scala block (such as variable declarations, method calls, binding, etc.) can also be done inline while constructing objects. For JavaFX builders you need to declare binding after you finish creating your objects, which leads to disassociated and hard to maintain code.

###Natural Language Bind Expressions

One of the greatest advantages of using the Scala language as a DSL is the rich support for operators as methods. This is similar to the C++ concept of operator overloading, but much more uniform and clean in its application.

The ScalaFX bind library exposes normal operators such as `&&`, `||`, `+`, `-`, `*`, `/` on top of all bindable objects. Also, Scala supports operator precedence, so it looks and feels like you are writing normal expressions even though you are creating bound objects under the covers. As a result, you have the full functionality available from the JavaFX 2.0 binding libraries with code that looks akin to mathematical expressions and operators.

Here are some examples of what you can do with the ScalaFX bind API:

Infix Addition/Subtraction/Multiplication/Division/etc.

{% highlight scala %}
height <== rect1.height + rect2.height
{% endhighlight %}

Aggregate Operators

{% highlight scala %}
width <== max(rect1.width, rect2.width, rect3.width)
{% endhighlight %}

Conditional Expressions

{% highlight scala %}
color <== when (hover) choose Color.GREEN otherwise Color.RED
{% endhighlight %}

Complex Boolean Expressions and String Concatenation

{% highlight scala %}
text <== when (rect.hover || circle.hover && !disabled) 
           choose textField.text + " is enabled" 
           otherwise "disabled"
{% endhighlight %}

Free-form Invalidation and Change Handlers

{% highlight scala %}
rect.hover onInvalidate {
  needsRepaint = true
}
{% endhighlight %}

###Tailored Animation Syntax

Animations are very commonly used in creating good UIs, which is why JavaFX Script had a built-in construct to simplify the creation of animations. ScalaFX has a similar syntax that allows you to quickly and easily create animations, which is used in the ColorfulCircles? example:

{% highlight scala %}
Seq(
  at(0 s) {circle.centerX -> random * 800},
  at(0 s) {circle.centerY -> random * 600},
  at(40 s) {circle.centerX -> random * 800},
  at(40 s) {circle.centerY -> random * 600}
)
{% endhighlight %}

This makes it trivially easy to create complex animations.

###Fully Type-Safe APIs

This may seem like an insignificant point… Type safety is something that Java developers have always had (and often take for granted), and developers in other scripting languages live without (and unknowingly suffer with runtime errors as a result). However, it is a critical feature if you are developing applications that cannot have unexpected runtime errors and bugs after deployment.

A good compiler will be able to pick up many common coding mistakes through comparison of expected and actual types, and a great compiler (like Scala) will automatically infer types for you so you don’t have to tediouisly repeat them throughout your code.

ScalaFX gets the best of both worlds with a scripting-like DSL syntax where you can rarely have to explicitly type objects, with the strong type-safety of the Scala compiler that will infer and check the types of every expression and API call. This means less time spent debugging weird code bugs and misspellings, and higher quality code right out of the gate!

###Seamless JavaFX/ScalaFX Interoperability

It is often the case that you do not have complete freedom about the predominant language of the codebase, or of the libraries you are including functionality from. Even in a mixed environment codebase where you have Java, Scala, and possibly other JVM languages, ScalaFX will seamlessly convert and interoperate.

ScalaFX gets this functionality through the implicit operator capabilities of Scala. Anywhere your program expects a JavaFX typed object, it will automatically insert the code to convert form ScalaFX wrapped objects to JavaFX native classes. Any time you use a ScalaFX specific feature, the compiler automatically creates a ScalaFX wrapper object that allows you to call advanced methods and access the full functionality. This all happens behind the scenes, letting you focus on writing clean code, and not fussing about integration and interoperability.

With all this magic interoperability happening behind the scenes, there is some additional overhead on your application. We have taken pains to minimize this using features like `@specialize` in the Scala language, which allows us to avoid boxing and unboxing costs on primitives. However, without real benchmarks it is hard to tell just how good of a job we have done.