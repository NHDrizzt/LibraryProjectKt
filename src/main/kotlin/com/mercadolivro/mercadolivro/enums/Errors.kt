package com.mercadolivro.mercadolivro.enums

enum class Errors (val message:String, val code:String){
    ML1001("Book [%s] not exists", "ML-1001"),
    ML1101("Customer [%s] not exists", "ML-1102")
}