package com.android.itunesservice.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * The data class for a response object. In general this class returns the resultCount and a list
 * of products. These products will be shown on Listing Page
 */
@Parcelize
data class ProductResultModel(
    @SerializedName("resultCount") var resultCount : Int,
    @SerializedName("results") var results : List<Product>


) : Parcelable {
    @Parcelize
    data class Product(
        @SerializedName("trackId") var id: Int,//Id
        @SerializedName("collectionName") var collectionName: String,//Name
        @SerializedName("artworkUrl100") var artworkUrl100: String?, //Image Url
        @SerializedName("collectionPrice") var collectionPrice: Double, //Price
        @SerializedName("primaryGenreName") var primaryGenreName: String, //Type (Comedy etc.)
        @SerializedName("releaseDate") var releaseDate: String, //Release
        @SerializedName("longDescription") var longDescription: String, //Description

    ) : Parcelable {

        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

    }

}

