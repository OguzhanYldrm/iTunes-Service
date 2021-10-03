package com.android.itunesservice.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    @SerializedName("wrapperType") var wrapperType : String,
    @SerializedName("collectionName") var collectionName : String,
    @SerializedName("artworkUrl100") var artworkUrl100 : String,
    @SerializedName("collectionPrice") var collectionPrice : Double
) : Parcelable

