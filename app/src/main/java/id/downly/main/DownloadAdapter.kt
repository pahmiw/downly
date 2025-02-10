package id.downly.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.downly.R
import id.downly.databinding.ItemDownloadedBinding
import id.downly.entity.ItemDownloaded


/**
 * @Author Ahmad Pahmi Created on July 2024
 */
class DownloadAdapter : RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder>() {

    private var downloadedItems = listOf<ItemDownloaded>()
    private var onItemClicked: ((ItemDownloaded) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {
        val binding =
            ItemDownloadedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DownloadViewHolder(binding, onItemClicked)
    }

    override fun getItemCount(): Int = downloadedItems.size

    override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
        holder.bind(downloadedItems[position])
    }

    fun submitData(newList: List<ItemDownloaded>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = downloadedItems.size
            override fun getNewListSize(): Int = newList.size
            override fun areItemsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean = downloadedItems[oldItemPosition].path == newList[newItemPosition].path

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean = downloadedItems[oldItemPosition] == newList[newItemPosition]
        }

        downloadedItems = newList
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    class DownloadViewHolder(
        private val binding: ItemDownloadedBinding,
        private val onItemClicked: ((ItemDownloaded) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemDownloaded) {

            Glide.with(binding.ivFile)
                .load(item.thumbnail ?: R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivFile)

            binding.tvTitle.text = item.name

            binding.root.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }

    fun setOnItemClicked(item: (ItemDownloaded) -> Unit) {
        this.onItemClicked = item
    }
}