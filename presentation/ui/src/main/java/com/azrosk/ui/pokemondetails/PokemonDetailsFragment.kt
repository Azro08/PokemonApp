package com.azrosk.ui.pokemondetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.azrosk.ui.R
import com.azrosk.ui.databinding.FragmentPokemonDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {
    private val binding by viewBinding(FragmentPokemonDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}
