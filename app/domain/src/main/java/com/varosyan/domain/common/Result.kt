package com.varosyan.domain.common

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()

    inline fun <R> fold(
        onSuccess: (T) -> R, onError: (Throwable) -> R
    ): R = when (this) {
        is Success -> onSuccess(data)
        is Error -> onError(exception)
    }

}

inline fun <T> Result<T>.getOrNull(): T? = (this as? Result.Success)?.data


inline suspend fun <T> safeCall(action: () -> T): Result<T> {
    return try {
        Result.Success(action())
    } catch (ex: Exception) {
        Result.Error(ex)
    }
}