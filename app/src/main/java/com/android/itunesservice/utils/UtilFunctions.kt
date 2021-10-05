package com.android.itunesservice.utils

import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*


object UtilFunctions {

    /**
     * A Method for shortening long text
     * @param txt givenText
     * @param maximumLength length limit that if we exceed it. 3 dots will be added to end of txt
     * @return shortened text if txt is not null
     */
    fun String.textShortener(maximumLength : Int) : String {
        this.let {
            return if (it.length > maximumLength) it.substring(0,maximumLength) + "..." else this
        }
    }

    /**
     * Dateformatter which converts given date string to human readable format.
     */
    fun String.dateFormatter() : String {
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val formattedDate = formatter.format(parser.parse(this.substring(0, this.length - 2))!!)
        return formattedDate.format(Date())
    }

    /**
     * @return URL-Encoded version of given text.
     */
    fun String.urlEncoder() : String = URLEncoder.encode(this, "utf-8")


}