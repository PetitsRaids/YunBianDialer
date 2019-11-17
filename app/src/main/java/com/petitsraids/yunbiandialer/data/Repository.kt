package com.petitsraids.yunbiandialer.data

import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class Repository {
    private val contactsDao: ContactsDao = AppDatabase.getInstance().getContactsDao()
    private var contactLiveData = contactsDao.getAllContacts()
    private val executor = Executors.newSingleThreadExecutor()
    fun insertContacts(contacts: List<Contact>) {
        executor.execute { contactsDao.insertContacts(contacts) }
    }

    fun getAllContacts():LiveData<List<Contact>>{
        return contactLiveData
    }
}