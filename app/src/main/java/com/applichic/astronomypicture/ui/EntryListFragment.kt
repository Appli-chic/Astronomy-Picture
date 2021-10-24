package com.applichic.astronomypicture.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.applichic.astronomypicture.databinding.FragmentEntryListBinding
import com.applichic.astronomypicture.ui.adapter.EntryGridAdapter
import com.applichic.astronomypicture.utils.network.Status
import com.applichic.astronomypicture.viewmodel.EntryListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EntryListFragment : Fragment() {
    private lateinit var binding: FragmentEntryListBinding

    private val viewModel: EntryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntryListBinding.inflate(inflater, container, false)

        val adapter = EntryGridAdapter(activity as AppCompatActivity)
        binding.recyclerViewPhotos.adapter = adapter
        binding.recyclerViewPhotos.layoutManager = GridLayoutManager(context, 3)

        // Load the entries
        viewModel.entries.observe(viewLifecycleOwner, { response ->
            if (response.status == Status.LOADING && response.data != null && response.data.isNotEmpty()) {
                adapter.submitList(response.data)
            }

            if (response.status == Status.SUCCESS && response.data != null && response.data.isNotEmpty()) {
                adapter.submitList(response.data)
            }
        })

        return binding.root
    }

}