package com.example.spacexclient_spirin.presentation.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacexclient_spirin.databinding.LaunchesItemPageBinding


class LaunchesAdapter :
    ListAdapter<LaunchesUIState, LaunchesAdapter.LaunchesViewHolder>(LaunchesEntityDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LaunchesItemPageBinding.inflate(inflater, parent, false)

        return LaunchesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        holder.binding.apply {
            missionName.text = currentList[position].missionName
            date.text = currentList[position].launchDate
            Glide.with(root.context)
                .load(currentList[position].launchImage)
                .into(resultImage)
        }
    }

    class LaunchesEntityDiffUtil : DiffUtil.ItemCallback<LaunchesUIState>() {
        override fun areItemsTheSame(oldItem: LaunchesUIState, newItem: LaunchesUIState): Boolean =
             oldItem.missionName == newItem.missionName
                    && oldItem.launchDate == newItem.launchDate
                    && oldItem.launchImage == newItem.launchImage

        override fun areContentsTheSame(oldItem: LaunchesUIState, newItem: LaunchesUIState): Boolean =
             oldItem.missionName == newItem.missionName
                    && oldItem.launchDate == newItem.launchDate
                    && oldItem.launchImage == newItem.launchImage

    }

    class LaunchesViewHolder(val binding: LaunchesItemPageBinding) : RecyclerView.ViewHolder(binding.root)

}