import io.fluidsonic.gradle.*
import io.fluidsonic.i18n.data.generator.*
import org.jetbrains.kotlin.gradle.plugin.*

plugins {
	id("io.fluidsonic.i18n.data.generator") version "1.0.0"
}

fluidLibraryModule(description = "Internationalization data used by fluid-i18n") {
	language {
		withoutExplicitApi() // TODO remove once the new Kotlinpoet release is available
	}

	targets {
		darwin()
		js()
		jvm()
	}
}

tasks.register("generateCode") {
	val destination = file("sources/common/Generated.kt").toPath()

	outputs.file(destination)

	doLast {
		IdentifierFileGenerator.generate(destination = destination)
	}
}
