package com.applichic.astronomypicture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BigImageViewer.initialize(GlideImageLoader.with(this))
    }
}
