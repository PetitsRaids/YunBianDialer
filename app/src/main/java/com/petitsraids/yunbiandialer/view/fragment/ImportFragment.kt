package com.petitsraids.yunbiandialer.view.fragment

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petitsraids.yunbiandialer.R
import com.petitsraids.yunbiandialer.data.Contact
import com.petitsraids.yunbiandialer.support.adapter.ImportAdapter
import com.petitsraids.yunbiandialer.viewmodel.ContactsViewModel

class ImportFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImportAdapter
    private lateinit var selectAll: ImageButton
    private lateinit var exportContacts: ImageButton
    private lateinit var exportList: List<Contact>
    private lateinit var viewModel: ContactsViewModel
    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_import, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        handler = Handler()
        selectAll.setOnClickListener {
            adapter.selectAllContacts()
        }
        exportContacts.setOnClickListener {
            exportList = adapter.getExportList()
            viewModel.insertContacts(exportList)
            val navController = Navigation.findNavController(it)
            navController.navigateUp()
        }
        getSystemContacts()
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application).create(ContactsViewModel::class.java)
    }

    private fun initView() {
        recyclerView = view!!.findViewById(R.id.import_recycler_view)
        recyclerView.addItemDecoration(object :
            DividerItemDecoration(requireContext(), VERTICAL) {})
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ImportAdapter(requireContext())
        recyclerView.adapter = adapter
        selectAll = view!!.findViewById(R.id.select_all)
        exportContacts = view!!.findViewById(R.id.export_contacts)
    }

    private fun getSystemContacts() {
        Thread {
            val uri: Uri = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI
            val cursor = requireActivity().contentResolver.query(uri, null, null, null, null, null)
            val list: MutableList<Contact> = mutableListOf()
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val name: String =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val contact = Contact(name, number)
                    list.add(contact)
                }
                handler.post {
                    adapter.setImportList(list.toList())
                    adapter.notifyDataSetChanged()
                }
            }
            cursor?.close()
        }.start()
    }
}