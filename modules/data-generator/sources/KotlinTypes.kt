package io.fluidsonic.i18n.data.generator

import com.squareup.kotlinpoet.*


internal object KotlinTypes {

	val int = INT
	val string = STRING
	val stringNullable = string.copy(nullable = true)
}
