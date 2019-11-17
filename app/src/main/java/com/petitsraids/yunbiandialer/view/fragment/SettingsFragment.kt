package com.petitsraids.yunbiandialer.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.petitsraids.yunbiandialer.R
import com.petitsraids.yunbiandialer.support.ShowDialog

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val settingLinearLayout: FrameLayout = view!!.findViewById(R.id.font_size_setting)
        settingLinearLayout.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.action_settingsFragment_to_fontSizeChangeFragment)
        }
    }

}