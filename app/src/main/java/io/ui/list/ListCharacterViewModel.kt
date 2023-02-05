package io.ui.list

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
class ListCharacterViewModel @Inject constructor(
    private val repository: MarvelRepository
) : ViewModel() {

    private val _list =  MutableStateFlow<ResourceState<CharacteModelResponse>>(ResourceState.Loading())
    val list: StateFlow<ResourceState<CharacteModelResponse>> = _list

    init{
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try{
            val response = repository.list()
            _list.value = handleResponse(response)
        }catch (t: Throwable){
            when(t){
                is IOException -> _list.value = ResourceState.Error("Erro de conexão com a internet")
                else -> _list.value = ResourceState.Error("Falha na conversão de dados")
            }
        }
    }

    private fun handleResponse(response: Response<CharacteModelResponse>): ResourceState<CharacteModelResponse> {
        if(response.isSuccessful){
            response.body()?.let { values ->
                return ResourceState.Sucess(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}