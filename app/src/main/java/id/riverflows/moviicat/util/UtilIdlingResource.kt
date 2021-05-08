package id.riverflows.moviicat.util

import androidx.test.espresso.idling.CountingIdlingResource

object UtilIdlingResource {
    private const val RESOURCE = "GLOBAL"
    val idlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        idlingResource.decrement()
    }
}