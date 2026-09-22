### ScalaFX in a Few Lines 

```scala 3
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, Slider, TextField}
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

object BindingDemo extends JFXApp3:

  override def start(): Unit =
    val nameField = new TextField:
      promptText = "Your name"

    val greeting = new Label:
      text <== nameField.text.map(name => s"Hello, $name!")

    val slider = new Slider(0, 100, 10)

    val bar = new Rectangle:
      height = 20
      fill = Color.SteelBlue
      width <== slider.value * 2.5

    val okButton = new Button("OK"):
      disable <== nameField.text.isEmpty

    stage = new JFXApp3.PrimaryStage:
      title = "Binding demo"
      scene = new Scene:
        root = new VBox:
          spacing = 10
          padding = Insets(20)
          children = Seq(nameField, greeting, slider, bar, okButton)
```

![04-binding-demo.png](02-core-concepts/04-binding-demo.png)

Four ideas, one small program:

- **Hierarchical syntax** — the stage, scene, and layout nest the way they render: `Stage` contains
  `Scene`, `Scene` contains `VBox`, `VBox` contains its children.
- **Natural bind expressions** — `<==` keeps the greeting, the bar, and the button in sync with the
  text field and slider. No listeners, no manual updates.
- **Fully type-safe APIs** — `nameField.text` is a `String` property end to end; the compiler
  checks it, an IDE completes it.
- **Seamless interoperability** — `Color`, `Rectangle`, `Slider`, and `Button` are thin ScalaFX
  wrappers over `javafx.scene.*`. Mix in plain JavaFX anywhere you need to.

This is the whole program — no boilerplate trimmed. See it explained line by line in
**[Properties and Bindings](02-core-concepts/04-properties-and-bindings.md)**, or start from the
beginning with **[For the Impatient](01-getting-started/01-for-the-impatient.md)**.
