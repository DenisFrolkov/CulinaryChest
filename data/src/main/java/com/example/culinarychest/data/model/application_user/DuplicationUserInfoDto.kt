package com.example.culinarychest.data.model.application_user

import com.google.gson.annotations.SerializedName

data class DuplicationUserInfoDto(
    @SerializedName("DuplicateUserName")
    val duplicateUserName: String?,
    @SerializedName("DuplicateEmail")
    val duplicateEmail: String?
)
