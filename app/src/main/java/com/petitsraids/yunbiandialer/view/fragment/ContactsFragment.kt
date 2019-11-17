package com.petitsraids.yunbiandialer.view.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petitsraids.yunbiandialer.R
import com.petitsraids.yunbiandialer.data.Contact
import com.petitsraids.yunbiandialer.support.adapter.ContactsAdapter
import com.petitsraids.yunbiandialer.viewmodel.ContactsViewModel

class ContactsFragment : Fragment() {

    private lateinit var contactsView: RecyclerView
    private lateinit var adapter: ContactsAdapter
    private lateinit var viewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        viewModel.contact.observe(requireActivity(), Observer<List<Contact>> {
            adapter.setContact(it)
        })
    }

    private fun initView() {
        contactsView = view!!.findViewById(R.id.contacts_list)
        adapter = ContactsAdapter(requireContext(), false)
        contactsView.layoutManager = LinearLayoutManager(requireContext())
        contactsView.adapter = adapter
        contactsView.addItemDecoration(object :
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL) {})
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application).create(ContactsViewModel::class.java)
        if (viewModel.contact.value != null)
            adapter.setContact(viewModel.contact.value!!)
    }

}
