package net.nolit.controller.form

import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class UserUpdateRequest {
    @NotBlank
    @Email
    lateinit var email: String
    @Min(value = 8)
    var password: String = ""
    @NotBlank
    @Max(value = 16)
    lateinit var userName: String
}