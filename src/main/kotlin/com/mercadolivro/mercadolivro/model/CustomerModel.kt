package com.mercadolivro.mercadolivro.model

import com.mercadolivro.mercadolivro.enums.BookStatus
import com.mercadolivro.mercadolivro.enums.CustomerStatus
import com.mercadolivro.mercadolivro.enums.Errors
import com.mercadolivro.mercadolivro.exceptions.BadRequestException
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "customer")
data class CustomerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,



)
{
    constructor(
        id: Int? = null,
        name: String,
        email: String,
        status: CustomerStatus?): this(id, name, email){
            this.status = status
        }
    @Column
    var status: CustomerStatus? = null
        set(value){
            if(field == CustomerStatus.INATIVO){
                throw BadRequestException(Errors.ML1102.message.format(field), Errors.ML1102.code)
            }
            field = value
        }
}

