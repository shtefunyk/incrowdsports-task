package com.incrowdsports.task.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.incrowdsports.task.model.data.Fixture
import com.incrowdsports.task.databinding.FixtureLayoutBinding

class FixtureListAdapter(private val itemClickListener: (item: Fixture) -> Unit) : ListAdapter<Fixture, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FixtureViewHolder.create(parent, itemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FixtureViewHolder).bind(getItem(position))
    }

    private class FixtureViewHolder(private val binding: FixtureLayoutBinding,
                                    private val itemClickListener: (item: Fixture) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Fixture) {
            binding.root.setOnClickListener { itemClickListener.invoke(item) }
            binding.competition.text = item.competition
            binding.period.text = item.period
            binding.venue.text = item.venue.name
            binding.homeName.text = item.homeTeam.name
            binding.homeScore.text = item.homeTeam.score
            binding.awayName.text = item.awayTeam.name
            binding.awayScore.text = item.awayTeam.score
        }

        companion object {
            fun create(parent: ViewGroup, itemClickListener: (item: Fixture) -> Unit)
                = FixtureViewHolder(FixtureLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), itemClickListener)
        }
    }

    private companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Fixture>() {
            override fun areItemsTheSame(oldItem: Fixture, newItem: Fixture): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Fixture, newItem: Fixture): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

}