package com.stevehechio.apps.mziiketrailers.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stevehechio.apps.mziiketrailers.data.local.dao.MovieDao
import com.stevehechio.apps.mziiketrailers.data.local.db.AppDatabase
import com.stevehechio.apps.mziiketrailers.data.remote.api.MovieApiService
import com.stevehechio.apps.mziiketrailers.data.repository.FetchMoviesRepository
import com.stevehechio.apps.mziiketrailers.data.repository.GetMovieDetailsByIdRepo
import com.stevehechio.apps.mziiketrailers.data.repository.SearchRepository
import com.stevehechio.apps.mziiketrailers.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by stevehechio on 10/23/21
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
   /**
     * Here we return the Gson object
     */
    @Provides
    @Singleton
     fun provideGson(): Gson {
        return GsonBuilder().create()
    }
    /**
     * Here we return the Retrofit object
     */
    @Provides
    @Singleton
     fun provideCache(application: Application): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    /**
     * Here we return the OKhttp object
     */
    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }
    @Provides
    @Singleton
     fun provideRetrofit(httpClient: OkHttpClient,gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .baseUrl(AppConstants.BASE_URL)
            .build()
    }

    /**
    * We need the MovieApiService module.
    * For this, We need the Retrofit object, Gson, Cache and OkHttpClient .
    * So we will define the providers for these objects here in this module.
    *
    * */
    @Singleton
    @Provides
     fun provideMovieApiService(retrofit: Retrofit): MovieApiService{
        return retrofit.create(MovieApiService::class.java)
    }

    @Singleton
    @Provides
     fun provideSearchRepository(movieService: MovieApiService): SearchRepository {
        return SearchRepository(movieService)
    }

    /**
     * Here we return the Database object
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, AppConstants.DB_NAME
        ).build()
    }

    /**
     * We need the MovieDao module.
     * For this, We need the AppDatabase object
     * So we will define the providers for this here in this module.
     * */
    @Provides
    @Singleton
     fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideFetchMoviesRepository(@ApplicationContext context: Context,movieDao: MovieDao,movieService: MovieApiService): FetchMoviesRepository {
        return FetchMoviesRepository(context,movieDao,movieService)
    }


    @Singleton
    @Provides
    fun provideGetMovieDetailsByIdRepo(@ApplicationContext context: Context,movieDao: MovieDao,movieService: MovieApiService): GetMovieDetailsByIdRepo{
        return GetMovieDetailsByIdRepo(context, movieDao,movieService)
    }


}