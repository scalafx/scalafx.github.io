## Scala and JavaFX Interop

ScalaFX does not replace JavaFX. It wraps it.
Every ScalaFX object holds a JavaFX object and forwards calls to it.
Once you know this, most of ScalaFX's behavior is easy to predict.

### Wrapper classes

Each ScalaFX class mirrors a JavaFX class with the same name.
Only the root package changes, from `javafx` to `scalafx`. For instance:

| JavaFX                           | ScalaFX                           |
|----------------------------------|-----------------------------------|
| `javafx.stage.Stage`             | `scalafx.stage.Stage`             |
| `javafx.scene.Scene`             | `scalafx.scene.Scene`             |
| `javafx.scene.control.Button`    | `scalafx.scene.control.Button`    |
| `javafx.scene.layout.VBox`       | `scalafx.scene.layout.VBox`       |

The wrapped JavaFX object is available as `delegate`.
You can also wrap an existing JavaFX object by passing it to the constructor.

```scala 3
import javafx.scene.control as jfxc
import scalafx.scene.control.Button

val fxButton = new jfxc.Button("Created by JavaFX")
val button   = new Button(fxButton)     // wrap an existing JavaFX object

button.delegate eq fxButton             // true: same underlying object
```

Whenever ScalaFX lacks a method you need, call it on `delegate`.

### Implicit conversions

ScalaFX automatically converts between the two worlds.
The direction matters:

- **ScalaFX to JavaFX** always works. No import is needed.
  Use a ScalaFX object anywhere a JavaFX API expects a JavaFX object.
- **JavaFX to ScalaFX** may need an import:

  ```scala 3
  import scalafx.Includes.*
  ```

```scala 3
import javafx.scene.control as jfxc
import scalafx.Includes.*
import scalafx.scene.control.Label

val label = new Label("Hi")

val fxLabel: jfxc.Label = label    // ScalaFX -> JavaFX (works without the import)
val sfxLabel: Label     = fxLabel  // JavaFX -> ScalaFX (needs `scalafx.Includes.*`)
```

If the compiler says *Found: `javafx...`, Required: `scalafx...`*, you are missing this import.
Most applications add import `scalafx.Includes.*` to every file that builds UI.

`Includes` is a bundle of smaller traits, such as `scalafx.scene.control.ControlIncludes`.
You can import one of those instead if you want a narrower import.

Conversions can create a new wrapper each time.
Wrappers with the same `delegate` are equal, so compare them with `==`.

### Properties instead of getters and setters

JavaFX exposes every attribute as three methods: a getter, a setter, and a property accessor.
ScalaFX collapses them into one name. 
For instance, instead of JavaFX: `label.setText`, `label.getText`, `label.textProperty`, in ScalaFX you can use `label.text`.

| JavaFX                  | ScalaFX                    |
|-------------------------|----------------------------|
| `label.setText("Hi")`   | `label.text = "Hi"`        |
| `label.getText()`       | `label.text.value`         |
| `label.textProperty()`  | `label.text`               |

Note that `label.text` is the *property*, not a `String`.
This does not compile:

```scala 3
val s: String = label.text        // error: found StringProperty, required String
val s: String = label.text.value  // OK
```

The assignment form `label.text = "Hi"` is what makes the ScalaFX's hierarchical pattern work.
Inside the body of `new Label:`, a plain `text = "Hi"` sets the label's text.

```scala 3
val label = new Label:
  text = "Hi"
  textFill = Color.Red
```

Properties are covered in detail in [Properties and Bindings](04-properties-and-bindings.md).

### Event handlers are Scala functions

With `scalafx.Includes.*` imported, an event handler is a plain Scala function.
Use `_ =>` when you do not need to access the `event` object in the handler.

```scala 3
button.onAction = _ => println("Clicked")
```

### Collections

JavaFX uses `ObservableList`.
ScalaFX presents it as an `ObservableBuffer`, which is a Scala mutable buffer that also notifies listeners. 
ScalaFX collections aim to mirror Scala collections; similar operators and methods are supported. 
For instance, when adding an element to a collection, we can use `+=` operator or `clear()` method, as in other mutable Scala collections. For example:

```scala 3
val box = new VBox
box.children += new Label("First")
box.children += new Label("Second")
box.children.clear()
```

You can assign a single node or a collection of nodes: `children = Seq(a, b, c)`.
Observable collections are covered in the *Working with Data* section.

### Mixing JavaFX and ScalaFX

A ScalaFX scene graph can contain plain JavaFX nodes.
This is useful for third-party JavaFX controls without a ScalaFX wrapper.
With `scalafx.Includes.*` imported, the conversion is automatic.
Give the collection an explicit element type so the compiler knows which conversion to apply:

```scala 3
import javafx.scene.control as jfxc
import scalafx.Includes.*
import scalafx.scene.Node
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox

val box = new VBox:
  children = Seq[Node](
    new Label("A ScalaFX label"),
    new jfxc.Label("A plain JavaFX label")
  )
```

The opposite also works.
Any JavaFX API that expects a JavaFX `Node` accepts a ScalaFX `Node` as it is.

### Summary

- ScalaFX classes wrap JavaFX classes. The JavaFX object is a `delegate`.
- ScalaFX converts to JavaFX automatically. To go from JavaFX to ScalaFX, import `scalafx.Includes.*`.
- Getters, setters, and property accessors become one name: `text`, `width`, `title`.
- Everything you know about JavaFX still applies.
