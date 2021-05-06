package id.riverflows.moviicat.data.source.remote

import id.riverflows.moviicat.BuildConfig
import id.riverflows.moviicat.di.Injection
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBuilder {
    fun<T> build(service: Class<T>): T {
        return Retrofit.Builder().apply {
            client(providesHttpClient())
            baseUrl(Injection.provideBaseUrl())
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(service)
    }

    private fun providesHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(false)
            if(BuildConfig.DEBUG){
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            else{
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
            }
            cache(null)
        }
            .addInterceptor(getHeaderInterceptor())
            .build()
    }

    private fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request =
                chain.request().newBuilder()
                    .header("Connection", "close")
                    .header("Accept-Encoding", "identity")
                    .header("Authorization", "Bearer ${Injection.provideToken()}")
                    .build()
            chain.proceed(request)
        }
    }
}