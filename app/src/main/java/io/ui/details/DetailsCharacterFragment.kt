package io.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import daniel.lop.io.marvelappstarter.databinding.FragmentFavoriteCharacterBinding
import io.ui.base.BaseFragment

@AndroidEntryPoint
class DetailsCharacterFragment : BaseFragment<FragmentFavoriteCharacterBinding, DetailsCharacterViewModel>(){
    override val viewModel: DetailsCharacterViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteCharacterBinding = FragmentFavoriteCharacterBinding.inflate(inflater, container, false)
}