import io.fluidsonic.gradle.*
import org.jetbrains.kotlin.gradle.plugin.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.8"
}

fluidLibrary(name = "i18n", version = "0.9.1")

fluidLibraryModule(description = "Kotlin multiplatform internationalization library") {
	targets {
		common {
			dependencies {
				implementation(fluid("country", "0.9.1"))
				implementation(fluid("currency", "0.9.1"))
				implementation(project(":fluid-i18n-data-identifiers"))
				implementation(project(":fluid-i18n-data-regions"))

				api(fluid("locale", "0.9.2"))
			}
		}

		darwin()
		js()
		jvm()
	}
}
