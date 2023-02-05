package io.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.data.model.character.CharacteModelResponse
import io.data.model.repository.MarvelRepository
import io.ui.state.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchCharacterViewModel @Inject constructor(
    private val repository: MarvelRepository
) : ViewModel(){

    private val _searchCharacter = MutableStateFlow<ResourceState<CharacteModelResponse>>(ResourceState.Empty())
    val searchCharacter : StateFlow<ResourceState<CharacteModelResponse>> = _searchCharacter

    fun fetch(nameStartsWith: String) = viewModelScope.launch{
        safeFeatch(nameStartsWith)
    }

    private suspend fun safeFeatch(nameStartsWith: String) {
        _searchCharacter.value = ResourceState.Loading()
        try{
           val response = repository.list(nameStartsWith)
            _searchCharacter.value = handleResponse(response)
        }catch(t: Throwable){
            when(t){
                is IOException -> _searchCharacter.value = ResourceState.Error("Erro na rede")
                else -> _searchCharacter.value = ResourceState.Error("Erro na conversão")
            }
        }
    }

    private fun handleResponse(response: Response<CharacteModelResponse>): ResourceState<CharacteModelResponse> {
        if(response.isSuccessful){
            response.body()?.let{values ->
                return ResourceState.Sucess(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}