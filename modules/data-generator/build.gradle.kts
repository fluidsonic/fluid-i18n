import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
	`java-gradle-plugin`
	kotlin("jvm") version "1.5.10"
	id("com.github.ben-manes.versions") version "0.39.0"
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
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform(kotlin("bom")))
	implementation(kotlin("gradle-plugin"))
	implementation("com.squareup:kotlinpoet:1.7.2")

	api("io.fluidsonic.cldr:fluid-cldr:0.9.3-37")
	api("io.fluidsonic.locale:fluid-locale:0.10.0")
}

tasks.withType<KotlinCompile> {
	sourceCompatibility = "1.8"
	targetCompatibility = "1.8"

	kotlinOptions.apiVersion = "1.4"
	kotlinOptions.jvmTarget = "1.8"
	kotlinOptions.languageVersion = "1.4"
}
