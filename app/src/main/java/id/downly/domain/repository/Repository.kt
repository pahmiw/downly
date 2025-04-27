package id.downly.domain.repository

import id.downly.domain.model.SocialAutolink
import id.downly.utils.Either

/**
 * @Author Ahmad Pahmi Created on April 2025
 */
interface Repository {
    suspend fun getSocialAutoLink(url: String): Either<SocialAutolink>
}