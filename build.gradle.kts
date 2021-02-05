import io.fluidsonic.gradle.*
import org.jetbrains.kotlin.gradle.plugin.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.18"
}

fluidLibrary(name = "i18n", version = "0.9.2")

fluidLibraryModule(description = "Kotlin multiplatform internationalization library") {
	targets {
		common {
			dependencies {
				implementation(fluid("country", "0.9.4-kotlin-1.5"))
				implementation(fluid("currency", "0.9.4-kotlin-1.5"))
				implementation(project(":fluid-i18n-data-identifiers"))
				implementation(project(":fluid-i18n-data-regions"))

				api(fluid("locale", "0.9.5-kotlin-1.5"))
			}
		}

		darwin {
			withoutWatchosX64() // https://github.com/Kotlin/kotlinx.serialization/pull/1285
		}
		js(KotlinJsCompilerType.BOTH)
		jvm()
	}
}
