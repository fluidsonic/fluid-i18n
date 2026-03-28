rootProject.name = "fluid-i18n"

pluginManagement {
	repositories {
		mavenLocal()
		gradlePluginPortal()
		mavenCentral()
		maven("https://maven.pkg.github.com/fluidsonic/fluid-cldr") {
			credentials {
				username = "unused"
				password = providers.gradleProperty("GITHUB_PACKAGES_AUTH_TOKEN").orNull
					?: System.getenv("GITHUB_PACKAGES_AUTH_TOKEN")
					?: ""
			}
		}
	}
}

includeBuild("modules/data-generator")

file("modules")
	.listFiles()!!
	.filter(File::isDirectory)
	.filter { it.name != "data-generator" }
	.forEach { directory ->
		val name = directory.name

		include(name)

		project(":$name").apply {
			this.name = "${rootProject.name}-$name"
			this.projectDir = directory
		}
	}
