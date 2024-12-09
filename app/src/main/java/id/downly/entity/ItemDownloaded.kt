package id.downly.entity

import android.graphics.Bitmap

/**
 * @Author Ahmad Pahmi Created on July 2024
 */
data class ItemDownloaded(
    val name: String,
    val thumbnail: Bitmap? = null,
    val path: String
)
