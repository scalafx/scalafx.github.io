---
layout: docs
title: Custom cells
prev_section: quickstart
next_section: faq_Using_unwrapped_JavaFX_components
permalink: /docs/faq_TableView_with_Custom_cell/
---

###**How to create TableView that displays custom cells containing text and graphics?**

To display custom content in `TableView` you create `TableColumn` and specify `cellValueFactory` and in needed `cellFactory`. Let's assume that data for each row in the table are represented by insdtances `Person` class:

{% highlight scala %}
class Person(firstName_ : String, 
             lastName_ : String, 
             favoriteColor_ : Color) {
  val firstName = new StringProperty(this, "firstName", firstName_)
  val lastName = new StringProperty(this, "lastName", lastName_)
  val favoriteColor = new ObjectProperty(this, "favoriteColor", 
                                         favoriteColor_)
}
{% endhighlight %}

Say, you want to display the first and last name of a person as text, but you want to show the favorite color as a circle. Displaying columns with text is simple, you just create a new column and provide `cellValueFactory` that describes how to extract data from `Person` object. `TableColumn` knows how to display text:

{% highlight scala %}
new TableColumn[Person, String] {
  text = "First Name"
  // Cell value is loaded from a `Person` object
  cellValueFactory = { _.value.firstName }
},
{% endhighlight %}

Displaying a color circle is not that much more complex, we just need to let the `TableColumn` know that the `favoriteColor` should be rendered as a graphic of a specific color. This is done defining custom `cellFactory` for the column. The factory creates a graphic when cell value changes. As before define how to create cell value from `Person` object using `cellValueFactory`:

{% highlight scala %}
new TableColumn[Person, Color] {
  text = "Favorite Color"
  // Cell value is loaded from a `Person` object
  cellValueFactory = { _.value.favoriteColor }
  // New circle is created when cell value changes
  cellFactory = { _ => 
    new TableCell[Person, Color] {
      item.onChange { (_, _, newColor) => 
        graphic = new Circle {
          fill = newColor; 
          radius = 8
        }
      }
    }
  }
}
{% endhighlight %}

Below is a complete example that will display a table with custom cells:
![TableView with custom cells]({{ site.url }}/images/faq_TableView_with_Custom_cell-1.png)

{% highlight scala %}
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
        columns ++= List(
          new TableColumn[Person, String] {
            text = "First Name"
            cellValueFactory = { _.value.firstName }
            prefWidth = 100
          },
          new TableColumn[Person, String]() {
            text = "Last Name"
            cellValueFactory = { _.value.lastName }
            prefWidth = 100
          },
          new TableColumn[Person, Color] {
            text = "Favorite Color"
            cellValueFactory = { _.value.favoriteColor }
            // Render the property value when it changes, 
            // including initial assignment
            cellFactory = { _ => 
              new TableCell[Person, Color] {
                item.onChange { (_, _, newColor) => 
                  graphic = new Circle {fill = newColor; radius = 8}
                }
              }
            }
            prefWidth = 100
          }
        )
      }
    }
  }
}

{% endhighlight %}

You can find the code for this example in [scalafx-demos](https://github.com/scalafx/scalafx/tree/master/scalafx-demos/src/main/scala/scalafx/controls/tableview).
