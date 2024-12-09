package com.liontail.arfind.register

class UserInfo {
    private var email: String? = null
    private var password: String? = null
    private var nombre: String? = null
    private var apellido: String? = null
    private var telefono: String? = null
    private var edad: Int? = null

    constructor()
    constructor(
        email: String?,
        password: String?,
        nombre: String?,
        apellido: String?,
        telefono: String?,
        edad: Int?
    ) {
        this.email = email
        this.password = password
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
        this.edad = edad
    }
}