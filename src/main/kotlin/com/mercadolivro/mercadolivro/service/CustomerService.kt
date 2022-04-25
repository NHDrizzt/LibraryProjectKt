package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.controller.mapper.PurchaseMapper
import com.mercadolivro.mercadolivro.enums.CustomerStatus
import com.mercadolivro.mercadolivro.enums.Errors
import com.mercadolivro.mercadolivro.enums.Profile
import com.mercadolivro.mercadolivro.exceptions.NotFoundException
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.model.CustomerModel
import com.mercadolivro.mercadolivro.model.PurchaseModel
import com.mercadolivro.mercadolivro.repository.CustomerRepository
import com.mercadolivro.mercadolivro.repository.PurchaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import java.awt.print.Book

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
) {

    fun getAllCustomer(name: String?, pageable: Pageable): Page<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it, pageable)
        }
        return customerRepository.findAll(pageable)
    }

    fun create(customer: CustomerModel) {
        val customerCopy = customer.copy(
            roles = setOf(Profile.CUSTOMER),
            password = bCrypt.encode(customer.password),

        )
        customerCopy.status = CustomerStatus.ATIVO
        customerRepository.save(customerCopy)
    }

    fun getCustomerById(@PathVariable id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow{NotFoundException(Errors.ML1101.message.format(id), Errors.ML1101.code)}
    }

    fun update(customer: CustomerModel) {
        if(!customerRepository.existsById(customer.id!!)){
            throw java.lang.Exception()
        }
        customerRepository.save(customer)
    }

    fun delete(@PathVariable id: Int) {
        val customer = getCustomerById(id)
        customer.status = CustomerStatus.INATIVO

        bookService.deleteByCustomer(customer)
        customerRepository.save(customer)
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }

}