package com.applichic.astronomypicture.viewmodel

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.applichic.astronomypicture.R
import com.applichic.astronomypicture.db.model.Entry

class EntryGridViewModel(val entry: Entry, private val activity: AppCompatActivity) {
    fun onPictureClicked(view: View) {
        val navHostFragment =
            activity.supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment?
        val navController = navHostFragment?.navController
        navController?.navigate(R.id.action_mainBottomNavigationFragment_to_entryDetailFragment)
    }
}