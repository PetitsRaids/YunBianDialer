package com.petitsraids.yunbiandialer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts ORDER BY name")
    fun getAllContacts(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(contacts: List<Contact>)

}