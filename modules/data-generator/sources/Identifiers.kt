package io.fluidsonic.i18n.data.generator

import io.fluidsonic.cldr.*
import io.fluidsonic.locale.*


internal object Identifiers {

	val languageTags: List<LanguageTag> = Cldr.localeIds
		.map { localeId ->
			when (localeId) {
				"root" -> null
				else -> localeId.replace('_', '-')
			}
		}
		.sortedWith(nullsFirst())
		.map { it?.let(LanguageTag::parse) ?: Locale.root.toLanguageTag() }


	private val indexByLanguage: Map<String?, Int> = languageTags
		.mapTo(mutableSetOf<String?>().also { it.add(null) }) { tag -> tag.language?.takeIf { it != LanguageTag.undeterminedPrefix } }
		.also { languages ->
			languages.forEach { language ->
				check(language == null || language.length in 2 .. 3) { "Invalid language: $language" }
			}
		}
		.mapIndexed { index, language -> language to index }
		.toMap()


	private val indexByRegion: Map<String?, Int> = languageTags
		.mapTo(mutableSetOf<String?>().also { it.add(null) }) { it.region }
		.plus(Cldr.regionIds.sorted())
		.sortedWith(nullsFirst())
		.also { regions ->
			regions.forEach { language ->
				check(language == null || language.length in 2 .. 3) { "Invalid region: $language" }
			}
		}
		.mapIndexed { index, region -> region to index }
		.toMap()


	private val indexByScript: Map<String?, Int> = languageTags
		.mapTo(mutableSetOf<String?>().also { it.add(null) }) { it.script }
		.mapIndexed { index, script -> script to index }
		.toMap()


	private val indexByVariant: Map<String?, Int> = languageTags
		.filter { it.variants.isNotEmpty() }
		.mapTo(mutableSetOf<String?>().also { it.add(null) }) { it.variants.single() }
		.mapIndexed { index, variant -> variant to index }
		.toMap()


	val languageIds = indexByLanguage.keys.toList()
	val regionIds = indexByRegion.keys.toList()
	val scriptIds = indexByScript.keys.toList()
	val variantIds = indexByVariant.keys.toList()


	fun indexByLanguage(language: String?) =
		indexByLanguage.getOrElse(language) { error("Cannot find language: $language") }


	fun indexByRegion(region: String?) =
		indexByRegion.getOrElse(region) { error("Cannot find region: $region") }


	fun indexByScript(script: String?) =
		indexByScript.getOrElse(script) { error("Cannot find script: $script") }


	fun indexByVariant(variant: String?) =
		indexByVariant.getOrElse(variant) { error("Cannot find variant: $variant") }
}
