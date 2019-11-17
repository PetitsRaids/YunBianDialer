package com.petitsraids.yunbiandialer.viewmodel

import androidx.lifecycle.ViewModel
import com.petitsraids.yunbiandialer.data.Contact
import com.petitsraids.yunbiandialer.data.Repository

class ContactsViewModel : ViewModel() {
    private val repository = Repository()
    var contact = repository.getAllContacts()
    fun insertContacts(contact: List<Contact>) {
        repository.insertContacts(contact)
    }
}
