package com.mercadolivro.mercadolivro.enums

enum class Errors (val message:String, val code:String){
    ML0001("Invalid Request", "ML-0001"),
    ML1001("Book [%s] not exists", "ML-1001"),
    ML1002("Can't update book with status [%s]", "ML-1002"),
    ML1101("Customer [%s] not exists", "ML-1102"),
    ML1102("Can't update customer with status [%s]", "ML-1102"),
    ML1201("Can't purchase book with status [%s]", "ML1201"),
    ML1202("Can't purchase with customer status [%s]", "ML1202")
}