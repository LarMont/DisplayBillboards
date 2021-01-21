package com.example.billboardslibrary.utilities.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.billboardslibrary.models.Billboard
import com.example.billboardslibrary.utilities.database.entity.BillboardEntity

interface DBWorker {
    suspend fun getSavedBillboards(): ArrayList<Billboard>
    suspend fun resaveBillboards(billboards: ArrayList<Billboard>)
}

class DBWorkerImpl(context: Context) : DBWorker {
    @Database(entities = [BillboardEntity::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun billboardDao(): BillboardDao
    }

    private val db = Room.databaseBuilder(
        context,
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