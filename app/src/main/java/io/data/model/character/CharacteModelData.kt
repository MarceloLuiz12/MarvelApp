package io.data.model.character

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CharacteModelData(
    @SerializedName("results")
    val results: List<CharacterModel>
): Serializable