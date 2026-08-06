# What is ScalaFX?

ScalaFX is a thin, idiomatic Scala wrapper around JavaFX. JavaFX is a mature, feature-rich GUI toolkit (layouts, controls, CSS styling, animation, 2D/3D graphics) with API is written for Java: verbose builder patterns, listener interfaces, and mutable getters/setters everywhere. ScalaFX takes that same underlying engine and exposes it through a Scala-friendly API.

ScalaFX uses a simple, hierarchical pattern for creating new objects and building up the scene graph.

- **Hierarchical constructors and simplified assignments** instead of Java builder chains:
  ```java
      // JavaFX
      Rectangle r = new Rectangle();
      r.setX(25); 
      r.setY(40); 
      r.setWidth(100); 
      r.setHeight(100);
  ```
  ```scala 3
      // ScalaFX
      val r = new Rectangle:
        x = 25
        y = 40
        width = 100
        height = 100
  ```
- **Simplified properties and bindings syntax**, using operators like `<==` instead of `addListener` boilerplate.
- **Implicit conversions** between ScalaFX wrapper types and the underlying JavaFX types, so you can drop down to raw JavaFX at any point.
- **Scala collections** API instead of raw Java.

Because ScalaFX is a wrapper, with some enhancements, everything you may already know (or will learn) about JavaFX concepts — `Stage`, `Scene`, `Node`, layouts, CSS, FXML — transfers directly to ScalaFX.
JavaFX tutorials or the official JavaFX docs remain useful references; ScalaFX just changes the surface syntax for more idiomatic Scala use.

**When to reach for ScalaFX:** if you're building a desktop GUI in Scala and want a native look, rich widget set, and CSS-based styling without leaving the JVM ecosystem, wherever JavaFX is supported, ScalaFX is the standard choice. It's not for web or mobile UIs.