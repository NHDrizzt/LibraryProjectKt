package com.mercadolivro.mercadolivro.model

import com.mercadolivro.mercadolivro.enums.BookStatus
import com.mercadolivro.mercadolivro.enums.CustomerStatus
import com.mercadolivro.mercadolivro.enums.Errors
import com.mercadolivro.mercadolivro.exceptions.BadRequestException
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "book")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null


){
    constructor(
        id: Int? = null,
        name: String,
        price: BigDecimal,
        customer: CustomerModel? = null,
        status: BookStatus?): this(id,name,price,customer){
        this.status = status
    }

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value){
            if(field == BookStatus.DELETADO){
                throw BadRequestException(Errors.ML1002.message.format(field), Errors.ML1002.code)
            }
            field = value
        }


}