package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BillboardEntity(
    @PrimaryKey val id: Int,
    val poster: String?,
    val year: String?
)