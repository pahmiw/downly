package id.downly.utils

/**
 * @Author Ahmad Pahmi Created on April 2025
 */

fun Int?.orZero(): Int = this ?: 0

fun Long?.orZero() = this ?: 0

fun Float?.orZero() = this ?: 0f

fun Double?.orZero() = this ?: 0.0

fun Int?.isNullOrZero(): Boolean {
    return this == null || this == 0
}

fun Int?.isZero(): Boolean {
    return this == 0
}

fun Int?.isNotNull(): Boolean {
    return this != null
}

fun Double?.isNullOrZero(): Boolean {
    return this == null || this == 0.0
}

fun Long?.isNullOrZero(): Boolean {
    return this == null || this == 0L
}