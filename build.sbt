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
    Some("ScalaFX Docs"))
  .site.internalCSS(Root / "custom.css")
  .site.landingPage(
    logo = Some(Image.internal(Root / "logo-2x.png")),
//    title = Some("ScalaFX"),
    subtitle = Some("A simpler way to use JavaFX in Scala"),
//    titleLinks = Seq(
//      IconLink.external("https://github.com/scalafx/scalafx", HeliumIcon.github, Some("GitHub")),
//      IconLink.external("https://javadoc.io/doc/org.scalafx/scalafx_3", HeliumIcon.api, Some("API")),
//      IconLink.external("https://github.com/scalafx/scalafx/discussions", HeliumIcon.chat, Some("Discussions"))
//    ),
    linkPanel = Some(LinkPanel(
      "Documentation",
      TextLink.internal(Root / "01-getting-started" / "01-for-the-impatient.md", "Getting Started"),
      TextLink.internal(Root / "02-core-concepts" / "01-scala-and-javafx-interop.md", "Core Concepts"),
      TextLink.internal(Root / "03-building-basic-uis" / "01-layout-panes.md", "Building Basic UIs"),
      TextLink.internal(Root / "99-tips-and-tricks" / "01-tips-and-tricks.md", "Tips & Tricks"),
      TextLink.external("https://javadoc.io/doc/org.scalafx/scalafx_3", "ScalaFX API")
    )),
    projectLinks = Seq(
      IconLink.external("https://github.com/scalafx/scalafx", HeliumIcon.github, Some("GitHub")),
      IconLink.external("https://github.com/scalafx/scalafx/discussions", HeliumIcon.chat, Some("Discussions"))
//      TextLink.external("https://github.com/scalafx/scalafx", "GitHub repository"),
//      TextLink.external("https://github.com/scalafx/scalafx/releases", "Release notes"),
//      TextLink.external("https://github.com/scalafx/scalafx/issues", "Issue tracker"),
//      TextLink.external("https://github.com/scalafx/scalafx/discussions", "GitHub discussions")
    ),
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
