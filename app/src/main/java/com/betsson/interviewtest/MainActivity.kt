package com.betsson.interviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.varosyan.domain.model.Bet
import com.varosyan.presenter.adapter.BetItemAdapter
import com.varosyan.presenter.common.UiState
import com.varosyan.presenter.databinding.ActivityMainBinding
import com.varosyan.presenter.viewmodel.BetViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val betViewModel: BetViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var betItemAdapter: BetItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        betItemAdapter = BetItemAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = betItemAdapter
        binding.updateButton.setOnClickListener { betViewModel.update() }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                betViewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Error -> onError()

                        UiState.Loading -> onLoading()

                        is UiState.StateReady<*> -> onSuccess(state.data as List<Bet>)
                    }
                }
            }
        }
    }

    private fun onError() {
        binding.errorView.visibility = View.VISIBLE
        binding.loadingView.visibility = View.GONE
    }

    private fun onLoading() {
        binding.errorView.visibility = View.GONE
        binding.loadingView.visibility = View.VISIBLE
    }

    private fun onSuccess(bets: List<Bet>) {
        binding.errorView.visibility = View.GONE
        binding.loadingView.visibility = View.GONE
        betItemAdapter.submitList(bets)
    }
}