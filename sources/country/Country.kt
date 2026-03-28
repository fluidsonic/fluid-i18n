package io.fluidsonic.i18n

import io.fluidsonic.country.*
import io.fluidsonic.i18n.data.*
import io.fluidsonic.locale.*


/** Returns the English name of this country. */
public val Country.name: String
	get() = name(Locale.english) ?: code.toString()


/**
 * Returns the localized name of this country for the given [locale].
 *
 * @return `null` if no name is available for [locale].
 */
public fun Country.name(locale: Locale): String? {
	val query = indexForRegion(code.toString())

	locale.lookup { language, script, region, variant ->
		localizedNameForRegion_normal(query = query, language = language, script = script, region = region, variant = variant)?.let { return it }
	}

	return null
}


/** Returns the short English name of this country (e.g. "US" for United States), or `null` if unavailable. */
public val Country.shortName: String?
	get() = shortName(Locale.english)


/**
 * Returns the localized short name of this country for the given [locale].
 *
 * @return `null` if no short name is available for [locale].
 */
public fun Country.shortName(locale: Locale): String? {
	val query = indexForRegion(code.toString())

	locale.lookup { language, script, region, variant ->
		localizedNameForRegion_short(query = query, language = language, script = script, region = region, variant = variant)?.let { return it }
	}

	return null
}


/** Returns the English variant name of this country (e.g. "Ivory Coast" for Cote d'Ivoire), or `null` if unavailable. */
public val Country.variantName: String?
	get() = variantName(Locale.english)


/**
 * Returns the localized variant name of this country for the given [locale].
 *
 * @return `null` if no variant name is available for [locale].
 */
public fun Country.variantName(locale: Locale): String? {
	val query = indexForRegion(code.toString())

	locale.lookup { language, script, region, variant ->
		localizedNameForRegion_variant(query = query, language = language, script = script, region = region, variant = variant)?.let { return it }
	}

	return null
}
