package com.example.mynewsapp.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynewsapp.models.ArticleModel

@Database(
    entities = [ArticleModel::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class ArticleModelDatabase : RoomDatabase() {
    abstract fun getArticleDAO() : ArticleModelDAO

    companion object{
        @Volatile
        private var articleDbInstance : ArticleModelDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = articleDbInstance ?: synchronized(LOCK) {
            articleDbInstance ?: createDatabaseInstance(context).also {
                articleDbInstance = it
            }
        }

        private fun createDatabaseInstance(context: Context) =
            Room.databaseBuilder(
                context, ArticleModelDatabase::class.java,
                "article_db.db"
            ).fallbackToDestructiveMigration().build()
    }

}