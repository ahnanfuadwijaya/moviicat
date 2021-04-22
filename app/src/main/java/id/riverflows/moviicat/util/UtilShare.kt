package id.riverflows.moviicat.util

import android.content.Context
import android.content.Intent
import id.riverflows.moviicat.R

object UtilShare {
    fun share(context: Context, text: String){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = UtilConstants.MIME_TYPE_TEXT
        }
        val shareIntent = Intent.createChooser(sendIntent, context.getString(R.string.title_share))
        context.startActivity(shareIntent)
    }
}