import io.fluidsonic.cldr.*
import io.fluidsonic.gradle.*
import io.fluidsonic.i18n.data.generator.*
import org.jetbrains.kotlin.gradle.plugin.*

plugins {
	id("io.fluidsonic.i18n.data.generator") version "1.0.0"
}

fluidLibraryModule(description = "Internationalization data used by fluid-i18n") {
	targets {
		darwin()
		js()
		jvm()
	}
}

tasks.register("generateCode") {
	CldrRegionNameAlternative.values().forEach { alternative ->
		val destination = file("sources/RegionNames_$alternative.generated.kt").toPath()

		outputs.file(destination)

		doLast {
			RegionNameFileGenerator.generate(destination = destination, alternative = alternative)
		}
	}
}
