package com.petitsraids.yunbiandialer.support.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.petitsraids.yunbiandialer.DialerApplication
import com.petitsraids.yunbiandialer.R
import com.petitsraids.yunbiandialer.data.Contact
import com.petitsraids.yunbiandialer.support.MyUtils
import com.petitsraids.yunbiandialer.support.ShowDialog

class ContactsAdapter(private val mContext: Context, private val isTesting: Boolean) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var contactsList: List<Contact> = ArrayList()
    private var currentTheme: Int = DialerApplication.FONT_SIZE

    fun setContact(contactsList: List<Contact>) {
        this.contactsList = contactsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_contacts, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.callButton.setOnClickListener {
            if (isTesting)
                return@setOnClickListener
            val contact = viewHolder.itemView.tag as Contact
            call(contact.phoneNumber)
        }
        viewHolder.itemView.setOnClickListener {
            val contact = viewHolder.itemView.tag as Contact
            ShowDialog().showContactDialog(mContext, contact)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactsList[position]
        holder.itemView.tag = contact
        holder.contactsName.text = contact.name
        if (position % 2 == 1) {
            holder.itemView.background = mContext.getDrawable(R.color.split_item)
        } else {
            holder.itemView.background = null
        }
        if (isTesting) {
            holder.contactsName.setTextSize(
                TypedValue.COMPLEX_UNIT_DIP,
                MyUtils.theme2sp(currentTheme)
            )
            holder.callButton.customSize = MyUtils.dp2px(mContext, MyUtils.theme2dp(currentTheme))
        }
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun modifyFontSize(theme: Int) {
        currentTheme = theme
        notifyDataSetChanged()
    }

    private fun call(phoneNumber: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_CALL
        intent.data = Uri.parse("tel:${phoneNumber}")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        mContext.startActivity(intent)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactsName: TextView = itemView.findViewById(R.id.contacts_name)
        val callButton: FloatingActionButton = itemView.findViewById(R.id.call_button)
    }
}