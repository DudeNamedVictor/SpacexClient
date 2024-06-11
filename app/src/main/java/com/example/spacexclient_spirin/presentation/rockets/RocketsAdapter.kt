package com.example.spacexclient_spirin.presentation.rockets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacexclient_spirin.databinding.RocketItemPageBinding


class RocketsAdapter(
    private var onSettingsClick: (() -> Unit),
    private var onLaunchesClick: ((String, String) -> Unit)
) : ListAdapter<RocketsUIState, RocketViewHolder>(RocketsEntityDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RocketItemPageBinding.inflate(inflater, parent, false)

        return RocketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(root.context)
                .load(currentList[position].flickrImages.random())
                .into(logo)
            rocketName.text = currentList[position].rocketName
            settings.setOnClickListener {
                onSettingsClick.invoke()
            }
            parameters.apply {
                val parametersAdapter = ParametersAdapter()
                addItemDecoration(ParametersItemDecoration(MARGIN_BETWEEN_PARAMETERS))
                layoutManager = LinearLayoutManager(root.context, RecyclerView.HORIZONTAL, false)
                adapter = parametersAdapter
                parametersAdapter.submitList(currentList[position].parameters)
            }
            firstFlightValue.text = currentList[position].firstFlight
            countryValue.text = currentList[position].country
            costPerLaunchValue.text = currentList[position].costPerLaunch
            firstStageEnginesValue.text = currentList[position].firstStage.engines
            firstStageFuelValue.text = currentList[position].firstStage.fuelAmount
            firstStageBurnValue.text = currentList[position].firstStage.burnTime
            secondStageEnginesValue.text = currentList[position].secondStage.engines
            secondStageFuelValue.text = currentList[position].secondStage.fuelAmount
            secondStageBurnValue.text = currentList[position].secondStage.burnTime
            launches.setOnClickListener {
                onLaunchesClick.invoke(currentList[position].rocketId, currentList[position].rocketName)
            }
        }
    }

    class RocketsEntityDiffUtil : DiffUtil.ItemCallback<RocketsUIState>() {
        override fun areItemsTheSame(oldItem: RocketsUIState, newItem: RocketsUIState): Boolean =
            oldItem.rocketId == newItem.rocketId
                    && oldItem.rocketName == newItem.rocketName
                    && oldItem.parameters == newItem.parameters
                    && oldItem.firstFlight == newItem.firstFlight
                    && oldItem.country == newItem.country
                    && oldItem.costPerLaunch == newItem.costPerLaunch
                    && oldItem.firstStage.engines == newItem.firstStage.engines
                    && oldItem.firstStage.fuelAmount.toString() == newItem.firstStage.fuelAmount.toString()
                    && oldItem.firstStage.burnTime.toString() == newItem.firstStage.burnTime.toString()
                    && oldItem.secondStage.engines == newItem.secondStage.engines
                    && oldItem.secondStage.fuelAmount.toString() == newItem.secondStage.fuelAmount.toString()
                    && oldItem.secondStage.burnTime.toString() == newItem.secondStage.burnTime.toString()
                    && oldItem.flickrImages == newItem.flickrImages

        override fun areContentsTheSame(oldItem: RocketsUIState, newItem: RocketsUIState): Boolean =
            oldItem.rocketId == newItem.rocketId
                    && oldItem.rocketName == newItem.rocketName
                    && oldItem.parameters.containsAll(newItem.parameters)
                    && newItem.parameters.containsAll(oldItem.parameters)
                    && oldItem.firstFlight == newItem.firstFlight
                    && oldItem.country == newItem.country
                    && oldItem.costPerLaunch == newItem.costPerLaunch
                    && oldItem.firstStage.engines == newItem.firstStage.engines
                    && oldItem.firstStage.fuelAmount.toString() == newItem.firstStage.fuelAmount.toString()
                    && oldItem.firstStage.burnTime.toString() == newItem.firstStage.burnTime.toString()
                    && oldItem.secondStage.engines == newItem.secondStage.engines
                    && oldItem.secondStage.fuelAmount.toString() == newItem.secondStage.fuelAmount.toString()
                    && oldItem.secondStage.burnTime.toString() == newItem.secondStage.burnTime.toString()
                    && oldItem.flickrImages.containsAll(newItem.flickrImages)
                    && newItem.flickrImages.containsAll(oldItem.flickrImages)
    }

    companion object {
        private const val MARGIN_BETWEEN_PARAMETERS = 12
    }

}

class RocketViewHolder(val binding: RocketItemPageBinding) : RecyclerView.ViewHolder(binding.root)