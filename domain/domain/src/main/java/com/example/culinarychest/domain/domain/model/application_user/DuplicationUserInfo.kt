package com.example.culinarychest.domain.domain.model.application_user

import com.google.gson.annotations.SerializedName

data class DuplicationUserInfo(
    @SerializedName("DuplicateUserName")
    val duplicateUserName: String?,
    @SerializedName("DuplicateEmail")
    val duplicateEmail: String?
)
