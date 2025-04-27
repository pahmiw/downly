package id.downly.data.response


import com.google.gson.annotations.SerializedName

data class SocialAutolinkDto(
    @SerializedName("author")
    val author: String? = null,

    @SerializedName("duration")
    val duration: Int? = null,

    @SerializedName("medias")
    val medias: List<MediaDto>? = null,

    @SerializedName("source")
    val source: String? = null,

    @SerializedName("thumbnail")
    val thumbnail: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("url")
    val url: String? = null
) {
    data class MediaDto(
        @SerializedName("duration")
        val duration: Int? = null,

        @SerializedName("extension")
        val extension: String? = null,

        @SerializedName("quality")
        val quality: String? = null,

        @SerializedName("type")
        val type: String? = null,

        @SerializedName("url")
        val url: String? = null
    )
}
