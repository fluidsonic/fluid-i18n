package io.fluidsonic.i18n.data


@Suppress("NOTHING_TO_INLINE")
internal actual inline fun <T> atomicLazy(noinline initializer: () -> T): Lazy<T> =
	kotlin.native.concurrent.atomicLazy(initializer)
