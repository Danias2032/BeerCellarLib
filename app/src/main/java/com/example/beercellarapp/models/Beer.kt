package com.example.beercellarapp.models

data class Beer(val id: Int,
                val brewery: String,
                val name: String,
                val style: String,
                val abv: Double,
                val volume: Double,
                //val pictureUrl: String? = null,
                val howMany: Int)
{
    constructor(brewery: String,
                name: String,
                style: String,
                abv: Double,
                volume: Double,
                //pictureUrl: String,
                howMany: Int) :
            this(0, brewery, name, style, abv, volume, howMany)

    override fun toString(): String {
        return "$id, $brewery, $name, $style, $abv, $volume, $howMany"
    }
}