package com.varosyan.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varosyan.domain.model.Bet
import com.varosyan.domain.usecase.GetBetsUseCase
import com.varosyan.domain.usecase.UpdateBetsUseCase
import com.varosyan.presenter.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BetViewModel(
    private val getBetsUseCase: GetBetsUseCase,
    private val updateBetsUseCase: UpdateBetsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Bet>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Bet>>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getBetsUseCase().fold(
                onSuccess = { _uiState.emit(UiState.StateReady(it)) },
                onError = { _uiState.emit(UiState.Error(it)) }
            )
        }
    }

    fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(UiState.Loading)
            updateBetsUseCase()
            getBetsUseCase().fold(
                onSuccess = { _uiState.emit(UiState.StateReady(it)) },
                onError = { _uiState.emit(UiState.Error(it)) }
            )
        }
    }
}