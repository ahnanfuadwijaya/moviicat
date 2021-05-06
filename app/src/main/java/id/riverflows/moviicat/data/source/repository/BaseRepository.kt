package id.riverflows.moviicat.data.source.repository


import id.riverflows.moviicat.data.source.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

abstract class BaseRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO){
            try {
                Resource.Success(apiCall.invoke())
            }
            catch (exception: Exception){
                Timber.d(exception)
                when(exception){
                    is HttpException -> {
                        val jsonReader = exception.response()?.errorBody()?.charStream()
                        val code = exception.code()
                        Timber.d(jsonReader.toString())
                        Resource.Failure(code)
                    }
                    else -> Resource.Failure(null)
                }
            }
        }
    }
}