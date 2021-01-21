package com.example.displaybillboards.models

class Billboard {
    var id = 0
    var poster = ""
    var year = ""

    override fun equals(other: Any?): Boolean {
        return id == ((other as? Billboard)?.id ?: -1)
    }
}