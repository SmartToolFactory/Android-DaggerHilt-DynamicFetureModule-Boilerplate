package com.smarttoolfactory.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.smarttoolfactory.data.api.SampleApi
import com.smarttoolfactory.data.constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val CLIENT_TIME_OUT = 120L
    }

    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        chuckInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder
            .addInterceptor(chuckInterceptor)
            .connectTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        return okHttpBuilder.build()
    }

    @Singleton
    @Provides
    fun providePropertyApiCoroutines(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): SampleApi {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
        return retrofitBuilder.build().create(SampleApi::class.java)
    }

    @Singleton
    @Provides
    fun provideChuckInterceptor(@ApplicationContext context: Context) =
        ChuckerInterceptor.Builder(context = context).build()
}
