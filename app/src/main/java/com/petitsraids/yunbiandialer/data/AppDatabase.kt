package com.petitsraids.yunbiandialer.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.petitsraids.yunbiandialer.DialerApplication

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(): AppDatabase {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            DialerApplication.mContext!!,
                            AppDatabase::class.java,
                            "contact_base"
                        ).build()
                    }
                }
            }
            return instance!!
        }
    }

    abstract fun getContactsDao(): ContactsDao
}
