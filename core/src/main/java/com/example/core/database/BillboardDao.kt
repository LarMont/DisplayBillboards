package com.example.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core.database.entity.BillboardEntity

@Dao
interface BillboardDao {
    @Query("SELECT * FROM billboardentity")
    fun getAll(): List<BillboardEntity>

    @Insert
    fun insertAll(billboards: List<BillboardEntity>)

    @Query("DELETE FROM billboardentity")
    fun deleteAll()
}