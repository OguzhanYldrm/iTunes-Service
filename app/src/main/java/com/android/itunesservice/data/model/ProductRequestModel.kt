package com.android.itunesservice.data.model

/**
 * The data class for a request. It takes one query parameter and a entity.
 * @param query should be URL-Encoded (for example "Michael Jackson" search should be Michael+Jackson)
 * @param entity is the category. (movie, music, ebook, podcast)
 */
data class ProductRequestModel(
    val query : String,
    val entity: String
)
