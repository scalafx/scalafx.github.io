---
layout: home
title: ScalaFX &bull; simpler way to use JavaFX from Scala
overview: true
---

ScalaFX is a UI DSL written within the Scala Language that sits on top of JavaFX. Every ScalaFX application is also a valid Scala application. It supports full interoperability with Java and can run anywhere the Java Virtual Machine (JVM) and JavaFX are supported.

ScalaFX uses a simple, hierarchical pattern for creating new objects and building up the scene graph. Here is a simple, complete application example that creates a new stage (window) with a button and a label that updates as the button is clicked:

```scala
import scalafx.application.JFXApp3
import scalafx.beans.property.IntegerProperty
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.VBox

object HelloStageDemo extends JFXApp3:

  override def start(): Unit =
    val clickCount = IntegerProperty(0)

    val messageLabel = new Label:
      text <== clickCount.asString("Clicked %d times")

    val button = new Button("Click Me"):
      onAction = _ => clickCount.value += 1

    stage = new JFXApp3.PrimaryStage:
      title = "Hello Stage"
      width = 300
      height = 150
      scene = new Scene:
        root = new VBox:
          alignment = Pos.Center
          spacing = 20
          children = Seq(messageLabel, button)
```

Some of the features of ScalaFX include:

* A programmer-friendly object-literal-like syntax
* Natural Language Bind Expressions
* Tailored Animation Syntax
* Fully Type-Safe APIs
* Seamless JavaFX/ScalaFX Interoperability

To learn more watch the presentation below and read the [Documentation]({{ site.url }}/docs/home) section.

## ScalaFX Overview Presentation

[Stephen Chin](http://steveonjava.com) presentation [JavaFX 2 and Scala - Like Milk and Cookies (33 Degrees)](Https://www.slideshare.net/steveonjava/javafx-2-and-scala-like-milk-and-cookies-33rd-degrees) 

<iframe src="https://www.slideshare.net/slideshow/embed_code/12148807" width="599" height="487" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px">  </div>

## Support ScalaFX Project

<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=QTHP3D2X4F3W4">
  <img src="https://www.paypal.com/en_US/i/btn/btn_donateCC_LG.gif" alt="Donate">
</a>

[How Donations Work]({{ site.url }}/docs/how_donations_work/)

## Community Code of Conduct

We request all the team members to follow the [Typelevel Code of Conduct](http://typelevel.org/conduct.html) in our mailing list, issue discussion, Gitter room or any of ScalaFX meetups.
