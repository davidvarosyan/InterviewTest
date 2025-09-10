package com.varosyan.presenter.common

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class StateReady<T>(val data: T) : UiState<T>
    data class Error(val exception: Exception) : UiState<Nothing>
}