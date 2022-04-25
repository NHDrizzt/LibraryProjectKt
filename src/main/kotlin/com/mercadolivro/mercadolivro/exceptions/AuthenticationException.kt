package com.mercadolivro.mercadolivro.exceptions

class AuthenticationException (override val message: String, val errorCode: String) : Exception()