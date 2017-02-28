---
layout: docs
title: Dialogs and Alerts
permalink: "/docs/dialogs_and_alerts/"
---

ScalaFX 8.0.40 added support for Alerts and Dialogs. The Dialog API allows for opening a dialog window and returning input from the user. The result can be as simple as the type of button used to close the dialog. A custom dialog allows for returning an arbitrary result.


Simple Alerts
-------------

There are several predefined dialogs called alerts that can be easily presented to the user.

### Information

The simplest alert can be shown with a single line of code:

``` scala
 new Alert(AlertType.Information, "Hello Dialogs!!!").showAndWait()
```

![Simple information alert]({{ site.url }}/img/dialogs/simple_information_alert.png)


Typically, you will customize it a bit more:

``` scala
new Alert(AlertType.Information) {
  initOwner(stage)
  title = "Information Dialog"
  headerText = "Look, an Information Dialog."
  contentText = "I have a great message for you!"
}.showAndWait()
```

![Information alert]({{ site.url }}/img/dialogs/information_alert.png)


`initOwner()` specifies the owner for a dialog. Its use is not required, but it is considered good style. Setting the owner for a dialog allows the dialog to use the same icon as the owner and, for modal dialogs, block the parent when the dialog is shown.

Here are some more ScalaFX examples of Alerts based on ones presented in the [JavaFX Dialogs] blog post.


### Warning

Warning dialog:

``` scala
new Alert(AlertType.Warning) {
  initOwner(stage)
  title = "Warning Dialog"
  headerText = "Look, an Warning Dialog."
  contentText = "Careful with the next step!"
}.showAndWait()
```

![Warning alert]({{ site.url }}/img/dialogs/warning_alert.png)


### Error

Error dialog:

``` scala
new Alert(AlertType.Error) {
  initOwner(stage)
  title = "Error Dialog"
  headerText = "Look, an Error Dialog."
  contentText = "Ooops, there was an error!"
}.showAndWait()
```

![Error alert]({{ site.url }}/img/dialogs/error_alert.png)


### Confirmation

Alerts and dialogs can be used to query the user for information. Every alert returns the type of the button that was pressed to close the dialog. The simplest form of a query is a confirmation dialog that indicates whether the user pressed `OK` or `Cancel`. Strictly speaking, a dialog returns an `Option` containing the type of button pressed or `None`.

``` scala
// Create and show confirmation alert
val alert = new Alert(AlertType.Confirmation) {
  initOwner(stage)
  title = "Confirmation Dialog"
  headerText = "Look, a Confirmation Dialog."
  contentText = "Are you ok with this?"
}

val result = alert.showAndWait()

// React to user's selectioon
result match {
  case Some(ButtonType.OK) => println("OK")
  case _                   => println("Cancel or closed")
}
```

![Confirmation alert]({{ site.url }}/img/dialogs/confirmation_alert.png)

A dialog can return any content; an example will be shown later. First, let us see how to use custom buttons in an alert.

Alerts with Custom Buttons
--------------------------

We can customize the buttons in an alert by defining `ButtonType` objects and passing them to the Alert's `buttonTypes` property. Notice that we overwrite the content of the property (rather than append to it):

``` scala
    val ButtonTypeOne = new ButtonType("One")
    val ButtonTypeTwo = new ButtonType("Two")
    val ButtonTypeThree = new ButtonType("Three")

    val alert = new Alert(AlertType.Confirmation) {
      initOwner(stage)
      title = "Confirmation Dialog with Custom Actions"
      headerText = "Look, a Confirmation Dialog with Custom Actions."
      contentText = "Choose your option."
      // Note that we override here default dialog buttons, OK and Cancel,
      // with new ones.
      // We could also just add to existing button using `++=`.
      buttonTypes = Seq(
        ButtonTypeOne, ButtonTypeTwo, ButtonTypeThree, ButtonType.Cancel)
    }

    val result = alert.showAndWait()

    result match {
      case Some(ButtonTypeOne)   => println("... user chose \"One\"")
      case Some(ButtonTypeTwo)   => println("... user chose \"Two\"")
      case Some(ButtonTypeThree) => println("... user chose \"Three\"")
      case _ => println("... user chose CANCEL or closed the dialog")
    }

```

![Custom Confirmation alert]({{ site.url }}/img/dialogs/confirmation_custom_alert.png)


Alerts with Custom Content
--------------------------

You are not limited to text in an alert. You can add your custom content. For instance, there is no predefined Alert for showing exceptions, but you can add your own implementation:

``` scala
// Create expandable Exception.
val exceptionText = {
  val ex = new FileNotFoundException("Could not find file blabla.txt")
  val sw = new StringWriter()
  val pw = new PrintWriter(sw)
  ex.printStackTrace(pw)
  sw.toString
}
val label = new Label("The exception stacktrace was:")
 val textArea = new TextArea {
  text = exceptionText
  editable = false
  wrapText = true
  maxWidth = Double.MaxValue
  maxHeight = Double.MaxValue
  vgrow = Priority.Always
  hgrow = Priority.Always
}
val expContent = new GridPane {
  maxWidth = Double.MaxValue
  add(label, 0, 0)
  add(textArea, 0, 1)
}

new Alert(AlertType.Error) {
  initOwner(stage)
  title = "Exception Dialog"
  headerText = "Look, an Exception Dialog."
  contentText = "Could not find file blabla.txt!"
  // Set expandable Exception into the dialog pane.
  dialogPane().expandableContent = expContent
}.showAndWait()
```

![Alert with Custom Content]({{ site.url }}/img/dialogs/exception_alert.png)


Text Input Dialog
-----------------

The `TextInputDialog` is used to obtain simple text input. It works similar to alerts, but it returns an `Option` containing the text entered by the user:

``` scala
val dialog = new TextInputDialog(defaultValue = "walter") {
  initOwner(stage)
  title = "Text Input Dialog"
  headerText = "Look, a Text Input Dialog."
  contentText = "Please enter your name:"
}

val result = dialog.showAndWait()

result match {
  case Some(name) => println("Your name: " + name)
  case None       => println("Dialog was canceled.")
}
```

![Text Input Dialog]({{ site.url }}/img/dialogs/text_input_dialog.png)


Choice Box Dialog
-----------------

A `ChoiceDialog` is used for selecting from a list of available choices. The list can be a collection of arbitrary objects. The choice dialog returns an `Option` containing the item selected by the user.

``` scala
val choices = Seq("a", "b", "c")

val dialog = new ChoiceDialog(defaultChoice = "b", choices = choices) {
  initOwner(stage)
  title = "Choice Dialog"
  headerText = "Look, a Choice Dialog."
  contentText = "Choose your letter:"
}

val result = dialog.showAndWait()

result match {
  case Some(choice) => println("Your choice: " + choice)
  case None         => println("No selection")
}
```

![Choice Box Dialog]({{ site.url }}/img/dialogs/choice_dialog.png)


Custom Dialog
--------------

Custom dialogs can be easily created using the Dialog class. Below is an example that shows a login dialog. The `Result` class defines the result returned by the dialog. The dialog contains a custom graphic, two input fields ("Username" and "Password"), and custom buttons ("Login" and "Cancel").

``` scala
case class Result(username: String, password: String)

// Create the custom dialog.
val dialog = new Dialog[Result]() {
  initOwner(stage)
  title = "Login Dialog"
  headerText = "Look, a Custom Login Dialog"
}

// Set the button types.
val loginButtonType = new ButtonType("Login", ButtonData.OKDone)
dialog.dialogPane().buttonTypes = Seq(loginButtonType, ButtonType.Cancel)

// Create the username and password labels and fields.
val username = new TextField() {
  promptText = "Username"
}
val password = new PasswordField() {
  promptText = "Password"
}

val grid = new GridPane() {
  hgap = 10
  vgap = 10
  padding = Insets(20, 100, 10, 10)

  add(new Label("Username:"), 0, 0)
  add(username, 1, 0)
  add(new Label("Password:"), 0, 1)
  add(password, 1, 1)
}

// Enable/Disable login button depending on whether a username was
// entered.
val loginButton = dialog.dialogPane().lookupButton(loginButtonType)
loginButton.disable = true

// Do some validation (disable when username is empty).
username.text.onChange { (_, _, newValue) =>
  loginButton.disable = newValue.trim().isEmpty
}

dialog.dialogPane().content = grid

// Request focus on the username field by default.
Platform.runLater(username.requestFocus())

// When the login button is clicked, convert the result to
// a username-password-pair.
dialog.resultConverter = dialogButton =>
  if (dialogButton == loginButtonType)
    Result(username.text(), password.text())
  else
    null

val result = dialog.showAndWait()

result match {
  case Some(Result(u, p)) => println("Username=" + u + ", Password=" + p)
  case None               => println("Dialog returned: None")
}
```

![Custom Dialog]({{ site.url }}/img/dialogs/custom_login_dialog.png)


Summary
-------

There are several predefined dialogs and alerts: Information, Warning, Error, Confirmation, Text Input, and Choice. The predefined dialogs allow some level of customization of their content and buttons. Source code for the examples of pre-defined dialogs including customization are in [DialogsDemo]. A completely customized dialog can be created using the `Dialog` class. Source code for a custom dialog is in [LoginDialogDemo].

[JavaFX Dialogs]: http://code.makery.ch/blog/javafx-dialogs-official/
[DialogsDemo]: https://github.com/scalafx/scalafx/blob/master/scalafx-demos/src/main/scala/scalafx/controls/DialogsDemo.scala
[LoginDialogDemo]: https://github.com/scalafx/scalafx/blob/master/scalafx-demos/src/main/scala/scalafx/controls/LoginDialogDemo.scala



