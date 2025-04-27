package id.downly.utils

/**
 * @Author Ahmad Pahmi Created on April 2025
 */
sealed class Either<out T> {
    data class Success<T>(val data: T) : Either<T>()
    data class Error(val message: String, val code: Int? = null) : Either<Nothing>()
}