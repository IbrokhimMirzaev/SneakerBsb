package com.example.chsbproject.model

data class Sneaker(
    var isFavourite: Boolean,
    var img: Int,
    var name: String,
    var price: Double,
    var isAddedToCart: Boolean,
    var count: Int = 1,
    var subTotal: Double = price,
) : java.io.Serializable