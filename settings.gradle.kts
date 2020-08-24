rootProject.name = "fluid-i18n"

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
