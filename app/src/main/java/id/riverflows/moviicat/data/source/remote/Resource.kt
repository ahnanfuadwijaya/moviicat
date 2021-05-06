package id.riverflows.moviicat.data.source.remote

sealed class Resource<out T> {
    data class Success<out T>(val value: T): Resource<T>()
    data class Failure(val code: Int?): Resource<Nothing>()
}