## Anatomy of a ScalaFX Application

A ScalaFX app is built using a hierarchical pattern:

```
JFXApp3 (your application object)
 └── Stage                    — the OS-level window
      └── Scene               — the content container for one window
           └── root Node      — a single container (often a layout pane)
                └── children  — the actual controls/shapes/nested panes
```

`JFXApp3` — your application's entry point. You extend it and implement `start()`; ScalaFX handles JavaFX runtime initialization and shutdown.

`Stage` — represents a window. Most apps only need the `primaryStage`/`stage` provided by `JFXApp3`, but you can create additional `Stage` instances for dialogs, secondary windows, etc.

`Scene` — nested inside a stage, holds one root node and defines scene-wide properties like fill color, stylesheets, and size. A stage can only show one scene at a time, but you can swap scenes on a stage (e.g., to switch "screens" in a simple app).

**Root node/layout pane** — almost always one of the layout panes (`VBox`, `HBox`, `BorderPane`, `GridPane`, `StackPane`, etc. — covered in Section 3). The root determines how its children are arranged.

**Children** — controls (`Button`, `Label`, `TextField`, …), shapes, or further nested layout panes. You build UIs by composing these into a tree, exactly as HTML/CSS or any retained-mode UI toolkit does.

### Project layout convention

A typical ScalaFX project follows standard SBT conventions:

```
my-scalafx-app/
├── build.sbt
├── project/
│   └── build.properties
└── src/
    └── main/
        ├── scala/
        │   └── ... your .scala files ...
        └── resources/
            └── ... CSS, images, FXML if used ...
```

Nothing ScalaFX-specific here — it's the same layout any SBT/Scala project uses.

### Further exploration: where ScalaFX's design comes from

If you're curious about *why* ScalaFX looks the way it does — the nested-builder syntax, the heavy use of implicit conversions — Stephen Chin's 2012 talk ["JavaFX 2 and Scala: Like Milk and Cookies"](https://www.slideshare.net/steveonjava/javafx-2-and-scala-like-milk-and-cookies-33rd-degrees) is the classic reference for the language-design motivations. Modern ScalaFX apps differ in small ways, but the core concepts remain the same. More examples, books and community links are collected in [Further Resources](06-further-resources.md).