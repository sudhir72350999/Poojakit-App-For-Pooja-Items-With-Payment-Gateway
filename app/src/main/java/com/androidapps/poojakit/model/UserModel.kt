package com.androidapps.poojakit.model

data class UserModel(
    val userName: String?="",
    val userPhoneNumber: String?="",
    val village: String?="",
    val state: String?="",
    val city: String?="",
    val pincode: String?="",

    // we can take other details here like exact location and email address

)
