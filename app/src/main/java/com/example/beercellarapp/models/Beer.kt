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
    /*constructor(id: Int, // Kun nødvendig, hvis man vil ændre ift. standard konstruktør.
                brewery: String,
                name: String,
                style: String,
                abv: Double,
                volume: Double,
                //pictureUrl: String,
                howMany: Int) :
            this(id, brewery, name, style, abv, volume, howMany)*/

    override fun toString(): String {
        return "$id, $brewery, $name, $style, $abv, $volume, $howMany"
    }
}