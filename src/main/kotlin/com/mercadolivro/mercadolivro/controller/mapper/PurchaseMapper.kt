package com.mercadolivro.mercadolivro.controller.mapper

import com.mercadolivro.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.mercadolivro.enums.BookStatus
import com.mercadolivro.mercadolivro.enums.CustomerStatus
import com.mercadolivro.mercadolivro.enums.Errors
import com.mercadolivro.mercadolivro.exceptions.BadRequestException
import com.mercadolivro.mercadolivro.model.PurchaseModel
import com.mercadolivro.mercadolivro.service.BookService
import com.mercadolivro.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component
import java.awt.print.Pageable

@Component
class PurchaseMapper (
    private val bookService: BookService,
    private val customerService: CustomerService
        ){

    fun toModel(request: PostPurchaseRequest): PurchaseModel {
        val customer = customerService.getCustomerById(request.customerId)
        if(customer.status == CustomerStatus.INATIVO){
            throw BadRequestException(Errors.ML1202.message.format(customer.status), Errors.ML1202.code)
        }
        val books = bookService.findAllByIds(request.booksId)
        for (book in books){
            if(book.status == BookStatus.VENDIDO || book.status == BookStatus.CANCELADO || book.status == BookStatus.DELETADO){
                throw BadRequestException(Errors.ML1201.message.format(book.status), Errors.ML1201.code)
            }
        }
        return PurchaseModel(
            customer = customer,
            books = books.toMutableList(),
            price = books.sumOf { it.price }
        )
    }
}