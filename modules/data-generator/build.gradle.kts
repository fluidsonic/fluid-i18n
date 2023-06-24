import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
	`java-gradle-plugin`
	kotlin("jvm") version "1.8.22"
	id("com.github.ben-manes.versions") version "0.47.0"
}

group = "io.fluidsonic.i18n.data.generator"
version = "1.0.0"

gradlePlugin {
	plugins {
		register("io.fluidsonic.i18n.data.generator") {
			id = "io.fluidsonic.i18n.data.generator"
			implementationClass = "io.fluidsonic.i18n.data.generator.GeneratorPlugin"
		}
	}
}

kotlin {
	explicitApi()

	target {
		compilations.named(KotlinCompilation.MAIN_COMPILATION_NAME) {
			defaultSourceSet.kotlin.setSrcDirs(listOf("sources"))
		}
	}
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform(kotlin("bom")))
	implementation(kotlin("gradle-plugin"))
	implementation("com.squareup:kotlinpoet:1.14.2")

	api("io.fluidsonic.cldr:fluid-cldr:0.9.3-37")
	api("io.fluidsonic.locale:fluid-locale:0.13.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.apiVersion = "1.8"
	kotlinOptions.jvmTarget = "17"
	kotlinOptions.languageVersion = "1.8"
}
