package com.aprikot.springboot_crash_course.database.model

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)
