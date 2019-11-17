package com.petitsraids.yunbiandialer.view.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petitsraids.yunbiandialer.DialerApplication
import com.petitsraids.yunbiandialer.R
import com.petitsraids.yunbiandialer.data.Contact
import com.petitsraids.yunbiandialer.support.MyUtils
import com.petitsraids.yunbiandialer.support.ShowDialog
import com.petitsraids.yunbiandialer.support.adapter.ContactsAdapter

class FontSizeChangeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactsAdapter
    private lateinit var navController: NavController
    private lateinit var seekBar: SeekBar
    private var oldFontSize: Int = DialerApplication.FONT_SIZE
    private var newFontSize: Int = oldFontSize

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_font_size_change, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        recyclerView.adapter = adapter
        seekBar.progress = switchSizeToProgress(oldFontSize)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (progress) {
                    in 0..10 -> {
                        newFontSize = switchProgressToSize(progress)
                        seekBar!!.progress = 0
                    }
                    in 11..37 -> {
                        newFontSize = switchProgressToSize(progress)
                        seekBar!!.progress = 25
                    }
                    in 38..65 -> {
                        newFontSize = switchProgressToSize(progress)
                        seekBar!!.progress = 50
                    }
                    in 66..90 -> {
                        newFontSize = switchProgressToSize(progress)
                        seekBar!!.progress = 75
                    }
                    in 91..100 -> {
                        newFontSize = switchProgressToSize(progress)
                        seekBar!!.progress = 100
                    }
                }
                adapter.modifyFontSize(newFontSize)
                DialerApplication.NEW_FONT_SIZE = newFontSize
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun onPause() {
        super.onPause()
        DialerApplication.FONT_SIZE = newFontSize
        DialerApplication.NEW_FONT_SIZE = newFontSize
    }

    override fun onStop() {
        super.onStop()
        val edit = requireContext().getSharedPreferences(
            MyUtils.SHARED_NAME,
            Context.MODE_PRIVATE
        )!!.edit()
        edit.putInt(MyUtils.SHARED_KEY, newFontSize)
        edit.apply()
    }

    private fun initView() {
        navController = Navigation.findNavController(view!!)
        recyclerView = view!!.findViewById(R.id.font_size_change_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(object :
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL) {})
        adapter = ContactsAdapter(requireContext(), true)
        adapter.setContact(listOf(Contact("Raids", ""), Contact("Petits", "")))
        seekBar = view!!.findViewById(R.id.font_seek_bar)
    }

    private fun switchSizeToProgress(size: Int): Int {
        var progress = 0
        when (size) {
            MyUtils.FONT_SIZE_SMALL -> progress = 0
            MyUtils.FONT_SIZE_MEDIUM -> progress = 25
            MyUtils.FONT_SIZE_LARGE -> progress = 50
            MyUtils.FONT_SIZE_XLARGE -> progress = 75
            MyUtils.FONT_SIZE_XXLARGE -> progress = 100
        }
        return progress
    }

    private fun switchProgressToSize(progress: Int): Int {
        var fontSize = 0
        when (progress) {
            0 -> fontSize = MyUtils.FONT_SIZE_SMALL
            25 -> fontSize = MyUtils.FONT_SIZE_MEDIUM
            50 -> fontSize = MyUtils.FONT_SIZE_LARGE
            75 -> fontSize = MyUtils.FONT_SIZE_XLARGE
            100 -> fontSize = MyUtils.FONT_SIZE_XXLARGE
        }
        return fontSize
    }
}