package com.imdb.movieList.di


import com.imdb.movieList.utils.BASE_URL
import com.imdb.movieList.api.MovieAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun providesMovieAPI(retrofitBuilder: Retrofit.Builder): MovieAPI {
        return retrofitBuilder.build().create(MovieAPI::class.java)
    }
}