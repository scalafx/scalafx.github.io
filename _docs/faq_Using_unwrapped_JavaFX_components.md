---
layout: docs
title: Unwrapped components
prev_section: faq_TableView_with_Custom_cell
next_section: faq_Using_Ambiguous_Methods
permalink: /docs/faq_Using_unwrapped_JavaFX_components/
---

###**How can I use JavaFX controls that aren't wrapped by ScalaFX, like controls from JFXtras or ControlsFX?**

You easily can mix JavaFX and ScalaFX controls. Certain ScalaFX features, like binding, will work with "regular" JavaFX controls, like ones from JFXtras or ControlsFX. Just make sure that you bring in the magic using `import scalafx.Includes._`. Here is an example of using a `ListSpinner` from JFXtras:

{% highlight scala %}
import jfxtras.scene.control.ListSpinner
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.scene.control.Label
import scalafx.scene.layout.GridPane
...

  class SomeUIModel {
    val chipMarginPercent = new ObjectProperty[Int](this, "chipMargin", 20)
    ...
  }

  ...

  val model = new SomeUIModel()
  val gp = new GridPane()

  // Create unwrapped JavaFX control
  val marginsListSpinner = new ListSpinner[Int](0, 49)

  // Bind JavaFX property to ScalaFX property using ScalaFX operator
  marginsListSpinner.valueProperty <==> model.chipMarginPercent

  // Insert ScalaFX and JavaFX controls into ScalaFX grid pane
  gp.addRow(row,
    new Label("Chip margin %"),
    marginsListSpinner
  )
{% endhighlight %}



Here is another example. This one is using `Dialog` from ControlsFX. Notice that `parentStage` passed as an argument to ControlsFX method owner is a ScalaFX control (remember `import scalafx.Includes._`):

{% highlight scala %}
import org.controlsfx.dialog.Dialogs
import scalafx.Includes._
import scalafx.stage.Stage

  val parentStage : Stage = ...
  ...

  private def showError(summary: String, message: String) {
    Dialogs.create().
      owner(parentStage).
      title("Error").
      masthead(summary).
      message(message).
      showError()
  }
{% endhighlight %}
