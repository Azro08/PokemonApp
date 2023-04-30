package com.azrosk.ui.pokemondetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.azrosk.ui.R
import com.azrosk.ui.databinding.FragmentPokemonDetailsBinding
import com.azrosk.ui.pokemondetails.model.PokemonDetails
import com.azrosk.ui.util.Constants
import com.azrosk.ui.util.ScreenState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {
    private val binding by viewBinding(FragmentPokemonDetailsBinding::bind)

    @Inject
    lateinit var viewModel: PokemonDetailsViewModelApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMenu()
        val pokemonId = arguments?.getString(Constants.POKEMON_ID)
        val pokemonName = arguments?.getString(Constants.POKEMON_NAME)
        if (pokemonId != null && pokemonName != null) {
            viewModelOutputs(pokemonId, pokemonName)
        } else handleLoadingDetailsError(getString(R.string.error_loading_details))
    }

    private fun setMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.item_back -> {
                        findNavController().popBackStack()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun viewModelOutputs(pokemonId: String, pokemonName: String) = with(viewModel) {
        run {
            viewModel.getPokemonDetails(pokemonId, pokemonName)
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    responsePokemonDetails.collect {
                        processPokemonResponseDetails(it)
                    }
                }
            }
        }
    }

    private fun processPokemonResponseDetails(screenState: ScreenState<PokemonDetails?>) {
        when (screenState) {
            is ScreenState.Loading -> {
                setLoadingScreen()
            }

            is ScreenState.Success -> {
                displayDetails(screenState.data)
            }

            is ScreenState.Error -> {
                handleLoadingDetailsError(screenState.message.toString())
            }

        }
    }

    private fun setLoadingScreen() = with(binding) {
        gifImagePokemonDetails.visibility = View.VISIBLE
        constraintLayoutDetails.visibility = View.GONE
        textViewDetailsError.visibility = View.GONE
    }

    private fun handleLoadingDetailsError(msg: String) = with(binding) {
        gifImagePokemonDetails.visibility = View.GONE
        constraintLayoutDetails.visibility = View.GONE
        textViewDetailsError.visibility = View.VISIBLE
        textViewDetailsError.text = msg
    }

    private fun displayDetails(data: PokemonDetails?) = with(binding) {
        if (data != null) {
            val height = data.height.toString() + getString(R.string.cm)
            val weight = data.weight.toString() + getString(R.string.kg)
            gifImagePokemonDetails.visibility = View.GONE
            constraintLayoutDetails.visibility = View.VISIBLE
            textViewName.text = data.name
            textViewType.text = data.type
            textViewHeight.text = height
            textViewWeight.text = weight
            Glide.with(requireActivity()).load(data.imageUrl)
                .into(binding.imageViewPokemon)
        } else {
            gifImagePokemonDetails.visibility = View.GONE
            constraintLayoutDetails.visibility = View.GONE
            textViewDetailsError.visibility = View.VISIBLE
            textViewDetailsError.setText(R.string.error_loading_details)
        }
    }

}
