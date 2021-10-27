package com.applichic.astronomypicture.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.applichic.astronomypicture.databinding.FragmentFavoriteListBinding
import com.applichic.astronomypicture.ui.adapter.EntryGridAdapter
import com.applichic.astronomypicture.utils.network.Status
import com.applichic.astronomypicture.viewmodel.FavoriteListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding
    private val viewModel: FavoriteListViewModel by viewModels()
    private lateinit var adapter: EntryGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        val layoutManager = GridLayoutManager(context, 3)
        adapter = EntryGridAdapter(activity as AppCompatActivity)
        binding.recyclerViewFavorites.adapter = adapter
        binding.recyclerViewFavorites.layoutManager = layoutManager

        viewModel.favorites.observe(viewLifecycleOwner, { response ->
            binding.imageNoData.visibility = View.GONE

            if (response.status == Status.LOADING && (response.data == null || response.data.isEmpty())) {
                binding.imageNoData.visibility = View.VISIBLE
            }

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