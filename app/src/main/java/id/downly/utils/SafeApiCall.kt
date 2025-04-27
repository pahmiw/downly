package id.downly.utils

import java.io.IOException
import retrofit2.HttpException
import retrofit2.Response

/**
 * @Author Ahmad Pahmi Created on April 2025
 */

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Either<T> {
    return try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                Either.Success(it)
            } ?: Either.Error("Empty Body", response.code())
        } else {
            Either.Error(response.message(), response.code())
        }
    } catch (e: HttpException) {
        Either.Error(e.message(), e.code())
    } catch (e: IOException) {
        Either.Error("Network error: ${e.localizedMessage}")
    } catch (e: Exception) {
        Either.Error("Unexpected error: ${e.localizedMessage}")
    }
}