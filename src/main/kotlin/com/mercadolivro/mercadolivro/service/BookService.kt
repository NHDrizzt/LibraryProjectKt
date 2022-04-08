package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.enums.BookStatus
import com.mercadolivro.mercadolivro.exceptions.NotFoundException
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.model.CustomerModel
import com.mercadolivro.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun getAllBooks(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    fun create(book : BookModel) {
        bookRepository.save(book)
    }

    fun findBookById(id: Int): BookModel {
       return bookRepository.findById(id).orElseThrow{ NotFoundException("Book ${id} not exists", "ML-0001") }
    }

    fun findByActive(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
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