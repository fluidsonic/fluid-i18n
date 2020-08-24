package io.fluidsonic.i18n.data.generator

import io.fluidsonic.locale.*


public fun LanguageTag.toLocaleId(): String =
	when (this) {
		Locale.root.toLanguageTag() -> "root"
		else -> toString().replace('-', '_')
	}
