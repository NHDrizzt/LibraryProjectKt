package com.mercadolivro.mercadolivro.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PutCustomerRequest (

    @field:NotEmpty
    var name: String,

    @field:Email
    var email: String
)