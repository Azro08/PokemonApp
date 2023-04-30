package com.azrosk.ui.pokemonlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azrosk.ui.databinding.PokemonViewHolderBinding
import com.azrosk.ui.pokemonlist.model.Pokemon

class PokemonsRvAdapter(
    private var pokemonsList: List<Pokemon>,
    private val listener: (pokemon: Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonsRvAdapter.MyViewHolder>() {

    class MyViewHolder(
        listener: (pokemon: Pokemon) -> Unit,
        private var binding: PokemonViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var pokemon: Pokemon? = null
        fun bind(myPokemon: Pokemon) {
            binding.textViewPokemonName.text = myPokemon.name
            pokemon = myPokemon
        }

        init {
            binding.cardView.setOnClickListener { listener(pokemon!!) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            listener,
            PokemonViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(pokemonsList[position])
    }

    override fun getItemCount(): Int {
        return pokemonsList.size
    }
}
