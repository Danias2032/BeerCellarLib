package com.example.beercellarapp.models

data class Beer(val id: Int,
                val user: String,
                val brewery: String,
                val name: String,
                val style: String,
                val abv: Double,
                val volume: Int,
                val pictureUrl: String? = null,
                val howMany: Int)
{
    constructor(user: String,
                brewery: String,
                name: String,
                style: String,
                abv: Double,
                volume: Int,
                pictureUrl: String,
                howMany: Int) :
            this(-1, user, brewery, name, style, abv, volume, pictureUrl, howMany)

    override fun toString(): String {
        return "$id, $user, $brewery, $name, $style, $abv, $volume, $pictureUrl, $howMany"
    }
}