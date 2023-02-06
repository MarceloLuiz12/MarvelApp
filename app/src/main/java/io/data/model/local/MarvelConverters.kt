package io.data.model.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import io.data.model.ThumbnailModel

class MarvelConverters {
    @TypeConverter
    fun fromThumnail(thumbnailModel: ThumbnailModel) : String = Gson().toJson(thumbnailModel)
    @TypeConverter
    fun toThumbnail(thumbnailModel: String) : ThumbnailModel = Gson().fromJson(thumbnailModel, ThumbnailModel::class.java)
}