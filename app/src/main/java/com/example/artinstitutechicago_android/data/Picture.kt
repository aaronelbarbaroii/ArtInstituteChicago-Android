package com.example.artinstitutechicago_android.data

import com.google.gson.annotations.SerializedName

data class PictureResponse (
    @SerializedName("data") val result: List<Picture>
) {

}

class Picture (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image_id") val imageId: String,
    @SerializedName("short_description") val shortDescription: String,
    @SerializedName("description") val description: String,

) {
    fun getImageUrl(): String {
        return "https://www.artic.edu/iiif/2/$imageId/full/843,/0/default.jpg"
    }
}