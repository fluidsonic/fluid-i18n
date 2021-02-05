package io.fluidsonic.i18n

import io.fluidsonic.country.*
import io.fluidsonic.i18n.data.*
import io.fluidsonic.locale.*


public val Country.name: String
	get() = name(Locale.english) ?: code.toString()


public fun Country.name(locale: Locale): String? {
	val query = indexForRegion(code.toString())

	locale.lookup { language, script, region, variant ->
		localizedNameForRegion_normal(query = query, language = language, script = script, region = region, variant = variant)?.let { return it }
	}

	return null
}


public val Country.shortName: String?
	get() = shortName(Locale.english)


public fun Country.shortName(locale: Locale): String? {
	val query = indexForRegion(code.toString())

	locale.lookup { language, script, region, variant ->
		localizedNameForRegion_short(query = query, language = language, script = script, region = region, variant = variant)?.let { return it }
	}

	return null
}


public val Country.variantName: String?
	get() = variantName(Locale.english)


public fun Country.variantName(locale: Locale): String? {
	val query = indexForRegion(code.toString())

	locale.lookup { language, script, region, variant ->
		localizedNameForRegion_variant(query = query, language = language, script = script, region = region, variant = variant)?.let { return it }
	}

	return null
}
