---
layout: docs
title: History
permalink: "/docs/history/"
---

## 1.0.0-R8 and 8.0.0-R4 / 2014-03-06

### User visible

* Version naming convention change to use R (release) instead of M (Milestone), to signify that releases are stable.
* More fixes for use of code blocks in event handlers (similar to  Issue 102 )
* Fixed  Issue 115 : EventHandlerDelegate#filterEvent should allow to remove created filters.
* Resolved  Issue 118 : Add scaladoc root page.
* Resolved  Issue 117 : Remove obsolete build files and documentation.
* Resolved  Issue 120 : Scene is missing two constructors.
* Some code changes to support Scala 2.11 stricter compiler.

### Contributor visible

* Resolve  Issue 116  - move settings from project/build.scala to build.sbt
* Remove "sbt-idea" plugin since IDEA can now import SBT projects directly.
* SBT is ScalaFX official build system, drop outdated Gradle and Maven files. It is difficult to maintain multiple build system support.
* Fixed  Issue 121 : Build script has some not needed resolvers.
* Update ScalaTest? to v.2.1 - mostly to support testing in Scala 2.11

### v.1.0.0.R8

Tested with Java 1.7.0 u51. All changes mentioned above. Binaries are released for Scala 2.9.3, 2.10, and 2.11.0-RC1.

### v.8.0.0.R4

Tested with early release Java 1.8.0 b129 (final release preview of Java 8). All changes mentioned above plus:

* Improvements to TriangleMeshDemo
* DrawMode, CullFace are now using Scala naming convention for constants.
* Resolved  Issue 92 : SFX-8: Add wrapper for TextFlow and added TextFlowDemo.
* Resolved  Issue 99 : add wrappers for new classes in javafx.scene.layout: Background, BackgroundFill, BackgroundImage, BackgroundPosition, BackgroundRepeat, BackgroundSize, CornerRadii, BorderImage, BorderRepeat, BorderStroke, BorderStrokeStyle, BorderWidths.

Binaries are released for Scala 2.10. Scala 2.11.0-RC1 cannot compile current ScalaFX SFX-8 code yet.

For downloads visit ScalaFX on Sonatype.