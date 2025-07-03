package com.aprikot.springboot_crash_course.controller

import com.aprikot.springboot_crash_course.database.model.Note
import com.aprikot.springboot_crash_course.database.repository.NoteRepository
import com.aprikot.springboot_crash_course.dto.mappers.toNoteResponse
import com.aprikot.springboot_crash_course.dto.requests.NoteRequest
import com.aprikot.springboot_crash_course.dto.responses.NoteResponse
import jakarta.validation.Valid
import org.bson.types.ObjectId
import org.springframework.http.HttpStatusCode
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@RestController
@RequestMapping("/notes")
class NoteController(
    private val repository: NoteRepository
) {


    @PostMapping
    fun save(
        auth: Authentication,
        @Valid @RequestBody body: NoteRequest
    ): NoteResponse {
        val ownerId = auth.principal
        print("====> $ownerId")
        val note = repository.save(
            Note(
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = body.title,
                content = body.content,
                color = body.color,
                ownerId = ObjectId("$ownerId"),
                createdAt = Instant.now()
            )
        )

        return note.toNoteResponse()
    }

    @GetMapping
    fun findByOwnerId (): List<NoteResponse> {
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String

        return repository.findByOwnerId(ObjectId(ownerId)).map { it.toNoteResponse() }
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        val note = repository.findById(ObjectId(id)).orElseThrow {
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Note not found")
        }

        val ownerId = SecurityContextHolder.getContext().authentication.principal as String

        if (note.ownerId.toHexString() == ownerId) {
            repository.deleteById(ObjectId(id))
        }
    }
}