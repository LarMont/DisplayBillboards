package com.example.displaybillboards.utilities.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.displaybillboards.models.Billboard
import com.example.displaybillboards.utilities.BaseApplication
import com.example.displaybillboards.utilities.database.entity.BillboardEntity

interface DBWorker {
    suspend fun getSavedBillboards(): ArrayList<Billboard>
    suspend fun resaveBillboards(billboards: ArrayList<Billboard>)
}

class DBWorkerImpl : DBWorker {
    @Database(entities = [BillboardEntity::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun billboardDao(): BillboardDao
    }

    private val db = Room.databaseBuilder(
        BaseApplication.getApplicationContext(),
        AppDatabase::class.java, "display_billboards"
    ).build()

    override suspend fun getSavedBillboards(): ArrayList<Billboard> {
        return db.billboardDao().getAll()
            .map {
                Billboard().apply {
                    id = it.id
                    poster = it.poster ?: ""
                    year = it.year ?: ""
                }
            }
            .toCollection(arrayListOf())
    }

    override suspend fun resaveBillboards(billboards: ArrayList<Billboard>) {
        db.billboardDao().apply {
            deleteAll()
            insertAll(billboards.map { BillboardEntity(it.id, it.poster, it.year) })
        }
    }
}