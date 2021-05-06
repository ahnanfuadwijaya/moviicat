package id.riverflows.moviicat.util

import android.view.View
import com.google.android.material.snackbar.Snackbar
import id.riverflows.moviicat.R

object UtilSnackBar {
    fun showIndeterminate(view: View, message: String){
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(view.context.getString(R.string.action_close)){
            snackBar.dismiss()
        }.show()
    }
}