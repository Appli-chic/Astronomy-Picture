package com.applichic.astronomypicture.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.applichic.astronomypicture.databinding.FragmentVideoViewerBinding
import com.applichic.astronomypicture.viewmodel.VideoViewerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoViewerFragment : Fragment() {
    private lateinit var binding: FragmentVideoViewerBinding
    private val viewModel: VideoViewerViewModel by viewModels()
    private val args: VideoViewerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVideoViewerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity?.setSupportActionBar(binding.toolbar)
        appCompatActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity?.supportActionBar?.setDisplayShowHomeEnabled(true)
        setHasOptionsMenu(true)

        // Keep the video state even if the phone rotates
        if (savedInstanceState == null) {
            viewModel.setUrl(args.url)
        } else {
            binding.webView.restoreState(savedInstanceState)
        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webView.saveState(outState)
    }
}