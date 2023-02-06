package io.ui.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import daniel.lop.io.marvelappstarter.R
import daniel.lop.io.marvelappstarter.databinding.FragmentDetailsCharacterBinding
import daniel.lop.io.marvelappstarter.databinding.FragmentFavoriteCharacterBinding
import io.data.model.character.CharacterModel
import io.ui.adapters.ComicAdapter
import io.ui.base.BaseFragment
import io.ui.state.ResourceState
import io.util.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailsCharacterFragment :
    BaseFragment<FragmentDetailsCharacterBinding, DetailsCharacterViewModel>(){

    override val viewModel: DetailsCharacterViewModel by viewModels()

    private val args: DetailsCharacterFragmentArgs by navArgs()
    private val comicAdapter by lazy { ComicAdapter() }
    private lateinit var characterModel: CharacterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterModel = args.character
        viewModel.fetch(characterModel.id)
        setupRecycleView()
        onLoadCharacter(characterModel)
        collectOberserver()
        descriptionsCharacter()
    }

    private fun descriptionsCharacter() {
        binding.tvDescriptionCharacterDetails.setOnClickListener {
            onShowDiaload(characterModel)
        }
    }

    private fun onShowDiaload(characterModel: CharacterModel) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(characterModel.name)
            .setMessage(characterModel.description)
            .setNegativeButton(getString(R.string.close_dialog)){dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun collectOberserver() = lifecycleScope.launch{
        viewModel.details.collect { result ->
            when(result){
                is ResourceState.Sucess -> {
                    binding.progressBarDetail.hide()
                    result.data?.let{values ->
                        if(values.data.result.isNotEmpty()){
                            comicAdapter.comics = values.data.result.toList()
                        }else{
                            toast(getString(R.string.empty_list_comics))
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressBarDetail.hide()
                    result.message?.let{message ->
                        Timber.tag("DetailsCharacter").e("Error ->  $message")
                        toast(getString(R.string.an_error_occurred))
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressBarDetail.show()

                }
                else -> {}
            }
        }
    }

    private fun onLoadCharacter(characterModel: CharacterModel) = with(binding) {
        tvNameCharacterDetails.text = characterModel.name
        if(characterModel.description.isEmpty()){
            tvDescriptionCharacterDetails.text = requireContext().getString(R.string.text_description_empty)
        }else{
            tvDescriptionCharacterDetails.text = characterModel.description.limitDescription(100)
        }
        loadImage(imgCharacterDetails, characterModel.thumbnailModel.path,  characterModel.thumbnailModel.extension)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
               viewModel.insert(characterModel)
                toast(getString(R.string.saved_successfully))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecycleView()  = with(binding){
        rvComics.apply{
            adapter = comicAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsCharacterBinding = FragmentDetailsCharacterBinding.inflate(inflater, container, false)
}