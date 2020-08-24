package io.fluidsonic.i18n

import io.fluidsonic.locale.*


private val _english = Locale.forLanguageTag("en")


internal val Locale.Companion.english
	get() = _english
