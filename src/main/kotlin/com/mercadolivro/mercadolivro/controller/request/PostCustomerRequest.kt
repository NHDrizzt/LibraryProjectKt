package com.mercadolivro.mercadolivro.controller.request

import com.mercadolivro.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest (

    @field:NotEmpty
    var name: String,

    @field:Email(message = "E-mail deve ser válido")
    @EmailAvailable
    var email: String
)