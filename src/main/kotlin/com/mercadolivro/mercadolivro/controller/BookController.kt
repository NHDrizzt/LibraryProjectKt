package com.mercadolivro.mercadolivro.controller

import com.mercadolivro.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.mercadolivro.controller.response.BookResponse
import com.mercadolivro.mercadolivro.extension.toBookModel
import com.mercadolivro.mercadolivro.extension.toResponse
import com.mercadolivro.mercadolivro.service.BookService
import com.mercadolivro.mercadolivro.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/book")
class BookController (
    val bookService: BookService,
    val customerService: CustomerService
){
    @GetMapping
    fun getAllBook(@PageableDefault(page = 0, size = 10) pageable : Pageable): Page<BookResponse>{
        return bookService.getAllBooks(pageable).map { it.toResponse() }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: PostBookRequest) {
        val customer = customerService.getCustomerById(request.customerId)
        return bookService.create(request.toBookModel(customer))
    }

    @GetMapping("/active")
    fun findActives(@PageableDefault(page = 0, size = 10) pageable : Pageable): Page<BookResponse>{
        return bookService.findByActive(pageable).map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Int): BookResponse {
        return bookService.findBookById(id).toResponse()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody  book: PutBookRequest) {
        val bookSavedOnDatabase = bookService.findBookById(id)
        bookService.update(book.toBookModel(bookSavedOnDatabase))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id:Int) {
        bookService.delete(id)
    }



}
