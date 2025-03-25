package com.shivayogi.epicnews.di



import android.content.Context
import androidx.room.Room
import com.shivayogi.epicnews.data.local.AppDatabase
import com.shivayogi.epicnews.data.local.NewsDao
import com.shivayogi.epicnews.data.remote.CommentsLikesApiService
import com.shivayogi.epicnews.data.remote.NewsApiService
import com.shivayogi.epicnews.data.remote.NewsRepositoryImpl
import com.shivayogi.epicnews.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import com.shivayogi.epicnews.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApiService(okHttpClient: OkHttpClient): NewsApiService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL1)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)

    @Provides
    @Singleton
    fun provideCommentsLikesApiService(okHttpClient: OkHttpClient): CommentsLikesApiService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL2)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentsLikesApiService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        apiService: NewsApiService,
        commentsLikesApiService: CommentsLikesApiService,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(apiService,commentsLikesApiService, newsDao)

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context,
            AppDatabase::class.java,
            "news_db").fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNewsDao(database: AppDatabase): NewsDao {
        return database.newsDao()
    }
}
