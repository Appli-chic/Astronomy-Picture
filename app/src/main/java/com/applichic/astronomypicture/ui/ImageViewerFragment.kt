package com.applichic.astronomypicture.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.github.piasy.biv.view.ImageSaveCallback
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executors


@AndroidEntryPoint
class ImageViewerFragment : Fragment() {
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

        // Save the image
        binding.previewImage.setImageSaveCallback(object : ImageSaveCallback {
            override fun onSuccess(uri: String) {
                Snackbar.make(binding.root, R.string.image_saved, Snackbar.LENGTH_LONG)
                    .show()
            }

            override fun onFail(t: Throwable) {
                t.printStackTrace()
                Snackbar.make(binding.root, R.string.error_save_image, Snackbar.LENGTH_LONG)
                    .show()
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.image_viewer_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        } else if (item.itemId == R.id.image_viewer_save) {
            // Save the image and ask for the permissions
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            }

            Executors.newSingleThreadExecutor().execute {
                requireActivity().runOnUiThread {
                    binding.previewImage.saveImageIntoGallery()
                }
            }


        }

        return super.onOptionsItemSelected(item)
    }
}