package com.example.displaybillboards.utilities.dao

import androidx.room.*

@Entity
class Poster(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "poster_name") val posterName: String?,
    @ColumnInfo(name = "poster_path") val posterPath: String?
)

@Dao
interface PosterDao {
    @Query("SELECT * FROM poster")
    fun getAll(): List<Poster>

    @Query("SELECT * FROM poster WHERE poster_name LIKE :name LIMIT 1")
    fun findByName(name: String): Poster?

    @Insert
    fun insertAll(vararg posters: Poster)////возможно не нужно

    @Delete
    fun delete(poster: Poster)////возможно не нужно
}

@Database(entities = [Poster::class], version = 1)
abstract class PosterDatabase : RoomDatabase() {
    abstract fun posterDao(): PosterDao
}