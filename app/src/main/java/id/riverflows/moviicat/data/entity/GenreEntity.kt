package id.riverflows.moviicat.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreEntity(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("name")
    val name: String
): Parcelable
