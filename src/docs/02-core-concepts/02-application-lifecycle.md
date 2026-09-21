## Application Lifecycle

A ScalaFX application starts, runs, and stops in a fixed order.
`JFXApp3` manages this for you.
You only fill in two places: `start()` and, optionally, `stopApp()`.

See [Your First ScalaFX App] for the basics
of creating a `JFXApp3` application.
Here we explain in more detail what happens as the application starts and stops.

### What happens when you run the app

1. The `JFXApp3` object body (its constructor) runs first, before JavaFX has started.
   Only initialize plain variables here — do not create ScalaFX/JavaFX elements such as a `Stage`,
   `Scene`, or any UI control, because JavaFX isn't running yet.
2. Next, JavaFX initializes, and `JFXApp3` calls your `start()`.
   Build your UI here. A typical first step is creating a `JFXApp3.PrimaryStage` and setting its
   content.
3. When `start()` returns, `JFXApp3` calls `show()` on the `PrimaryStage`, and the window appears.
4. The application runs, handling events, until the last window closes.
5. `JFXApp3` calls `stopApp()`, and the application shuts down.

The example below prints a line at each step, along with the current thread name.

```scala 3
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane

object Lifecycle extends JFXApp3:

  private def log(message: String): Unit =
    println(s"[${Thread.currentThread.getName}] $message")

  log("object initializer")

  override def start(): Unit =
    log("start() begins")

    stage = new JFXApp3.PrimaryStage:
      title = "Lifecycle"
      scene = new Scene(300, 100):
        root = new StackPane:
          children = new Label("Watch the console")

    stage.showing.onChange((_, _, isShowing) => log(s"showing = $isShowing"))
    log("start() ends")

  override def stopApp(): Unit =
    log("stopApp()")
```

Run it and close the window. The console shows:

```
[main] object initializer
[JavaFX Application Thread] start() begins
[JavaFX Application Thread] start() ends
[JavaFX Application Thread] showing = true
[JavaFX Application Thread] showing = false
[JavaFX Application Thread] stopApp()
```

Two things to notice:

- The object body runs on the `main` thread, before JavaFX starts — the **JavaFX Application
  Thread** does not exist yet.
- `start()` and `stopApp()` run on the JavaFX Application Thread.
  The window appears only after `start()` returns.

### Build the UI inside `start()`

As step 1 above notes, the object body runs too early to use JavaFX elements — put all UI construction
inside `start()` instead.

`stage` is `null` until you assign it there.

### The primary stage

The key to using `JFXApp3` is a code pattern that creates `PrimaryStage` and assigns it to `stage`:

```scala 3
...
stage = new JFXApp3.PrimaryStage:
  ...
```

Behind the scenes, JavaFX creates the primary stage for you. `new JFXApp3.PrimaryStage` creates a ScalaFX wrapper and allows you to modify the stage, adding elements like `title` and `scene`.   

ScalaFX shows the primary stage after `start()` returns.
To choose the moment yourself, turn this off and call `stage.show()` where desired:

```scala 3
override def start(): Unit =
  JFXApp3.AutoShow = false
  stage = new JFXApp3.PrimaryStage:
    title = "Shown on demand"
    scene = new Scene(300, 100)
  // ...
  stage.show()
```

### Command-line arguments

Arguments are available through `parameters`.

| Member                | Content                                     |
|-----------------------|---------------------------------------------|
| `parameters.raw`      | All arguments, as given                     |
| `parameters.named`    | `--name=value` arguments, as a `Map`        |
| `parameters.unnamed`  | Everything else                             |

```scala 3
override def start(): Unit =
  val userName = parameters.named.getOrElse("name", "World")
// ...
```

With SBT, pass them in quotes: `sbt "run --name=Ann"`.

### Shutting down

By default, the application exits when the last window closes.
To exit at any other time, call `Platform.exit()`:

```scala 3
import scalafx.application.Platform

Platform.exit()
```

If you want the application to keep running when windows are no longer open, turn off implicit exit:

```scala 3
Platform.implicitExit = false
```

You must then call `Platform.exit()` yourself.

Override `stopApp()` to do cleanup like releasing resources(closing files, saving settings, disconnecting from servers.

```scala 3
override def stopApp(): Unit =
  database.close()
```

`stopApp()` runs on the JavaFX Application Thread.
Keep it short, or the application will seem to hang while closing.

### Threads

Only the JavaFX Application Thread may change the UI once it is showing.
`start()` already runs on it.
See [Your First ScalaFX App] for a short note on threading, and the
Concurrency section for long-running work.

### Summary

- `JFXApp3` provides `main` behind the scenes and starts JavaFX before calling `start()`. You implement `start()`.
- Build the UI inside `start()`, never in the application object body.
- The primary stage is shown automatically after `start()`.
- The app ends when the last window closes or when you call `Platform.exit()`.
- Use `stopApp()` for cleanup.


[Your First ScalaFX App]: ../01-getting-started/04-your-first-scalafx-app.md