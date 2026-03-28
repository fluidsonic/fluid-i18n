package io.fluidsonic.i18n.data


internal fun <T> atomicLazy(initializer: () -> T): Lazy<T> =
	lazy(initializer)
