package io.fluidsonic.i18n

import io.fluidsonic.country.*
import io.fluidsonic.i18n.data.*
import io.fluidsonic.locale.*


public val Country.name: String
	get() = name(Locale.english) ?: code.toString()


public fun Country.name(locale: Locale): String? =
	countryNameResolver.resolve(code.toString(), locale = locale)


public val Country.shortName: String?
	get() = shortName(Locale.english)


public fun Country.shortName(locale: Locale): String? =
	countryShortNameResolver.resolve(code.toString(), locale = locale)


public val Country.variantName: String?
	get() = variantName(Locale.english)


public fun Country.variantName(locale: Locale): String? =
	countryVariantNameResolver.resolve(code.toString(), locale = locale)

