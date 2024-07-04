package com.application.snackhub.model

data class Store(
    val name:String,
    val uid:String
){
    constructor():this("","")
}
