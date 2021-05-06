package id.riverflows.moviicat.util

import android.content.Context
import id.riverflows.moviicat.R

object UtilErrorMessage {
    fun getErrorMessage(context: Context, errorCode: Int?): String{
        return when(errorCode){
            in 100..199 -> context.getString(R.string.error_message_1xx)
            in 200..299 -> context.getString(R.string.error_message_2xx)
            in 300..399 -> context.getString(R.string.error_message_3xx)
            in 400..499 -> context.getString(R.string.error_message_4xx)
            in 500..599 -> context.getString(R.string.error_message_5xx)
            else -> context.getString(R.string.error_message_network)
        }
    }
}