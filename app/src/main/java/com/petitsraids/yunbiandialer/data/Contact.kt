package com.petitsraids.yunbiandialer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(val name: String, val phoneNumber: String) {
    @PrimaryKey
    var id: Int = hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contact

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

}
