package com.neythuaung.product_catalog_app.product.data.data_source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.neythuaung.product_catalog_app.product.data.data_source.local.dao.FavoriteProductDao
import com.neythuaung.product_catalog_app.product.domain.entity.FavoriteProductEntity
import kotlin.concurrent.Volatile

@Database(
    entities = [FavoriteProductEntity::class],
    version = 1
)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDao() : FavoriteProductDao

    companion object{

        // Volatile singleton prevents multiple instances of database opening at the same time
        //write variable will be immediately visible to other threads
        @Volatile
        private var instance : ProductDatabase?= null
        private val LOCK = Any()

        // always call invoke function when everytime create an ArticleDatabase instance
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{
                instance = it
            }
        }

        //build database
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ProductDatabase::class.java,
                "product_db.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}