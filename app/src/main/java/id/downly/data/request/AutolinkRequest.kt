package id.downly.data.request

import com.google.gson.annotations.SerializedName

/**
 * @Author Ahmad Pahmi Created on April 2025
 */
data class AutolinkRequest(
    @SerializedName("url")
    val url: String
)
