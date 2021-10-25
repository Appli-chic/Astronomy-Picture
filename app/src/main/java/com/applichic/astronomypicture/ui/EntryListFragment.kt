package com.applichic.astronomypicture.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.applichic.astronomypicture.R
import com.applichic.astronomypicture.databinding.FragmentEntryListBinding
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.db.model.MediaType
import com.applichic.astronomypicture.ui.adapter.EntryGridAdapter
import com.applichic.astronomypicture.utils.network.Status
import com.applichic.astronomypicture.viewmodel.EntryListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.RecyclerView


@AndroidEntryPoint
class EntryListFragment : Fragment() {
    private lateinit var binding: FragmentEntryListBinding

    private val viewModel: EntryListViewModel by viewModels()

    private var pastVisibleItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntryListBinding.inflate(inflater, container, false)

        val adapter = EntryGridAdapter(activity as AppCompatActivity)
        val layoutManager = GridLayoutManager(context, 3)
        binding.recyclerViewPhotos.adapter = adapter
        binding.recyclerViewPhotos.layoutManager = layoutManager

        // Load more with scroll
        binding.recyclerViewPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (!viewModel.isLoading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            viewModel.loadMore()
                        }
                    }
                }
            }
        })

        // Load the entries
        viewModel.entries.observe(viewLifecycleOwner, { response ->
            if (response.status == Status.ERROR) {
                binding.progressBarEntries.visibility = View.GONE
                viewModel.isLoading = false
                showErrorLoading()
            }

            if ((response.status == Status.SUCCESS || response.status == Status.LOADING) &&
                response.data != null && response.data.isNotEmpty()
            ) {
                binding.progressBarEntries.visibility = View.GONE
                viewModel.isLoading = false

                if (adapter.itemCount == 0) {
                    adapter.submitList(getEntriesToDisplay(response.data))
                } else {
                    val newList = getEntriesToDisplay(response.data)
                    newList.addAll(0, adapter.currentList)
                    adapter.submitList(newList)
                }
            }
        })

        return binding.root
    }

    /**
     * Filter the list of entries removing the ones that can't be displayed
     *
     * Which are:
     * - Video without thumbnails
     * - Video that are web pages
     */
    private fun getEntriesToDisplay(entries: List<Entry>): MutableList<Entry> {
        return entries.filter { entry ->
            entry.mediaType == MediaType.Image ||
                    (entry.mediaType == MediaType.Video && entry.thumbnailUrl != null && !entry.url.endsWith(
                        ".html"
                    ))
        }.toMutableList()
    }

    /**
     * Displays a snack bar to show an error happened while loading the entries
     */
    private fun showErrorLoading() {
        val snackbar =
            Snackbar.make(binding.root, R.string.network_error_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry) {
                    // Retry to load entries
                    viewModel.reloadEntries()
                }

        snackbar.anchorView = activity?.findViewById(R.id.bottom_navigation)
        snackbar.show()
    }
}