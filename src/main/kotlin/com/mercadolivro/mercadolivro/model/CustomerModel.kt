package com.mercadolivro.mercadolivro.model

import com.mercadolivro.mercadolivro.enums.BookStatus
import com.mercadolivro.mercadolivro.enums.CustomerStatus
import com.mercadolivro.mercadolivro.enums.Errors
import com.mercadolivro.mercadolivro.enums.Profile
import com.mercadolivro.mercadolivro.exceptions.BadRequestException
import javax.persistence.*

@Entity(name = "customer")
data class CustomerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    var password: String,



    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    var roles: Set<Profile> = setOf()

)
{
    constructor(
        id: Int? = null,
        name: String,
        email: String,
        password: String,
        status: CustomerStatus?): this(id, name, email, password){
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

