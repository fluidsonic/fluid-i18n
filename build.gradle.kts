import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "3.0.0"
}

fluidLibrary(name = "i18n", version = "0.14.0")

fluidLibraryModule(description = "Kotlin multiplatform internationalization library") {
	targets {
		common {
			dependencies {
				implementation(fluid("country", "0.14.0"))
				implementation(fluid("currency", "0.14.0"))
				implementation(project(":fluid-i18n-data-identifiers"))
				implementation(project(":fluid-i18n-data-regions"))

				api(fluid("locale", "0.14.0"))
			}
		}

		@Suppress("DEPRECATION") js()
		jvm()
	}
}
