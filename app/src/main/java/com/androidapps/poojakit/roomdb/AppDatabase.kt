package com.androidapps.poojakit.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var database: AppDatabase? = null
        private val DATABASE_NAME = "poojakit"
//        private val DATABASE_NAME: String? = "poojakit"
//        private const val DATABASE_NAME: "poojakit"

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext, // Use applicationContext here
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database!!
        }
    }

    abstract fun productDao(): ProductDao
}


//package com.androidapps.poojakit.roomdb
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(entities = [ProductModel::class], version = 1)
//
//abstract class AppDatabase : RoomDatabase() {
//
//    companion object {
//        private var database: AppDatabase? = null
////        private val DATABASE_NAME: "poojakit"
//        private val DATABASE_NAME: String = "poojakit"
//
//
//        @Synchronized
//        fun getInstance(context: Context): AppDatabase {
//            if (database == null) {
//                database = Room.databaseBuilder(
//                    context = applicationContext,
//                    AppDatabase::class.java,
//                    DATABASE_NAME
//                ).allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
//                    .build()
//            }
//            return database!!
//        }
//    }
//
//    abstract fun productDao(): ProductDao
//
//}