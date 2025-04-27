package id.downly.data.mapper

import id.downly.data.response.SocialAutolinkDto
import id.downly.domain.model.SocialAutolink
import id.downly.utils.orZero

/**
 * @Author Ahmad Pahmi Created on April 2025
 */
object DataMapper {
    fun mapSocialAutoLink(dataModel: SocialAutolinkDto?): SocialAutolink {
        return with(dataModel) {
            SocialAutolink(
                author = this?.author.orEmpty(),
                duration = this?.duration.orZero(),
                medias = this?.medias?.map {
                    SocialAutolink.Media(
                        duration = it.duration.orZero(),
                        extension = it.extension.orEmpty(),
                        quality = it.quality.orEmpty(),
                        type = it.type.orEmpty(),
                        url = it.url.orEmpty()
                    )
                }.orEmpty(),
                source = this?.source.orEmpty(),
                thumbnail = this?.thumbnail.orEmpty(),
                title = this?.title.orEmpty(),
                type = this?.type.orEmpty(),
                url = this?.url.orEmpty()
            )
        }
    }
}