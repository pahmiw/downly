package id.downly.entity

import androidx.annotation.DrawableRes

/**
 * @Author Ahmad Pahmi Created on December 2024
 */
data class SupportedFile(
    val id: Int,
    val name: String,
    @DrawableRes val image: Int
)
