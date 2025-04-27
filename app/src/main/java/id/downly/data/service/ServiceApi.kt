package id.downly.data.service

import id.downly.data.request.AutolinkRequest
import id.downly.data.response.SocialAutolinkDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @Author Ahmad Pahmi Created on April 2025
 */
interface ServiceApi {
    @POST("social/autolink")
    suspend fun getSocialAutoLink(
        @Body url: AutolinkRequest
    ): Response<SocialAutolinkDto>
}