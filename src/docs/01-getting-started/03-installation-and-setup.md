## Installation & Setup

### Prerequisites

In this guide, we use the latest version of the software available during the writing. If you are using a different version of ScalaFX, please verify in the [ScalaFX Releases](https://github.com/scalafx/scalafx/releases) notes which Java and Scala versions are supported.

The following versions are used in this guide:

| Component | Version                          |
|-----------|----------------------------------|
| ScalaFX   | `26.0.0-R38`                     |
| JavaFX    | `26`                             |
| Scala     | `3.8.4`                          |
| SBT       | `2.0.5`                          |
| Mill      | `1.1.7`                          |
| JDK       | `24` or later (`26` recommended) |


@:callout(info)
- **ScalaFX** version number contains the supported JavaFX version number, for instance `26.0.0-R38` supports JavaFX 26.
- **JDK**: JavaFX puts restrictions on the required Java version; those are noted in JavaFX release notes and the [ScalaFX Releases](https://github.com/scalafx/scalafx/releases).
- **Scala**: ScalaFX 26.0.0-R38 works with Scala 2.12, 2.13, and Scala 3.3+ — this guide uses Scala 3.8.4.
- **Build tool**: SBT is the most common choice and is what this guide uses; Mill and scala-cli are also covered below. 
@:@

### SBT setup

It is recommended to use SBT v.1.9+ or newer; we assume v.2.x. In general, an SBT project requires two files to set up: `build.sbt` and `project/build.properties`. To work with ScalaFX, a minimal `build.sbt` looks like this:

```scala
name := "my-scalafx-app"
version := "0.1.0"
scalaVersion := "3.8.4"

libraryDependencies += "org.scalafx" %% "scalafx" % "26.0.0-R38"

// Avoids JavaFX double-initialization issues when running via sbt
fork := true
```

In `project/build.properties` you specify which version of SBT to use:

```properties
sbt.version=2.0.5
```

That single `scalafx` dependency is enough for current versions — SBT pulls in the matching JavaFX modules and OS-specific natives for you.

### Mill setup

Mill doesn't do the same automatic resolution, so JavaFX modules need to be listed explicitly:

```scala
//| mill-version: 1.1.7
package build
import mill.*, scalalib.*

object myApp extends ScalaModule {
  def jvmId = "temurin:24" // JavaFX 26 requires JDK >= 24
  def scalaVersion = "3.8.4"

  val javaFxVersion = "26"
  val scalaFxVersion = "26.0.0-R38"
  val javaFxModules = List("base", "controls", "fxml", "graphics", "media", "swing", "web")

  def mvnDeps = Seq(
    mvn"org.scalafx::scalafx:$scalaFxVersion"
  ) ++ javaFxModules.map(mod => mvn"org.openjfx:javafx-$mod:$javaFxVersion")
}
```

@:callout(info)
Mill 1.1.7 also supports declaring `mvnDeps` and `jvmId`/`mill-version` via a plain YAML-flavored `build.mill.yaml` file instead of Scala — either form works; the Scala-file form above is the more common one you'll see in existing ScalaFX examples.
@:@

### scala-cli setup

[scala-cli](https://scala-cli.virtuslab.org/) is the lightest-weight option: no project directory, no build file — dependencies are declared directly at the top of a `.scala` file using `//> using` directives, and `scala-cli` compiles and executes it.

```scala
//> using scala 3.8.4
//> using dep "org.scalafx::scalafx:26.0.0-R38"
```

Run it with:

```bash
scala-cli [file_name].scala
```

@:callout(info)
`scala-cli` is a great fit for quick experiments, single-file demos, and following along with this guide's early examples. 
SBT is more convenient once you're building something with multiple files.
@:@

### IDE setup

- **IntelliJ IDEA**: install the Scala plugin, restart IntelliJ IDEA, then open the project directory directly (IntelliJ detects the `build.sbt`/Mill build automatically). Running the app via the IDE's run configuration works the same as running via the build tool.
- **VS Code**: use the Metals extension; it picks up SBT, Mill, and scala-cli builds via BSP.

### Verifying your setup

Run `sbt run` (or the Mill/scala-cli equivalent) against the Hello World app in the next section. If you see a window, you're set.
