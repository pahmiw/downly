package id.downly.data.repository

import id.downly.data.mapper.DataMapper
import id.downly.data.request.AutolinkRequest
import id.downly.data.service.ServiceApi
import id.downly.domain.model.SocialAutolink
import id.downly.domain.repository.Repository
import id.downly.utils.Either
import id.downly.utils.safeApiCall
import javax.inject.Inject

/**
 * @Author Ahmad Pahmi Created on April 2025
 */
class RepositoryImpl @Inject constructor(private val service: ServiceApi) : Repository {
    override suspend fun getSocialAutoLink(url: String): Either<SocialAutolink> {
        return when (val response =
            safeApiCall { service.getSocialAutoLink(AutolinkRequest(url)) }) {
            is Either.Success -> Either.Success(DataMapper.mapSocialAutoLink(response.data))
            is Either.Error -> Either.Error(response.message, response.code)
        }
    }
}