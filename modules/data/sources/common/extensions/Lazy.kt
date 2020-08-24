package io.fluidsonic.i18n.data


internal expect inline fun <T> atomicLazy(noinline initializer: () -> T): Lazy<T>
