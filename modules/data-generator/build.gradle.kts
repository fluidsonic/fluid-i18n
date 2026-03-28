import org.jetbrains.kotlin.gradle.plugin.*

plugins {
	`java-gradle-plugin`
	kotlin("jvm") version "2.3.20"
	id("com.github.ben-manes.versions") version "0.53.0"
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

	compilerOptions {
		apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3)
		jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
	}

	target {
		compilations.named(KotlinCompilation.MAIN_COMPILATION_NAME) {
			defaultSourceSet.kotlin.setSrcDirs(listOf("sources"))
		}
	}
}

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenLocal()
	mavenCentral()
	maven("https://maven.pkg.github.com/fluidsonic/fluid-cldr") {
		credentials {
			username = "unused"
			password = System.getenv("GITHUB_PACKAGES_AUTH_TOKEN")
				?: findProperty("GITHUB_PACKAGES_AUTH_TOKEN") as? String
				?: ""
		}
	}
}

dependencies {
	implementation(platform(kotlin("bom")))
	implementation(kotlin("gradle-plugin"))
	implementation("com.squareup:kotlinpoet:1.18.0")

	api("io.fluidsonic.cldr:fluid-cldr:0.10.0-48")
	api("io.fluidsonic.locale:fluid-locale:0.14.0")
}
