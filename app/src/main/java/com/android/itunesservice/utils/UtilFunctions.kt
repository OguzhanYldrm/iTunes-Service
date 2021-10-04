package com.android.itunesservice.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*


object UtilFunctions {

    fun textShortener(txt: String?, maximumLength : Int) : String{
        txt?.let {
            return if (it.length > maximumLength) it.substring(0,maximumLength) + "..." else txt
        }
        return ""
    }

    fun dateFormatter(zonedDate : String) : String{
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val formattedDate = formatter.format(parser.parse(zonedDate.substring(0, zonedDate.length - 2))!!)
        return formattedDate.format(Date())
    }


}