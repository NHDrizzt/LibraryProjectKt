package com.mercadolivro.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

class PostBookRequest (

    @field: NotEmpty
    var name: String,

    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int
)