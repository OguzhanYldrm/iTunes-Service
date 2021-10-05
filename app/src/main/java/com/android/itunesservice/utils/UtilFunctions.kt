package com.android.itunesservice.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*


object UtilFunctions {

    /**
     * A Method for shortening long text
     * @param txt givenText
     * @param maximumLength length limit that if we exceed it. 3 dots will be added to end of txt
     * @return shortened text if txt is not null
     */
    fun textShortener(txt: String?, maximumLength : Int) : String{
        txt?.let {
            return if (it.length > maximumLength) it.substring(0,maximumLength) + "..." else txt
        }
        return ""
    }

    /**
     * Dateformatter which converts given date string to human readable format.
     */
    fun dateFormatter(zonedDate : String) : String{
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val formattedDate = formatter.format(parser.parse(zonedDate.substring(0, zonedDate.length - 2))!!)
        return formattedDate.format(Date())
    }


}