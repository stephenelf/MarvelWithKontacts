package com.stephenelf.marvelwithkontacts.domain.dagger

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stephenelf.marvelwithkontacts.data.net.MarvelAPI
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule(val baseUrl: String) {


    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Named("cached")
    @Singleton
    fun provideOkHttpClientWithCache(cache: Cache): OkHttpClient = OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .cache(cache).build()


    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)

        return gsonBuilder.create()
    }


    @Provides
    @Named("logs_enabled")
    @Singleton
    fun provideOkHttpLogsEnabled(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val interceptor2 = HttpLoggingInterceptor()
        interceptor2.setLevel(HttpLoggingInterceptor.Level.BODY)
        val interceptor3 = HttpLoggingInterceptor()
        interceptor3.setLevel(HttpLoggingInterceptor.Level.BASIC)


        //  OkHttpClient client =  getUnsafeOkHttpClient();

        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(interceptor)
            .addInterceptor(interceptor2)
            .addInterceptor(interceptor3)
            .cache(null) //disable cache
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout((60 / 2).toLong(), TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            //  .hostnameVerifier { hostname, session -> true }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, @Named("logs_enabled") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    @Provides
    @Singleton
    fun providesMarvelAPI(retrofit: Retrofit): MarvelAPI =retrofit.create<MarvelAPI>(MarvelAPI::class.java)

}