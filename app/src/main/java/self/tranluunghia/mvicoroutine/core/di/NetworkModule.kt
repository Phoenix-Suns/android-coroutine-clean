package self.tranluunghia.mvicoroutine.core.di

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.tranluunghia.mvicoroutine.BuildConfig
import self.tranluunghia.mvicoroutine.core.helper.NetworkHelper
import self.tranluunghia.mvicoroutine.core.helper.extention.toPrettyJSONString
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CATCH_SIZE = (10 * 1024 * 1024).toLong() // 10 MB
private const val CONNECT_TIMEOUT = 30L  // Second

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create(gson))
            //addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            baseUrl(BuildConfig.URL_GITHUB)
            client(okHttpClient)
        }.build()
    }

    @Provides
    @Singleton
    internal fun provideCache(application: Application): Cache {
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, CATCH_SIZE)
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(cache: Cache, application: Application): OkHttpClient {
        val httpClient = OkHttpClient.Builder().apply {
            cache(cache)
            addNetworkInterceptor(headerInterceptor())          // Add request header
            // addInterceptor(cacheInterceptor(application))    // Save request Catch
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        }

        // Write Log
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                //Log.i("OkHttp", message.toPrettyJSONString())
                Log.i("OkHttp", message)
            })
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        return httpClient.build()
    }

    /**
     * Add Header
     */
    private fun headerInterceptor(): Interceptor {
        return Interceptor { chain ->
            val token = ""
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()

            if(token.isNotEmpty()) {
                requestBuilder .header("Authorization", "bearer $token")
                    .header("Content-Type", "application/json")
                    .method(originalRequest.method,originalRequest.body)
            }

            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * Save request Catch
     */
    private fun cacheInterceptor(application: Application): Interceptor {
        return Interceptor { chain ->

            var request = chain.request()
            val isNetworkConnected = NetworkHelper.isInternetConnected(application.applicationContext)

            if (!isNetworkConnected) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            if (isNetworkConnected) {
                val maxAge = 0
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .removeHeader("Retrofit")
                    .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("nyn")
                    .build()
            }
            response
        }
    }
}
