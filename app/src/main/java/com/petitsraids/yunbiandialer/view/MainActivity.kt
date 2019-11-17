package com.petitsraids.yunbiandialer.view

import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.petitsraids.yunbiandialer.DialerApplication
import com.petitsraids.yunbiandialer.R
import com.petitsraids.yunbiandialer.support.MyUtils
import com.petitsraids.yunbiandialer.support.ShowDialog

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        when (DialerApplication.FONT_SIZE) {
            MyUtils.FONT_SIZE_SMALL -> theme.applyStyle(R.style.FontStyle_Small, true)
            MyUtils.FONT_SIZE_MEDIUM -> theme.applyStyle(R.style.FontStyle_Medium, true)
            MyUtils.FONT_SIZE_LARGE -> theme.applyStyle(R.style.FontStyle_Large, true)
            MyUtils.FONT_SIZE_XLARGE -> theme.applyStyle(R.style.FontStyle_XLarge, true)
            MyUtils.FONT_SIZE_XXLARGE -> theme.applyStyle(R.style.FontStyle_XXLarge, true)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> navController.navigate(R.id.action_contactsFragment_to_settingsFragment)
            R.id.import_contacts -> navController.navigate(R.id.action_contactsFragment_to_importFragment)
        }
        return super.onContextItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (DialerApplication.FONT_SIZE != DialerApplication.NEW_FONT_SIZE) {
            ShowDialog().showAlterDialog(this, R.string.restart_title,
                R.string.restart_message,
                DialogInterface.OnClickListener { _, _ ->
                    finish()
                    startActivity(intent)
                },
                DialogInterface.OnClickListener { _, _ ->

                })
            false
        } else {
            navController.navigateUp()
        }
    }

    override fun onBackPressed() {
        if (DialerApplication.FONT_SIZE != DialerApplication.NEW_FONT_SIZE) {
            ShowDialog().showAlterDialog(this, R.string.restart_title,
                R.string.restart_message,
                DialogInterface.OnClickListener { _, _ ->
                    finish()
                    startActivity(intent)
                },
                DialogInterface.OnClickListener { _, _ ->

                })
        } else {
            super.onBackPressed()
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.READ_CONTACTS
                ),
                MyUtils.REQUEST_CODE_CALL
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MyUtils.REQUEST_CODE_CALL) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Granted Permission", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Denied Permission", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
