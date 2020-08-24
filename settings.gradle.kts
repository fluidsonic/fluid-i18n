rootProject.name = "fluid-i18n"

file("modules")
	.listFiles()!!
	.filter(File::isDirectory)
	.forEach { directory ->
		val name = directory.name

		include(name)

		project(":$name").apply {
			this.name = "${rootProject.name}-$name"
			this.projectDir = directory
		}
	}
