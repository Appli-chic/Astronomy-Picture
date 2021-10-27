package com.applichic.astronomypicture.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.applichic.astronomypicture.R
import com.applichic.astronomypicture.databinding.FragmentImageViewerBinding
import com.applichic.astronomypicture.viewmodel.ImageViewerViewModel
import com.github.piasy.biv.loader.ImageLoader.Callback
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.lang.Exception
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore

import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import android.graphics.BitmapFactory
import java.io.IOException
import java.net.URL
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import com.google.android.material.snackbar.Snackbar


@AndroidEntryPoint
class ImageViewerFragment : Fragment() {
    private val _tag = "ImageViewerFragment"

    private lateinit var binding: FragmentImageViewerBinding
    private val viewModel: ImageViewerViewModel by viewModels()
    private val args: ImageViewerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageViewerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity?.setSupportActionBar(binding.toolbar)
        appCompatActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity?.supportActionBar?.setDisplayShowHomeEnabled(true)
        setHasOptionsMenu(true)

        viewModel.setUrl(args.url)
        viewModel.setHdUrl(args.hdUrl)

        // Control the photo loading
        binding.previewImage.setImageLoaderCallback(object : Callback {
            override fun onCacheHit(imageType: Int, image: File?) {
            }

            override fun onCacheMiss(imageType: Int, image: File?) {
            }

            override fun onStart() {
                viewModel.setLoading(true)
            }

            override fun onProgress(progress: Int) {
            }

            override fun onFinish() {
                viewModel.setLoading(false)
            }

            override fun onSuccess(image: File?) {
            }

            override fun onFail(error: Exception?) {
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.image_viewer_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
            }
            R.id.download -> {
                if (viewModel.url.value != null) {
                    downloadImage(viewModel.url.value!!)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun downloadImage(url: String) {
        Thread {
            try {
                // Create a snack bar to notify the image is being downloaded
                val snackBar =
                    Snackbar.make(
                        binding.root,
                        R.string.downloading_in_progress,
                        Snackbar.LENGTH_INDEFINITE
                    )
                snackBar.show()

                // Create the Image from the URL
                val uri = URL(url)
                val policy = ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)
                val image = BitmapFactory.decodeStream(uri.openConnection().getInputStream())
                saveMediaToStorage(image, snackBar)
            } catch (e: IOException) {
                e.message?.let { Log.e(_tag, it) }
            }
        }.start()
    }

    private fun saveMediaToStorage(bitmap: Bitmap, snackBar: Snackbar) {
        val filename = "${UUID.randomUUID()}.jpg"
        var outputStream: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // To save the image in a version of Android over Android 9
            context?.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                outputStream = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // Save the image in an old version of android
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            outputStream = FileOutputStream(image)
        }

        outputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            snackBar.dismiss()


            // Notify the image has been downloaded
            Snackbar.make(binding.root, R.string.image_downloaded, Snackbar.LENGTH_LONG).show()
        }
    }
}