package io.fluidsonic.i18n.data.generator

import com.squareup.kotlinpoet.*
import java.nio.file.*


public object IdentifierFileGenerator {

	public fun generate(destination: Path) {
		val languages = Identifiers.languageIds.filterNotNull()
		val languages2 = languages.filter { it.length == 2 }
		val languages3 = languages.filter { it.length == 3 }

		val regions = Identifiers.regionIds.filterNotNull()
		val regions2 = regions.filter { it.length == 2 }
		val regions3 = regions.filter { it.length == 3 }

		Files.newOutputStream(destination).writer().use { writer ->
			FileSpec.builder(packageName = "io.fluidsonic.i18n.data", fileName = destination.fileName.toString())
				.addComment(Generation.fileComment)
				.addFunction(FunSpec.builder("indexForLanguage")
					.addParameter("language", KotlinTypes.stringNullable)
					.returns(KotlinTypes.int)
					.addCode(buildCodeBlock {
						beginControlFlow("if (language == null)")
						run {
							add("return %L\n", Identifiers.indexByLanguage(null))
						}
						endControlFlow()

						beginControlFlow("return when (language.length)")
						run {
							beginControlFlow("2 -> when (language[0])")
							run {
								languages2.groupBy { it[0] }.forEach { (character, sublanguages) ->
									beginControlFlow("'%L' -> when (language[1])", character)
									run {
										sublanguages.forEach { sublanguage ->
											add("'%L' -> %L\n", sublanguage[1], Identifiers.indexByLanguage(sublanguage))
										}
										add("else -> -1\n")
									}
									endControlFlow()
								}
								add("else -> -1\n")
							}
							endControlFlow()

							beginControlFlow("3 -> when (language[0])")
							run {
								languages3.groupBy { it[0] }.forEach { (character, sublanguages) ->
									beginControlFlow("'%L' -> when (language[1])", character)
									run {
										sublanguages.groupBy { it[1] }.forEach { (character2, sublanguages2) ->
											beginControlFlow("'%L' -> when (language[2])", character2)
											run {
												sublanguages2.forEach { sublanguage2 ->
													add("'%L' -> %L\n", sublanguage2[2], Identifiers.indexByLanguage(sublanguage2))
												}
												add("else -> -1\n")
											}
											endControlFlow()
										}
										add("else -> -1\n")
									}
									endControlFlow()
								}
								add("else -> -1\n")
							}
							endControlFlow()
							add("else -> -1\n")
						}
						endControlFlow()
					})
					.build()
				)
				.addFunction(FunSpec.builder("indexForRegion")
					.addParameter("region", KotlinTypes.stringNullable)
					.returns(KotlinTypes.int)
					.addCode(buildCodeBlock {
						beginControlFlow("if (region == null)")
						run {
							add("return %L\n", Identifiers.indexByRegion(null))
						}
						endControlFlow()

						beginControlFlow("return when (region.length)")
						run {
							beginControlFlow("2 -> when (region[0])")
							run {
								regions2.groupBy { it[0] }.forEach { (character, subregions) ->
									beginControlFlow("'%L' -> when (region[1])", character)
									run {
										subregions.forEach { subregion ->
											add("'%L' -> %L\n", subregion[1], Identifiers.indexByRegion(subregion))
										}
										add("else -> -1\n")
									}
									endControlFlow()
								}
								add("else -> -1\n")
							}
							endControlFlow()

							beginControlFlow("3 -> when (region)")
							run {
								regions3.forEach { region ->
									add("%S -> %L\n", region, Identifiers.indexByRegion(region))
								}
								add("else -> -1\n")
							}
							endControlFlow()
							add("else -> -1\n")
						}
						endControlFlow()
					})
					.build()
				)
				.addFunction(FunSpec.builder("indexForScript")
					.addParameter("script", KotlinTypes.stringNullable)
					.returns(KotlinTypes.int)
					.addCode(buildCodeBlock {
						beginControlFlow("return when (script)")
						run {
							Identifiers.scriptIds.forEach { script ->
								add("%S -> %L\n", script, Identifiers.indexByScript(script))
							}
							add("else -> -1\n")
						}
						endControlFlow()
					})
					.build()
				)
				.addFunction(FunSpec.builder("indexForVariant")
					.addParameter("variant", KotlinTypes.stringNullable)
					.returns(KotlinTypes.int)
					.addCode(buildCodeBlock {
						beginControlFlow("return when (variant)")
						run {
							Identifiers.variantIds.forEach { variant ->
								add("%S -> %L\n", variant, Identifiers.indexByVariant(variant))
							}
							add("else -> -1\n")
						}
						endControlFlow()
					})
					.build()
				)
				.build()
				.writeTo(writer)
		}
	}
}
