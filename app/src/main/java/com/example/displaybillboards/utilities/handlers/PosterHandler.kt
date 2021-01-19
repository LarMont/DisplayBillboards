package com.example.displaybillboards.utilities.handlers

import androidx.room.*
import com.example.displaybillboards.constants.POSTER_DATABASE
import com.example.displaybillboards.utilities.BaseApplication
import com.example.displaybillboards.utilities.dao.PosterDatabase
import com.example.displaybillboards.utilities.getFileTaskManager
import java.io.File

interface PosterHandler {
    fun getImageFile(imageName: String, callback: (File) -> Unit)
}

class PosterHandlerImpl : PosterHandler {

    private val db = Room.databaseBuilder(
        BaseApplication.getApplicationContext(),
        PosterDatabase::class.java, POSTER_DATABASE
    ).build()
    private val posterDao = db.posterDao()

    @Synchronized
    override fun getImageFile(imageName: String, callback: (File) -> Unit) {
        val imagePath = posterDao.findByName(imageName)?.posterPath
        if (imagePath != null) {
            callback.invoke(File(imagePath))
        } else {
            getFileTaskManager().downloadImage(imageName, {file ->
                callback.invoke(file)
            })
        }
    }
}