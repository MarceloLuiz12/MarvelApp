package io.data.model

import com.google.gson.annotations.SerializedName

class ThumbnailModel(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String
)
