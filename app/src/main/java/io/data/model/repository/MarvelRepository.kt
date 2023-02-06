package io.data.model.repository

import io.data.model.character.CharacterModel
import io.data.model.local.MarvelDao
import io.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api: ServiceApi,
    private val dao: MarvelDao
){
    suspend fun list(nameStartsWith: String? = null) = api.list(nameStartsWith)
    suspend fun getComics(characterId: Int) = api.getComics(characterId)

    suspend fun insert(characterModedl: CharacterModel) = dao.insert(characterModedl)
    fun getAll() = dao.getAll()

    suspend fun delete(characterModedl: CharacterModel) = dao.delete(characterModedl)
}