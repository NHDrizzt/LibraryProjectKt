package com.mercadolivro.mercadolivro.exceptions

class NotFoundException(override val message: String, val errorCode: String) : Exception(){

}