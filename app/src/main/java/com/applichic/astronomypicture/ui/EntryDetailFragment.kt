package com.applichic.astronomypicture.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.applichic.astronomypicture.databinding.FragmentEntryDetailBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.applichic.astronomypicture.R
import com.applichic.astronomypicture.db.model.MediaType
import com.applichic.astronomypicture.utils.DateConverter
import com.applichic.astronomypicture.utils.network.Status
import com.applichic.astronomypicture.viewmodel.EntryDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EntryDetailFragment : Fragment() {
    private lateinit var binding: FragmentEntryDetailBinding
    private val viewModel: EntryDetailViewModel by viewModels()
    private val args: EntryDetailFragmentArgs by navArgs()
    private var appBarMenu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEntryDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity?.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        // Get the entry from the date
        loadEntry()

        viewModel.entryQuery.observe(viewLifecycleOwner, { response ->
            //TODO: Manage the error

            // Load the url from the cache
            if (response.status == Status.LOADING && response.data != null) {
                viewModel.setEntry(response?.data)

                if (appBarMenu != null) {
                    setMenuFavorite(appBarMenu!!)
                }
            }

            if (response.status == Status.SUCCESS && response.data != null) {
                viewModel.setEntry(response?.data)

                if (appBarMenu != null) {
                    setMenuFavorite(appBarMenu!!)
                }
            }
        })

        // On the click on the media
        binding.imageEntry.setOnClickListener {
            if (viewModel.entry.value != null && viewModel.entry.value!!.mediaType != null) {
                val navHostFragment =
                    activity?.supportFragmentManager?.findFragmentById(R.id.nav_host) as NavHostFragment?
                val navController = navHostFragment?.navController

                if (args.isFirstPage) {
                    when (viewModel.entry.value!!.mediaType) {
                        MediaType.Image -> {
                            val action = MainBottomNavigationFragmentDirections.actionImageViewer(
                                viewModel.entry.value!!.url,
                                viewModel.entry.value!!.hdUrl
                            )

                            navController?.navigate(action)
                        }
                        MediaType.Video -> {
                            val action = MainBottomNavigationFragmentDirections.actionVideoViewer(
                                viewModel.entry.value!!.url,
                            )

                            navController?.navigate(action)
                        }
                    }
                } else {
                    when (viewModel.entry.value!!.mediaType) {
                        MediaType.Image -> {
                            val action = EntryDetailFragmentDirections.actionImageViewer(
                                viewModel.entry.value!!.url,
                                viewModel.entry.value!!.hdUrl
                            )

                            navController?.navigate(action)
                        }
                        MediaType.Video -> {
                            val action = EntryDetailFragmentDirections.actionVideoViewer(
                                viewModel.entry.value!!.url,
                            )

                            navController?.navigate(action)
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun loadEntry() {
        val appCompatActivity = activity as AppCompatActivity?

        if (!args.isFirstPage) {
            // Load the entry from the date
            val date = Calendar.getInstance()
            date.timeInMillis = args.time
            viewModel.setDate(date)

            // Add back button if the instance is the first page
            appCompatActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            appCompatActivity?.supportActionBar?.setDisplayShowHomeEnabled(true)
        } else {
            // Load the entry for today
            val date = Calendar.getInstance()

            // Calculate the difference of time between nasa and phone position
            // At midnight in France, no photo is added yet
            // https://github.com/nasa/apod-api/issues/26
            DateConverter.adaptToNasaTimeZone(date)

            viewModel.setDate(date)
        }
    }

    private fun setMenuFavorite(menu: Menu) {
        if (viewModel.entry.value != null) {
            val item = menu.findItem(R.id.favorite)
            if (viewModel.entry.value?.isFavorite == true) {
                item.setIcon(R.drawable.ic_favorite)
            } else {
                item.setIcon(R.drawable.ic_favorite_outlined)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.entry_detail_menu, menu)
        appBarMenu = menu
        setMenuFavorite(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        } else if (item.itemId == R.id.favorite) {
            if (viewModel.entry.value != null) {
                if (viewModel.entry.value?.isFavorite == true) {
                    item.setIcon(R.drawable.ic_favorite_outlined)
                } else {
                    item.setIcon(R.drawable.ic_favorite)
                }

                viewModel.updateFavorite(!viewModel.entry.value?.isFavorite!!)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}