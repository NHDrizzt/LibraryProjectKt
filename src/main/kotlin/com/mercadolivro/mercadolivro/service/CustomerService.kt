package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.enums.CustomerStatus
import com.mercadolivro.mercadolivro.enums.Errors
import com.mercadolivro.mercadolivro.exceptions.NotFoundException
import com.mercadolivro.mercadolivro.model.CustomerModel
import com.mercadolivro.mercadolivro.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable

@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val bookService: BookService
) {

    fun getAllCustomer(name: String?, pageable: Pageable): Page<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it, pageable)
        }
        return customerRepository.findAll(pageable)
    }

    fun create(customer: CustomerModel) {
        customerRepository.save(customer)
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

}