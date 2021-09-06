---
layout: docs title: Custom cells permalink: /docs/faq_TableView_with_Custom_cell/
---

### **How to create TableView that displays custom cells containing text and graphics?**

To display custom content in `TableView` you create `TableColumn` and specify what should be displayed
using `cellValueFactory` and how it should be deployed using `cellFactory`.

Let's assume that data for each row in the table are represented by instances of a case class `Person`:

```scala
class Person(firstName_ : String, lastName_ : String, favoriteColor_ : Color) {
  val firstName     = new StringProperty(this, "firstName", firstName_)
  val lastName      = new StringProperty(this, "lastName", lastName_)
  val favoriteColor = new ObjectProperty(this, "favoriteColor", favoriteColor_)
}

```

Say, you want to display the first and last name of a person as text, but you want to show the favorite color as a
colored circle. Displaying columns with text is simple, you just create a new column and provide `cellValueFactory` that
describes how to extract data from `Person` object. `TableColumn` knows how to display text:

```scala
new TableColumn[Person, String] {
  text = "First Name"
  // Cell value is loaded from a `Person` object
  cellValueFactory = {
    _.value.firstName
  }
}
```

Displaying a color circle is not that much more complex, we just need to let the `TableColumn` know that
the `favoriteColor` should be rendered as a graphic of a specific color. This is done defining custom `cellFactory` for
the column. As before, define how to create cell value from `Person` object using `cellValueFactory`.

#### ScalaFX 16.0.0-R25 or newer

Here is how this can be done in ScalaFX 16.0.0-R25. Notice that when defining `cellFactory` we do not need to create a
new cell (as in older versions of ScalaFX), and we do not need to provide how to render empty cells. All boilerplate
code is created behind the scenes by ScalaFX. We only need to customize cell's content using a lambda that provides
already created cell and value that needs to be displayed in that cell:

```scala
new TableColumn[Person, Color] {
  text = "Favorite Color"
  // What should be used as the value of the cell
  cellValueFactory = _.value.favoriteColor
  // How the value should be displayed in the cell
  cellFactory = (cell, color) => {
    cell.graphic = Circle(fill = color, radius = 8)
  }
}
```

Below is a complete example that will display a table with custom cells:
![TableView with custom cells]({{ site.url }}/img/faq_TableView_with_Custom_cell-1.png)

```scala
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

/** Illustrates use of TableColumn CellFactory to do custom rendering of a TableCell. */
object TableWithCustomCellDemo extends JFXApp3 {

  private val characters = ObservableBuffer[Person](
    new Person("Peggy", "Sue", "555-6798", Color.Violet),
    new Person("Rocky", "Raccoon", "555-6798", Color.GreenYellow),
    new Person("Bungalow ", "Bill", "555-9275", Color.DarkSalmon)
    )

  override def start(): Unit = {
    stage = new PrimaryStage {
      title = "TableView with custom color cell"
      scene = new Scene {
        root = new TableView[Person](characters) {
          columns ++= Seq(
            new TableColumn[Person, String] {
              text = "First Name"
              cellValueFactory = _.value.firstName
            },
            new TableColumn[Person, String]() {
              text = "Last Name"
              cellValueFactory = _.value.lastName
            },
            new TableColumn[Person, Color] {
              text = "Favorite Color"
              // What should be used as the value of the cell
              cellValueFactory = _.value.favoriteColor
              // How the value should be displayed in the cell
              cellFactory = (cell, color) => {
                cell.graphic = Circle(fill = color, radius = 8)
              }
            }
            )
        }
      }
    }
  }
}

```

You can find the code for this and other examples
in [scalafx-demos](https://github.com/scalafx/scalafx/tree/master/scalafx-demos/src/main/scala/scalafx/controls/tableview)
.

#### ScalaFX 16.0.0-R24 and older

Before 16.0.0-R25 the process of creating a custom cellFactory was a bit more tedious. You needed to create a new
instance of a `TableCell` and handle the cases when cell content was empty (or `null`).

```scala
new TableColumn[Person, Color] {
  text = "Favorite Color"
  // What should be used as the value of the cell
  cellValueFactory = _.value.favoriteColor
  // How the value should be displayed in the cell
  cellFactory = { _ =>
    new TableCell[Person, Color] {
      item.onChange { (_, _, newColor) =>
        graphic =
          if (newColor != null)
            Circle(fill = color, radius = 8)
          else
            null
      }
    }
  }
}
```

Below is a complete example that will display a table with custom cells:

```scala
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

object TableWithCustomCellDemo extends JFXApp {

  val characters = ObservableBuffer[Person](
    new Person("Peggy", "Sue", Color.Violet),
    new Person("Rocky", "Raccoon", Color.GreenYellow),
    new Person("Bungalow ", "Bill", Color.DarkSalmon)
    )

  stage = new PrimaryStage {
    title = "TableView with custom color cell"
    scene = new Scene {
      content = new TableView[Person](characters) {
        columns ++= Seq(
          new TableColumn[Person, String] {
            text = "First Name"
            cellValueFactory = {
              _.value.firstName
            }
          },
          new TableColumn[Person, String]() {
            text = "Last Name"
            cellValueFactory = {
              _.value.lastName
            }
          },
          new TableColumn[Person, Color] {
            text = "Favorite Color"
            cellValueFactory = {
              _.value.favoriteColor
            }
            // Render the property value when it changes,
            // including initial assignment
            cellFactory = { _ =>
              new TableCell[Person, Color] {
                item.onChange { (_, _, newColor) =>
                  graphic =
                    if (newColor != null)
                      new Circle {
                        fill = newColor
                        radius = 8
                      }
                    else
                      null
                }
              }
            }
          }
          )
      }
    }
  }
}

```


