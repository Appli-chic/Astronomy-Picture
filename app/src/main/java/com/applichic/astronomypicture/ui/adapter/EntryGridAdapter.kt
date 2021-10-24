package com.applichic.astronomypicture.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.applichic.astronomypicture.R
import com.applichic.astronomypicture.databinding.ItemGridEntryBinding
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.viewmodel.EntryGridViewModel

class EntryGridAdapter(private val activity: AppCompatActivity) :
    ListAdapter<Entry, EntryGridAdapter.ViewHolder>(EntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_grid_entry,
                parent,
                false
            ),
            activity
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemGridEntryBinding,
        private val activity: AppCompatActivity
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: Entry) {
            with(binding) {
                viewModel = EntryGridViewModel(entry, activity)
                executePendingBindings()
            }
        }
    }
}

private class EntryDiffCallback : DiffUtil.ItemCallback<Entry>() {
    override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem == newItem
    }
}