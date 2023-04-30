package com.azrosk.ui.pokemonlist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.azrosk.ui.R
import com.azrosk.ui.databinding.FragmentPokemonListBinding
import com.azrosk.ui.pokemonlist.adapter.PokemonsRvAdapter
import com.azrosk.ui.pokemonlist.model.Pokemon
import com.azrosk.ui.pokemonlist.model.PokemonList
import com.azrosk.ui.util.Constants
import com.azrosk.ui.util.ScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {
    private val binding by viewBinding(FragmentPokemonListBinding::bind)
    private var rvAdapter: PokemonsRvAdapter? = null
    private var limit = 20      //the amount of loaded data with each api request
    private var scrollPosition = 0       //saves recyclerview possession after updating data

    @Inject
    lateinit var viewModel: PokemonsListViewModelApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt("SCROLL_POSITION")
            saveScrollPosition()
        }
        setConnectionStatus()
        viewModelOutputs()
    }

    override fun onResume() {
        super.onResume()
        restoreScrollPosition()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SCROLL_POSITION", scrollPosition)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        val d = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showConfirmationDialog()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, d)
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure you want to exit?")
        builder.setPositiveButton("Yes") { _, _ ->
            requireActivity().finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    @SuppressLint("ResourceAsColor")
    private fun setConnectionStatus() {
        binding.apply {
            if (internetIsConnected()) {
                textViewInternetStatus.text = getString(R.string.online)
                textViewInternetStatus.setTextColor(R.color.green)
            } else {
                textViewInternetStatus.text = getString(R.string.offline)
                textViewInternetStatus.setTextColor(R.color.red)
            }
        }
    }

    private fun viewModelOutputs() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getPokemons(limit)
                responsePokemon.collect {
                    processPokemonResponse(it)
                }
            }
        }
    }

    private fun processPokemonResponse(screenState: ScreenState<PokemonList?>) {
        when (screenState) {
            is ScreenState.Loading -> {
                setLoadingScreen()
            }

            is ScreenState.Success -> {
                displayPokemons(screenState.data)
            }

            is ScreenState.Error -> {
                handleLoadingError(screenState.message.toString())
            }

        }
    }

    private fun setLoadingScreen() = with(binding) {
        gifImagePokemon.visibility = View.VISIBLE
        recyclerViewPokemons.visibility = View.GONE
        textViewError.visibility = View.GONE
    }

    private fun displayPokemons(data: PokemonList?) {
        if (data != null) {
            binding.apply {
                if (data.results.isNotEmpty()) {
                    gifImagePokemon.visibility = View.GONE
                    recyclerViewPokemons.visibility = View.VISIBLE
                    rvAdapter = PokemonsRvAdapter(data.results) { myPokemon ->
                        val pokemonId = getPokemonId(myPokemon, data.results)
                        if (pokemonId == "-1") Toast.makeText(
                            requireContext(),
                            "Error getting pokemon id",
                            Toast.LENGTH_SHORT
                        ).show()
                        else navToPokemonDetails(myPokemon.name, pokemonId)
                    }
                    recyclerViewPokemons.layoutManager = LinearLayoutManager(requireContext())
                    recyclerViewPokemons.setHasFixedSize(true)
                    recyclerViewPokemons.adapter = rvAdapter
                    restoreScrollPosition()
                    setScrollToUpdateData()
                } else handleLoadingError(getString(R.string.error_loading_details))
            }

        } else handleLoadingError(getString(R.string.error_loading_details))
    }

    private fun restoreScrollPosition() {
        binding.recyclerViewPokemons.scrollToPosition(scrollPosition)
    }

    private fun setScrollToUpdateData() {
        binding.recyclerViewPokemons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
                    if (internetIsConnected()) {
                        loadMoreData()
                    }
                }
            }
        })

    }

    private fun loadMoreData() {
        saveScrollPosition()
        setLoadingScreen()
        limit += 20
        viewModelOutputs()
    }

    private fun saveScrollPosition() {
        // save position after updating the adapter
        scrollPosition = (binding.recyclerViewPokemons.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
    }

    private fun handleLoadingError(msg: String) = with(binding) {
        gifImagePokemon.visibility = View.GONE
        recyclerViewPokemons.visibility = View.GONE
        textViewError.visibility = View.VISIBLE
        textViewError.text = msg
    }

    private fun getPokemonId(myPokemon: Pokemon, pokemonsList: List<Pokemon>): String {
        var id = -1
        for ((j, i) in pokemonsList.withIndex()) {
            if (i.name == myPokemon.name) id = j + 1
        }
        return id.toString()
    }

    private fun navToPokemonDetails(pokemonName: String, pokemonId: String) {
        val bundle = bundleOf(
            Pair(Constants.POKEMON_ID, pokemonId),
            Pair(Constants.POKEMON_NAME, pokemonName)
        )
        saveScrollPosition()
        findNavController().navigate(R.id.nav_to_details, bundle)
    }

    private fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

}
