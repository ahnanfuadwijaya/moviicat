package id.riverflows.moviicat.data.entity

import com.google.gson.annotations.SerializedName


data class GenreEntity(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("name")
    val name: String
)
