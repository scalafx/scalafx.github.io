## Tips & Tricks

#### Do not include "media" and "web" dependencies using "CoreIncludes.*" instead of "Includes.*"

> Since version *-R39

If your project excludes javafx-media and/or javafx-web from its build, swap your blanket import `scalafx.Includes.*` for the `scalafx.CoreIncludes.*`:

```scala 3 
// Before — requires javafx-media and javafx-web on the classpath
import scalafx.Includes.*

// After — only needs javafx-base/graphics/controls/fxml/swing
import scalafx.CoreIncludes.*
```

`CoreIncludes` gives you all includes in base/graphics/controls/fxml/swing, minus the ones tied to `javafx.scene.media` and `javafx.scene.web`. If you do use MediaView/MediaPlayer or WebView somewhere, just add the matching trait alongside it in that file, as needed:

```scala 3
import scalafx.CoreIncludes.*

import scalafx.scene.media.MediaIncludes.*   // only where you use media APIs

import scalafx.scene.web.WebIncludes.*       // only where you use WebView
```

Existing code using `scalafx.Includes.*` keeps working unchanged — Includes still aggregates CoreIncludes + MediaIncludes + WebIncludes for backwards compatibility. This is purely opt-in and doesn't change ScalaFX's own dependencies or require any `build.sbt` changes on your end.