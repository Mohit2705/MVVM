package com.example.msharma.dashlite.data

import com.google.gson.annotations.SerializedName


data class Restaurant(val name: String,
                      val id: String,
                      val description: String,
                      @SerializedName("status") val duration: String,
                      @SerializedName("cover_img_url") val imageUrl: String,
                      @SerializedName("phone_number") val phoneNumber: String,
                      @SerializedName("average_rating") val rating: Float,
                      val address: Address)

data class Address(@SerializedName("printable_address") val address: String)
