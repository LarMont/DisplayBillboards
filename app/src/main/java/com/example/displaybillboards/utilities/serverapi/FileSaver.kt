package com.example.displaybillboards.utilities.serverapi

import com.example.displaybillboards.utilities.functions.FileFunctions
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

enum class FileType {
    IMAGE
}

open class FileSaver(private val inputStream: InputStream,  ////разобраться, возможно переписать
                     private val fileSize: Long = 0L) {

    fun save(type: FileType,
             fileName: String,
             tracker: ProgressTracker? = null): File? {
        var cancelled = false
        tracker?.initCancel { cancelled = true }
        val result = FileFunctions.getFileByType(type, fileName)
        result.parentFile.mkdirs()

        val fos = FileOutputStream(result, false)
        var readedBytesCount = 0L
        val bytes = ByteArray(262144)
        try {
            var currentReaded = inputStream.read(bytes)
            while (currentReaded > 0 && !cancelled) {
                fos.write(bytes, 0, currentReaded)
                readedBytesCount += currentReaded.toLong()
                if(tracker != null && fileSize > 0L) {
                    val progress = readedBytesCount.toFloat() / fileSize
                    tracker.setProgress(progress)
                }
                currentReaded = inputStream.read(bytes)
            }
            if(cancelled) {
                result.delete()
                throw Exception()
            }
        } finally {
            fos.close()
            inputStream.close()
        }

        return result
    }
}