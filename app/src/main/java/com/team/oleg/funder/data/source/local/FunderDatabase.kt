package com.team.oleg.funder.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.team.oleg.funder.data.Sponsor

//@Database(entities = arrayOf(Sponsor::class),version = 1)
//abstract class FunderDatabase : RoomDatabase(){
//
//    abstract fun userDao(): UserDao
//
//
//    companion object{
//
//        private var INSTANCE: FunderDatabase? = null
//
//        private val lock = Any()
//
//        fun getInstance(context: Context): FunderDatabase {
//            synchronized(lock){
//                if (INSTANCE == null){
//                    INSTANCE = Room.databaseBuilder(context.applicationContext,
//                        FunderDatabase::class.java,"FunderDatabase.db")
//                        .build()
//                }
//                return INSTANCE!!
//            }
//        }
//    }
//
//}