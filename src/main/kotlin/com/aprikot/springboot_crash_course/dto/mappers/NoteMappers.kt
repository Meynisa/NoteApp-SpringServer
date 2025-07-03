package com.aprikot.springboot_crash_course.dto.mappers

import com.aprikot.springboot_crash_course.database.model.Note
import com.aprikot.springboot_crash_course.dto.requests.NoteRequest
import com.aprikot.springboot_crash_course.dto.responses.NoteResponse
import org.bson.types.ObjectId
import java.time.Instant

fun Note.toNoteResponse() = NoteResponse(
    id = id.toHexString(),
    title = title,
    content = content,
    color = color,
    createdAt = createdAt,
)

fun NoteRequest.toNoteEntity() = Note(
    id = id?.let { ObjectId(it) } ?: ObjectId.get(),
    title = title,
    content = content,
    color = color,
    ownerId = ObjectId(),
    createdAt = Instant.now()
)