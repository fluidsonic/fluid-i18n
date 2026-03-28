fluid-i18n
============

[![Maven Central](https://img.shields.io/maven-central/v/io.fluidsonic.i18n/fluid-i18n?label=Maven%20Central)](https://search.maven.org/artifact/io.fluidsonic.i18n/fluid-i18n)
[![Tests](https://github.com/fluidsonic/fluid-i18n/workflows/Tests/badge.svg)](https://github.com/fluidsonic/fluid-i18n/actions?workflow=Tests)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.20%20(JVM,%20JS)-blue.svg)](https://github.com/JetBrains/kotlin/releases/v2.3.20)
[![#fluid-libraries Slack Channel](https://img.shields.io/badge/slack-%23fluid--libraries-543951.svg?label=Slack)](https://kotlinlang.slack.com/messages/C7UDFSVT2/)

Kotlin multiplatform internationalization support backed by [Unicode CLDR](http://cldr.unicode.org/) data.  
**Highly experimental. Feel free to contribute!**



Installation
------------

`build.gradle.kts`:

```kotlin
dependencies {
	implementation("io.fluidsonic.i18n:fluid-i18n:0.14.0")
}
```

Usage
-----

### Country names

```kotlin
val usa = Country.forCode("US")
println(usa.name) // United States
println(usa.shortName) // US
println(usa.variantName) // null

val locale = Locale.forLanguage("de")
println(usa.name(locale)) // Vereinigte Staaten
println(usa.shortName(locale)) // USA
println(usa.variantName(locale)) // null

val ivoryCoast = Country.forCode("CI")
println(ivoryCoast.name) // Côte d’Ivoire
println(ivoryCoast.shortName) // null
println(ivoryCoast.variantName) // Ivory Coast
```

Building
--------

Build generated Kotlin files for Unicode CLDR data:

```shell
./gradlew generateCode   # macOS
gradlew generateCode     # Windows
```

License
-------

Apache 2.0
