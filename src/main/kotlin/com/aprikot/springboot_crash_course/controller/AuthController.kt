package com.aprikot.springboot_crash_course.controller

import com.aprikot.springboot_crash_course.database.model.TokenPair
import com.aprikot.springboot_crash_course.dto.requests.AuthRequest
import com.aprikot.springboot_crash_course.dto.requests.RefreshRequest
import com.aprikot.springboot_crash_course.security.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody body: AuthRequest
    ) {
        authService.register(body.email, body.password)
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody body: AuthRequest
    ): TokenPair {
        return  authService.login(body.email, body.password)
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody body: RefreshRequest
    ): TokenPair {
        return  authService.refresh(body.refreshToken)
    }
}