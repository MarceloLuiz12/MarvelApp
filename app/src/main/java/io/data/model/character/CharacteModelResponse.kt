package io.data.model.character

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CharacteModelResponse(
    @SerializedName("data")
    val data: CharacteModelData
): Serializable
