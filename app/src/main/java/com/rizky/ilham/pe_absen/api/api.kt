package com.rizky.ilham.pe_absen.api


import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface api {
    @Multipart
    @POST("http://10.0.49.16:5001")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): Response<String>
}