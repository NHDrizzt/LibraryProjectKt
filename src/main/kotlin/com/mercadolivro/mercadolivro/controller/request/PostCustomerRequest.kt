package com.mercadolivro.mercadolivro.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest (

    @field:NotEmpty
    var name: String,

    @field:Email(message = "E-mail deve ser válido")
    var email: String
)