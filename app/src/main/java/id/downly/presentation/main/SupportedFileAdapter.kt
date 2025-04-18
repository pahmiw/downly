package id.downly.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import id.downly.databinding.ItemSupportedFileBinding
import id.downly.domain.model.SupportedFile

/**
 * @Author Ahmad Pahmi Created on December 2024
 */
class SupportedFileAdapter(private val items: List<SupportedFile>) : BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): SupportedFile = items[position]

    override fun getItemId(position: Int): Long = items[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemSupportedFileBinding
        val view: View

        if (convertView == null) {
            binding = ItemSupportedFileBinding.inflate(
                LayoutInflater.from(parent?.context), parent, false
            )
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemSupportedFileBinding
            view = convertView
        }

        val item = items[position]
        binding.ivFileType.setImageResource(item.image)
        binding.tvName.text = item.name

        return view
    }

}