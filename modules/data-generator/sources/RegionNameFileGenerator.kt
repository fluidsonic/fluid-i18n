package io.fluidsonic.i18n.data.generator

import com.squareup.kotlinpoet.*
import io.fluidsonic.cldr.*
import io.fluidsonic.locale.*
import java.nio.file.*


public object RegionNameFileGenerator {

	public fun generate(destination: Path, alternative: CldrRegionNameAlternative) {
		val languageFunSpecs = Identifiers.languageTags
			.groupBy { it.language }
			.mapNotNull { (language, tags) ->
				generateFunctionForLanguage(
					language = language?.takeIf { it != LanguageTag.undeterminedPrefix },
					tags = tags,
					alternative = alternative
				)
					?.let { language to it }
			}

		Files.newOutputStream(destination).writer().use { writer ->
			FileSpec.builder(packageName = "io.fluidsonic.i18n.data", fileName = destination.fileName.toString())
				.addComment(Generation.fileComment)
				.addFunction(FunSpec.builder("localizedNameForRegion_$alternative")
					.addParameter("query", KotlinTypes.int)
					.addParameter("language", KotlinTypes.int)
					.addParameter("script", KotlinTypes.int)
					.addParameter("region", KotlinTypes.int)
					.addParameter("variant", KotlinTypes.int)
					.returns(KotlinTypes.stringNullable)
					.addCode(buildCodeBlock {
						beginControlFlow("return when (language)")
						run {
							languageFunSpecs.forEach { (language, funSpec) ->
								add(
									"%L·->·%N(query,·script,·region,·variant)\n",
									Identifiers.indexByLanguage(language),
									funSpec.name
								)
							}
							add("else -> null\n")
						}
						endControlFlow()
					})
					.build()
				)
				.apply {
					languageFunSpecs.forEach { (_, funSpec) -> addFunction(funSpec) }
				}
				.build()
				.writeTo(writer)
		}
	}


	private fun generateBlockForNames(names: List<Pair<String, String>>): CodeBlock = buildCodeBlock {
		beginControlFlow("when (query)")
		run {
			names.forEach { (regionId, name) ->
				add("%L·->·%S·//·$regionId\n", Identifiers.indexByRegion(regionId), name)
			}
			add("else -> null\n")
		}
		endControlFlow()
	}


	private fun generateBlockForRegion(region: String?, namesByLanguageTag: List<Pair<LanguageTag, List<Pair<String, String>>>>): CodeBlock? {
		return buildCodeBlock {
			val singleEntry = namesByLanguageTag.singleOrNull()
			if (singleEntry != null) {
				val (tag, names) = singleEntry

				add("//·$tag\n")
				beginControlFlow(
					"%L -> when (script == %L && variant == %L)",
					Identifiers.indexByRegion(region),
					Identifiers.indexByScript(tag.script),
					Identifiers.indexByVariant(tag.variants.singleOrNull())
				)
				run {
					add("true -> ")
					add(generateBlockForNames(names))
					add("false -> null\n")
				}
				endControlFlow()
			}
			else {
				add("//·region·=·${region ?: "<none>"}\n")
				beginControlFlow("%L -> when (script)", Identifiers.indexByRegion(region))
				run {
					namesByLanguageTag.groupBy { it.first.script }
						.mapNotNull { (script, namesByLanguageTagForScript) ->
							generateBlockForScript(script, namesByLanguageTag = namesByLanguageTagForScript)
						}
						.ifEmpty { return null }
						.forEach { add(it) }

					add("else -> null\n")
				}
				endControlFlow()
			}
		}
	}


	private fun generateBlockForScript(script: String?, namesByLanguageTag: List<Pair<LanguageTag, List<Pair<String, String>>>>): CodeBlock? {
		return buildCodeBlock {
			val singleEntry = namesByLanguageTag.singleOrNull()
			if (singleEntry != null) {
				val (tag, names) = singleEntry

				add("//·$tag\n")
				beginControlFlow(
					"%L -> when (variant == %L)",
					Identifiers.indexByScript(script),
					Identifiers.indexByVariant(tag.variants.singleOrNull())
				)
				run {
					add("true -> ")
					add(generateBlockForNames(names))
					add("false -> null\n")
				}
				endControlFlow()
			}
			else {
				add("//·script·=·${script ?: "<none>"}\n")
				beginControlFlow("%L -> when (variant)", Identifiers.indexByScript(script))
				run {
					namesByLanguageTag.forEach { (tag, namesByLanguageForVariant) ->
						add("%L -> ", Identifiers.indexByVariant(tag.variants.singleOrNull()))
						add(generateBlockForNames(namesByLanguageForVariant))
						add("false -> null\n")
					}
					add("else -> null\n")
				}
				endControlFlow()
			}
		}
	}


	private fun generateFunctionForLanguage(language: String?, tags: List<LanguageTag>, alternative: CldrRegionNameAlternative): FunSpec? {
		val namesByLanguageTag = tags
			.mapNotNull { tag ->
				Identifiers.regionIds
					.mapNotNull { regionId ->
						regionId?.let {
							Cldr.regionName(localeId = tag.toLocaleId(), regionId = regionId, alternative = alternative)?.let { regionId to it }
						}
					}
					.ifEmpty { null }
					?.let { tag to it }
			}
			.ifEmpty { return null }

		return FunSpec.builder("localizedNameForRegion_${alternative}_${language ?: "root"}")
			.addModifiers(KModifier.PRIVATE)
			.addParameter("query", KotlinTypes.int)
			.addParameter("script", KotlinTypes.int)
			.addParameter("region", KotlinTypes.int)
			.addParameter("variant", KotlinTypes.int)
			.returns(KotlinTypes.stringNullable)
			.addCode(buildCodeBlock {
				val singleEntry = namesByLanguageTag.singleOrNull()
				if (singleEntry != null) {
					val (tag, names) = singleEntry

					beginControlFlow(
						"if (region != %L || script != %L || variant != %L)",
						Identifiers.indexByRegion(tag.region),
						Identifiers.indexByScript(tag.script),
						Identifiers.indexByVariant(tag.variants.singleOrNull())
					)
					run {
						add("return null\n")
					}
					endControlFlow()

					add("return ")
					add(generateBlockForNames(names))
				}
				else {
					beginControlFlow("return when (region)", Identifiers.indexByLanguage(language))
					run {
						namesByLanguageTag.groupBy { it.first.region }
							.mapNotNull { (region, namesByLanguageTagForRegion) ->
								generateBlockForRegion(region, namesByLanguageTag = namesByLanguageTagForRegion)
							}
							.ifEmpty { return null }
							.forEach { add(it) }

						add("else -> null\n")
					}
					endControlFlow()
				}
			})
			.build()
	}
}
