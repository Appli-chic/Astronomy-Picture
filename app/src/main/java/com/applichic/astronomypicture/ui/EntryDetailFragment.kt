package com.applichic.astronomypicture.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.applichic.astronomypicture.databinding.FragmentEntryDetailBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.applichic.astronomypicture.utils.network.Status
import com.applichic.astronomypicture.viewmodel.EntryDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EntryDetailFragment : Fragment() {
    private lateinit var binding: FragmentEntryDetailBinding
    private val viewModel: EntryDetailViewModel by viewModels()
    private val args: EntryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEntryDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val appCompatActivity = activity as AppCompatActivity?

        // Add back button if the instance is the first page and get the entry from the date
        if (!args.isFirstPage) {
            // Load the entry from the date
            val date = Calendar.getInstance()
            date.timeInMillis = args.time
            viewModel.setDate(date)

            appCompatActivity?.setSupportActionBar(binding.toolbar)
            appCompatActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            appCompatActivity?.supportActionBar?.setDisplayShowHomeEnabled(true)
            setHasOptionsMenu(true)
        } else {
            // Load the entry for today
            val date = Calendar.getInstance()
            viewModel.setDate(date)
        }

        viewModel.entryQuery.observe(viewLifecycleOwner, { response ->
            // Load the url from the cache
            if (response.status == Status.LOADING && response.data != null) {
                viewModel.setEntry(response?.data)
            }

            // If the URL from the cache and from the API is the same, we do not reload the image
            if (response.status == Status.SUCCESS && response.data != null &&
                response?.data.url != viewModel.entry.value?.url
            ) {
                viewModel.setEntry(response?.data)
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