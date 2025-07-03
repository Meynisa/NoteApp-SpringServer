package com.aprikot.springboot_crash_course.dto.requests

import jakarta.validation.constraints.NotBlank

data class NoteRequest(
    val id: String?,
    @field:NotBlank(message = "Title can't be blank.")
    val title: String,
    val content: String,
    val color: Long
)
