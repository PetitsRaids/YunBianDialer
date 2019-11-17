package com.petitsraids.yunbiandialer.support

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.petitsraids.yunbiandialer.DialerApplication
import com.petitsraids.yunbiandialer.R
import com.petitsraids.yunbiandialer.data.Contact

class ShowDialog {

    fun showAlterDialog(
        context: Context,
        titleId: Int,
        messageId: Int,
        positiveListener: DialogInterface.OnClickListener,
        negativeButton: DialogInterface.OnClickListener
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(titleId)
            .setMessage(messageId)
            .setPositiveButton(R.string.sure, positiveListener)
            .setNegativeButton(R.string.cancel, negativeButton)
            .create()
        dialog.show()
        val message: TextView = dialog.findViewById(android.R.id.message)
        message.textSize = MyUtils.theme2sp(DialerApplication.FONT_SIZE)
    }

    fun showContactDialog(context: Context, contact: Contact) {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_show_contact_info, null, false)
        val fab: FloatingActionButton = view.findViewById(R.id.dialog_call_fab)
        val dialog = AlertDialog.Builder(context).setView(view)
            .setNegativeButton(
                R.string.cancel
            ) { _, _ -> }
            .create()
        val title: TextView = view.findViewById(R.id.dialog_title)
        val message: TextView = view.findViewById(R.id.dialog_message)
        title.text = contact.name
        message.text = contact.phoneNumber
        fab.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_CALL
            intent.data = Uri.parse("tel:${contact.phoneNumber}")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            dialog.dismiss()
        }
        dialog.show()
    }
}
