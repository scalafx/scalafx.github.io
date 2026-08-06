enablePlugins(LaikaPlugin)

import laika.ast.Image
import laika.ast.Path.Root
import laika.config.SyntaxHighlighting
import laika.format.Markdown
import laika.helium.Helium
import laika.helium.config.*
import laika.theme.config.Color

scalaVersion := "2.13.18"

laikaExtensions := Seq(Markdown.GitHubFlavor, SyntaxHighlighting)

// Configure theme and metadata
laikaTheme := Helium.defaults
  .site.metadata(title =
    Some("ScalaFX Library Docs"))
  .site.landingPage(
    logo = Some(Image.internal(Root / "logo-2x.png")),
    title = None, //Some("ScalaFX"),
    subtitle = Some("A simpler way to use JavaFX in Scala"),
    linkPanel = Some(LinkPanel(
      "Documentation",
      TextLink.external("/01-getting-started/01-for-the-impatient.html", "For the impatient"),
      TextLink.external("/01-getting-started/02-what-is-scalafx.html", "What is ScalaFX?")
    )),
    teasers = Seq(
      Teaser(
        "Hierarchical Syntax",
        "A programmer-friendly, hierarchical syntax for building up the scene graph."
      ),
      Teaser(
        "Natural Bind Expressions",
        "Rich operator support makes binding expressions read like ordinary code."
      ),
      Teaser(
        "Fully Type-Safe APIs",
        "The Scala compiler checks and infers types across the full API surface."
      ),
      Teaser(
        "Seamless Interoperability",
        "Mix ScalaFX and plain JavaFX controls freely in the same codebase."
      )
    )
  )
  // Enables dark mode support and header toggle button
  .site.darkMode.themeColors(
//    // Laika dark theme
//    primary = Color.hex("A7D4DE"),
//    secondary = Color.hex("F1C47B"),
//    primaryMedium = Color.hex("A7D4DE"),
//    primaryLight = Color.hex("125D75"),
//    text = Color.hex("EEEEEE"),
//    background = Color.hex("064458"),
//    bgGradient = (Color.hex("064458"), Color.hex("197286"))
    // Laika dark theme with desaturated teal
    primary = Color.hex("DDDDDD"), // <-
    secondary = Color.hex("F1C47B"), // (Soft gold/orange for links and structural headlines)
    primaryMedium = Color.hex("DDDDDD"), // <-
    primaryLight = Color.hex("2D2D2D"),
    text = Color.hex("EEEEEE"),
    background = Color.hex("444444"),
    bgGradient = (Color.hex("444444"), Color.hex("727272"))
  )
  .build
