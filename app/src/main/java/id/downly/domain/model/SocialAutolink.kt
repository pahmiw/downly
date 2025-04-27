package id.downly.domain.model

/**
 * @Author Ahmad Pahmi Created on April 2025
 */
data class SocialAutolink(
    val author: String,
    val duration: Int,
    val medias: List<Media>,
    val source: String,
    val thumbnail: String,
    val title: String,
    val type: String,
    val url: String
) {
    data class Media(
        val duration: Int,
        val extension: String,
        val quality: String,
        val type: String,
        val url: String
    )
}
