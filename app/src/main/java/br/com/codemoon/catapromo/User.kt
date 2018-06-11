package br.com.codemoon.catapromo

class User {

    var nome: String = ""
    var email: String = ""
    var longitude: String = ""
    var latitude: String = ""

    constructor() {}

    constructor(nome: String, email: String, longitude: String, latitude: String) {

        this.nome = nome
        this.email = email
        this.longitude = longitude
        this.latitude = latitude
    }

}