---
layout: docs
title: Quick-start guide
permalink: /docs/quickstart/
---

For the impatient, here's how to get started using ScalaFX in your development (link to complete code at the bottom). 

If you are reading this Quick-start Guide you probably already know how to use Scala in your project and probably have 
your favorite IDE or build setup to program in Scala. 
 
To create a ScalaFX application you need to add the ScalaFX dependency using either [scala-cli](https://scala-cli.virtuslab.org/):
```scala
//> using dep "org.scalafx::scalafx:26.0.0-R38"
```

Or [sbt](https://www.scala-sbt.org/):
```scala
libraryDependencies += "org.scalafx" %% "scalafx" % "26.0.0-R38"
```

Now you are ready to type-in and run you first ScalaFX application:

```scala
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.text.Text

object ScalaFXHelloWorld extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
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
