package io.fluidsonic.i18n

import io.fluidsonic.i18n.data.*
import io.fluidsonic.locale.*


private val _english = Locale.forLanguageTag("en")


internal inline fun Locale.lookup(resolve: (language: Int, script: Int, region: Int, variant: Int) -> Unit) {
	val language = indexForLanguage(language?.takeIf { it != LanguageTag.undeterminedPrefix })
	if (language < 0)
		return

	val region = indexForLanguage(region)
	if (region < 0)
		return

	val script = indexForLanguage(script)
	if (script < 0)
		return

	if (variants.size > 1)
		return

	val variant = indexForLanguage(variants.firstOrNull())
	if (variant < 0)
		return

	resolve(language, script, region, variant)

	if (variant != 0)
		resolve(language, script, region, 0)

	if (region != 0)
		resolve(language, script, 0, 0)

	if (script != 0) {
		resolve(language, 0, region, variant)

		if (variant != 0)
			resolve(language, 0, region, 0)

		resolve(language, 0, 0, 0)
	}

	resolve(0, 0, 0, 0)
}


internal val Locale.Companion.english
	get() = _english
