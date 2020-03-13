package com.projecturanus.foodcraft.common.util

import kotlin.reflect.KProperty

/**
 * Ensure a function is called only one time
 * Situations: Registry
 */
class Once(val func: () -> Unit) {
    var called = false
}


operator fun Once.getValue(thisRef: Any?, property: KProperty<*>): Unit {
    if (!called) {
        func()
        called = true
    }
}

fun once(initializer: () -> Unit): Once = Once(initializer)
