package com.applichic.astronomypicture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.applichic.astronomypicture.viewmodel.EntryListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: EntryListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.entries.observe(this, { entries ->
            Log.d("test", entries.status.name)

            if(entries.data != null) {
                Log.d("test", entries.data.toString())
            }
        })

        viewModel.startLoading();
    }
}
