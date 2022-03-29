package com.mercadolivro.mercadolivro.controller

import com.mercadolivro.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.mercadolivro.extension.toCustomerModel
import com.mercadolivro.mercadolivro.extension.toResponse
import com.mercadolivro.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customer")
class CustomerController (val customerService : CustomerService){

    @GetMapping
    fun getAllCustomer(@RequestParam name: String?): List<CustomerResponse> {
        return customerService.getAllCustomer(name).map { it.toResponse() }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody customer: PostCustomerRequest) {
        return customerService.create(customer.toCustomerModel())
    }

    @GetMapping("/{id}")
    fun getCustomerById(@PathVariable id: Int): CustomerResponse {
        return customerService.getCustomerById(id).toResponse()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody customer: PutCustomerRequest) {
        customerService.update(customer.toCustomerModel(id))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        customerService.delete(id)
    }

}