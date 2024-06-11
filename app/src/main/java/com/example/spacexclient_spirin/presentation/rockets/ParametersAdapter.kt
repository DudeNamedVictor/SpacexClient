package com.example.spacexclient_spirin.presentation.rockets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexclient_spirin.databinding.ParametersItemBinding


class ParametersAdapter :
    ListAdapter<RocketsParametersUI, ParametersViewHolder>(ParametersEntityDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParametersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ParametersItemBinding.inflate(inflater, parent, false)

        return ParametersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParametersViewHolder, position: Int) {
        holder.binding.apply {
            value.text = currentList[position].value
            units.text = currentList[position].unit
        }
    }

    class ParametersEntityDiffUtil : DiffUtil.ItemCallback<RocketsParametersUI>() {
        override fun areItemsTheSame(
            oldItem: RocketsParametersUI,
            newItem: RocketsParametersUI
        ): Boolean = oldItem.value == newItem.value && oldItem.unit == newItem.unit

        override fun areContentsTheSame(
            oldItem: RocketsParametersUI,
            newItem: RocketsParametersUI
        ): Boolean = oldItem.value == newItem.value && oldItem.unit == newItem.unit
    }

}

class ParametersViewHolder(val binding: ParametersItemBinding) : RecyclerView.ViewHolder(binding.root)