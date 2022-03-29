package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.enums.BookStatus
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.model.CustomerModel
import com.mercadolivro.mercadolivro.repository.BookRepository
import com.mercadolivro.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun getAllBooks() : List<BookModel> {
        return bookRepository.findAll().toList()
    }

    fun create(book : BookModel) {
        bookRepository.save(book)
    }

    fun findBookById(id: Int): BookModel {
       return bookRepository.findById(id).get()
    }

    fun findByActive(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

    fun update(book: BookModel) {
        bookRepository.save(book)
    }

    fun delete(id: Int) {
        val book = findBookById(id)
        book.status = BookStatus.CANCELADO
        bookRepository.save(book)

    }

    fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomer(customer)
        for(book in books){
            book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }


}