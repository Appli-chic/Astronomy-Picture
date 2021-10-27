package com.applichic.astronomypicture.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.applichic.astronomypicture.databinding.FragmentImageViewerBinding
import com.applichic.astronomypicture.viewmodel.ImageViewerViewModel
import com.github.piasy.biv.loader.ImageLoader.Callback
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.lang.Exception

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

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }
}