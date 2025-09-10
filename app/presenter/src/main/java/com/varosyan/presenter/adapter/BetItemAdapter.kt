package com.varosyan.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.varosyan.domain.model.Bet
import com.varosyan.presenter.databinding.ListItemBinding

class BetItemAdapter : ListAdapter<Bet, BetItemAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Bet>() {
        override fun areItemsTheSame(oldItem: Bet, newItem: Bet) =
            oldItem.betType == newItem.betType

        override fun areContentsTheSame(oldItem: Bet, newItem: Bet) =
            oldItem == newItem
    }

    inner class VH(private val b: ListItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: Bet) {
            b.titleView.text = item.betType.name
            b.sellInView.text = "Sell-in: ${item.sellIn}"
            b.oddsView.text = "Odds: ${item.odds}"
            Glide.with(b.imageView).load(item.imageURL).into(b.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inf = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inf, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}