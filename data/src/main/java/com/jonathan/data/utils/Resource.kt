package com.jonathan.data.utils

sealed class Resource<out T>(
    open val value: T?,
) {
    data class Success<out T>(
        override val value: T,
    ) : Resource<T>(value) {
        override fun toString(): String = "✔ $value"
    }

    data class Error<out T>(
        val error: String,
        override val value: T?,
    ) : Resource<T>(value) {
        override fun toString(): String = "x $value; error=$error"
    }
}
