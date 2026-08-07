## For the Impatient

If you want to quickly build and run a simple ScalaFX application on your computer, here are the quick paths using either  [Scala CLI](https://scala-cli.virtuslab.org/) or [SBT](https://www.scala-sbt.org/). Details are explained in the sections that follow. 


### Path A — scala-cli (fastest, zero project setup)

[Scala CLI](https://scala-cli.virtuslab.org/) runs a single `.scala` file directly, no build.sbt needed. Save this as `Hello.scala`:

```scala 3
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane

object Hello extends JFXApp3:
  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Hello, ScalaFX"
      width = 300
      height = 150
      scene = new Scene:
        root = new StackPane:
          children = new Label("Hello, ScalaFX!")
```

Then run it:

```bash
scala-cli Hello.scala
```


### Path B — SBT with the ScalaFX giter8 template (fastest full project)

If you want a proper [SBT](https://www.scala-sbt.org/) project instead of a single script, SBT can scaffold one for you instantly using the official ScalaFX template. In a directory of choice run on the command line:

```bash
sbt new scalafx/scalafx.g8
```

Answer the prompts (project name, etc.), and you'll have a runnable ScalaFX project skeleton. `cd` into it and run:

```bash
sbt run
```


### Path C — I want to understand what I'm doing

Skip to [Installation & Setup](03-installation-and-setup.md) and [Your First ScalaFX App](04-your-first-scalafx-app.md) below for the full explanation.