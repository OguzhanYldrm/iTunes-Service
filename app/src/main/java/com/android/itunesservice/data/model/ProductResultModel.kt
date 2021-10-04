package com.android.itunesservice.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResultModel(
    @SerializedName("resultCount") var resultCount : Int,
    @SerializedName("results") var results : List<Product>


) : Parcelable {
    @Parcelize
    data class Product(
        @SerializedName("trackId") var id: Int,
        @SerializedName("collectionName") var collectionName: String,
        @SerializedName("artworkUrl100") var artworkUrl100: String,
        @SerializedName("collectionPrice") var collectionPrice: Double,
        @SerializedName("releaseDate") var releaseDate: String,
    ) : Parcelable {

        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

    }

}

