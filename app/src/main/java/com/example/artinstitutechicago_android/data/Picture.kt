package com.example.artinstitutechicago_android.data

import com.google.gson.annotations.SerializedName

data class PictureListResponse (
    @SerializedName("data") val result: List<Picture>
)

data class PictureResponse (
    @SerializedName("data") val result: Picture
)

class Picture (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image_id") val imageId: String,
    @SerializedName("short_description") val shortDescription: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("artist_title") val artisTitle: String,
    @SerializedName("publication_history") val publication: String?,
    @SerializedName("exhibition_history") val exhibition: String?,

) {
    fun getImageUrl(): String {
        return "https://www.artic.edu/iiif/2/$imageId/full/843,/0/default.jpg"
    }
}