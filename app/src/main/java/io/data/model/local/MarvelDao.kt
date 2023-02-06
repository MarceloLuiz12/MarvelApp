package io.data.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.data.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterModel: CharacterModel) : Long

    @Query("SELECT * FROM characterModel ORDER BY name")
    fun getAll() : Flow<List<CharacterModel>>

    @Delete
    suspend fun delete(characterModel: CharacterModel)
}