---
layout: docs
title: Quick-start guide
permalink: /docs/quickstart/
---

For the impatient, here's how to get started using ScalaFX in your development. 

If you are reading this Quick-start Guide you are probably already know how to use Scala in your project and probably have 
your favorite IDE or build setup to program in Scala. If not, details will be explained later.
 
ScalaFX supports JavaFX 2.2 and JavaFX 8. 
To create a ScalaFX application you only need to add dependency on ScalaFX library. 
For JavaFX 2.2 (distributed with Java 7) you will need to add dependency on ScalaFX v.1. 
For JavaFX 8 (distributed with Java 8) you will need to add dependency on ScalaFX v.8. 
For instance, if you are using Java8 and SBT as your build system, add the following line to `build.sbt`

{% highlight scala %}
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"
{% endhighlight %}

You can download ScalaFX releases from [Maven repository at Sonatype](http://search.maven.org/#search&#124;ga&#124;1&#124;scalafx).

Now you are ready to type-in and run you first ScalaFX application:

{% highlight scala %}
package hello

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{Stops, LinearGradient}
import scalafx.scene.text.Text

object ScalaFXHelloWorld extends JFXApp {

  stage = new PrimaryStage {
    title = "ScalaFX Hello World"
    scene = new Scene {
      fill = Black
      content = new HBox {
        padding = Insets(20)
        children = Seq(
          new Text {
            text = "Hello "
            style = "-fx-font-size: 48pt"
            fill = new LinearGradient(
              endX = 0,
              stops = Stops(PaleGreen, SeaGreen))
          },
          new Text {
            text = "World!!!"
            style = "-fx-font-size: 48pt"
            fill = new LinearGradient(
              endX = 0,
              stops = Stops(Cyan, DodgerBlue)
            )
            effect = new DropShadow {
              color = DodgerBlue
              radius = 25
              spread = 0.25
            }
          }
        )
      }
    }
  }
}
{% endhighlight %}

It will display window like this:
![ScalaFX Hello World!!!]({{ site.url }}/img/scalafx_hello_world.png)

You can find complete project in the [scalafx-hello-world](https://github.com/scalafx/scalafx-hello-world) sample at GitHub.
You can clone the repo or download sample code from the [Release](https://github.com/scalafx/scalafx-hello-world/releases) page.
The project can be run from command line or loaded into IntelliJ IDEA, NetBeans, or Eclipse. 
