package com.application.snackhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Order(
    val id: String,
    val date:Date,
    val totalPrice:String,
    val state:String
):Parcelable {

    constructor():this("",Date(),"","")
}