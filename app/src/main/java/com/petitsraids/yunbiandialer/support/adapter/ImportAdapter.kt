package com.petitsraids.yunbiandialer.support.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.petitsraids.yunbiandialer.R
import com.petitsraids.yunbiandialer.data.Contact
import com.petitsraids.yunbiandialer.support.MyUtils

class ImportAdapter(private val mContext: Context) :
    RecyclerView.Adapter<ImportAdapter.ViewHolder>() {

    private var importList: List<Contact> = arrayListOf()
    private var exportSet: HashSet<Contact> = hashSetOf()

    fun setImportList(importList: List<Contact>) {
        this.importList = importList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_import, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            viewHolder.checkImport.isChecked = !viewHolder.checkImport.isChecked
        }
        viewHolder.checkImport.setOnCheckedChangeListener { _, isChecked ->
            val contact = viewHolder.itemView.tag as Contact
            if (isChecked) {
                Log.d(MyUtils.TAG, contact.name)
                viewHolder.itemView.background =
                    mContext.getDrawable(R.color.split_item)
                exportSet.add(contact)
            } else {
                viewHolder.itemView.background = null
                exportSet.remove(contact)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = importList[position]
        holder.itemView.tag = contact
        holder.contactName.text = contact.name
        holder.checkImport.isChecked = exportSet.contains(contact)
    }

    override fun getItemCount(): Int {
        return importList.size
    }

    fun getExportList(): List<Contact> {
        return exportSet.toList()
    }

    fun selectAllContacts() {
        exportSet.addAll(importList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkImport: CheckBox = view.findViewById(R.id.import_or_not)
        val contactName: TextView = view.findViewById(R.id.import_contact_name)
    }
}