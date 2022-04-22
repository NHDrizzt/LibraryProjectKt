package com.mercadolivro.mercadolivro.extension

import com.mercadolivro.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.mercadolivro.controller.response.BookResponse
import com.mercadolivro.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.mercadolivro.enums.BookStatus
import com.mercadolivro.mercadolivro.enums.CustomerStatus
import com.mercadolivro.mercadolivro.enums.Errors
import com.mercadolivro.mercadolivro.exceptions.BadRequestException
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.model.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(
        name = this.name,
        email = this.email,
        status = CustomerStatus.ATIVO,
        password = this.password
    )
}

fun PutCustomerRequest.toCustomerModel(customer: CustomerModel): CustomerModel {
    if(customer.status == CustomerStatus.INATIVO){
        throw BadRequestException(Errors.ML1102.message.format(customer.status), Errors.ML1102.code)
    }
    return CustomerModel(
        id = customer.id,
        name = this.name,
        email = this.email,
        status = customer.status,
        password = customer.password
    )}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(
        name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        customer = customer
    )
}

fun PutBookRequest.toBookModel(previousBookValue: BookModel): BookModel {
    if(previousBookValue.status == BookStatus.DELETADO || previousBookValue.status == BookStatus.CANCELADO){
        throw BadRequestException(Errors.ML1002.message.format(previousBookValue.status), Errors.ML1002.code)
    }

    return BookModel(
        id = previousBookValue.id,
        name = this.name ?: previousBookValue.name,
        price = this.price ?: previousBookValue.price,
        customer = previousBookValue.customer,
        status = previousBookValue.status

    )
}

fun CustomerModel.toResponse(): CustomerResponse {

    return CustomerResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        status = this.status
    )
}

fun BookModel.toResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer,
        status = this.status
    )
}
