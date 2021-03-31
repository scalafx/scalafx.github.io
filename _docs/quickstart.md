---
layout: docs
title: Quick-start guide
permalink: /docs/quickstart/
---

For the impatient, here's how to get started using ScalaFX in your development (link to complete code at the bottom). 

If you are reading this Quick-start Guide you probably already know how to use Scala in your project and probably have 
your favorite IDE or build setup to program in Scala. If not, details will be explained later. Here we assume that project is defined using [SBT](https://www.scala-sbt.org/)
 
To create a ScalaFX application you need to add a dependency on the ScalaFX library and corresponding version of the JavaFX. In the example we will use JavaFX "15.0.1" and ScalaFX "15.0.1-R21".

To add dependency on ScalaFX add the following line:
```scala
libraryDependencies += "org.scalafx" %% "scalafx" % "15.0.1-R21"
```

JavaFX binaries are system dependent. We can detect the current system type and determined needed JavaFX binaries using following code:  
```scala
lazy val javaFXModules = {
  // Determine OS version of JavaFX binaries
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux")   => "linux"
    case n if n.startsWith("Mac")     => "mac"
    case n if n.startsWith("Windows") => "win"
    case _                            => 
      throw new Exception("Unknown platform!")
  }
  // Create dependencies for JavaFX modules
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map( m=> "org.openjfx" % s"javafx-$m" % "15.0.1" classifier osName)
}

libraryDependencies ++= javaFXModules
```


Now you are ready to type-in and run you first ScalaFX application:

```scala
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.text.Text

object ScalaFXHelloWorld extends JFXApp {

  stage = new PrimaryStage {
    title = "ScalaFX Hello World"
    scene = new Scene {
      fill = Color.rgb(38, 38, 38)
      content = new HBox {
        padding = Insets(50, 80, 50, 80)
        children = Seq(
          new Text {
            text = "Scala"
            style = "-fx-font: normal bold 100pt sans-serif"
            fill = new LinearGradient(
              endX = 0,
              stops = Stops(Red, DarkRed))
          },
          new Text {
            text = "FX"
            style = "-fx-font: italic bold 100pt sans-serif"
            fill = new LinearGradient(
              endX = 0,
              stops = Stops(White, DarkGray)
            )
            effect = new DropShadow {
              color = DarkGray
              radius = 15
              spread = 0.25
            }
          }
        )
      }
    }
  }
}
```

It will display a window like this:
![ScalaFX Hello World!!!]({{ site.url }}/img/scalafx_hello_world.png)

You can find the complete project in the [scalafx-hello-world](https://github.com/scalafx/scalafx-hello-world) sample in GitHub.
You can clone the repo or download the sample code from the [Release](https://github.com/scalafx/scalafx-hello-world/releases) page.

Alternatively you can create a simple ScalaFX project using SBT's "new" command and ScalaFX template:
```
$ sbt new scalafx/scalafx.g8
```
