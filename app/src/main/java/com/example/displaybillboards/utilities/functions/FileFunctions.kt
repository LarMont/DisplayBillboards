package com.example.displaybillboards.utilities.functions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment.getExternalStorageDirectory
import com.example.displaybillboards.constants.APPLICATION_CACHE_PATH
import com.example.displaybillboards.constants.CODING
import com.example.displaybillboards.utilities.serverapi.FileSaver
import com.example.displaybillboards.utilities.serverapi.FileType
import com.example.displaybillboards.utilities.serverapi.ProgressTracker
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


class FileFunctions {////удалить лишнее
    companion object {
        private val fileDirectoriesByType: Map<FileType, String> = mapOf(
            Pair(
                FileType.IMAGE,
                "${getExternalStorageDirectory().absolutePath}/$APPLICATION_CACHE_PATH/IMAGE"
            )
        )

        fun getFileByType(type: FileType, fileName: String) =
            File("${getDirectoryByFileType(type)}/$fileName")

        fun writeToFile(file: File, string: String, isAppend: Boolean): Boolean {
            return try {
                writeToFile(file, string.toByteArray(charset(CODING)), isAppend)
            } catch (e: Exception) {
                false
            }
        }

        fun writeToFile(file: File, input: ByteArray, isAppend: Boolean): Boolean {
            var result = true
            try {
                if (input.isEmpty()) result = true
                val fos = FileOutputStream(file, isAppend)
                fos.write(input)
                fos.flush()
                fos.close()
            } catch (e: Exception) {
                result = false
            }
            return result
        }

        private fun getDirectoryByFileType(type: FileType): String {
            val result = fileDirectoriesByType[type] ?: throw Exception("Не определён путь к файлам типа $type")
            val resultFile = File(result)
            if(!resultFile.exists()) {
                resultFile.mkdirs()
            }
            return result
        }

        @Throws(Exception::class)
        private fun unzipFile(inputFile: File, outFile: File, tracker: ProgressTracker? = null) {
            if(!inputFile.exists() || inputFile.length() == 0L) {
                throw Exception()
            }
            outFile.parentFile.mkdirs()
            val zipInput = ZipInputStream(FileInputStream(inputFile))
            zipInput.use {
                val entry: ZipEntry? = zipInput.nextEntry
                if (entry != null) {
                    zipInput.saveEntryToFile(outFile.absolutePath, entry.size, tracker)
                } else {
                    throw Exception()
                }
            }
            inputFile.delete()
        }

        @Throws(IOException::class)
        private fun unzipDirectory(
            inputFile: File,
            outputDirectory: String,
            tracker: ProgressTracker? = null
        ) {
            if(!inputFile.exists() || inputFile.length() == 0L) {
                throw Exception()
            }
            var cancelled = false
            tracker?.initCancel { cancelled = true }
            createDirectory(outputDirectory)
            val zipInput = ZipInputStream(FileInputStream(inputFile))
            val fullSize = inputFile.length()
            var readedBytesCount = 0L
            zipInput.use { //zipInput ->
                var entry: ZipEntry? = zipInput.nextEntry
                while (entry != null && !cancelled) {
                    val destName = "$outputDirectory/${entry.name}"
                    if (entry.isDirectory) {
                        createDirectory(destName.replace("\\", "/"))
                    } else {
                        zipInput.saveEntryToFile(destName, entry.size)
                    }
                    readedBytesCount += entry.compressedSize
                    tracker?.setProgress(readedBytesCount.toFloat() / fullSize)
                    entry = zipInput.nextEntry
                }
                if(cancelled) {
                    throw Exception()
                }
            }
            inputFile.delete()
        }

        private fun createDirectory(dir: String) {
            val f = File(dir)
            if (!f.exists()) {
                f.mkdirs()
            }
        }

        fun clearDownloadDirectoryAsync() {
            val dir = File(fileDirectoriesByType[FileType.IMAGE])
            Observable.just(dir)
                .subscribeOn(Schedulers.newThread())
                .subscribe { deleteFilesInDirectory(it) }
        }

        private fun deleteFilesInDirectory(f: File) {
            f.listFiles()?.forEach { file: File ->
                if (file.isDirectory) {
                    deleteFilesInDirectory(file)
                }
                file.delete()
            }
        }

        fun deleteFilesAndDirectory(f: File) {
            deleteFilesInDirectory(f)
            f.delete()
        }


        @Throws(FileNotFoundException::class, IllegalArgumentException::class)
        fun DecodeSampledBitmapFromFile(
            file: File,
            reqWidth: Int,
            reqHeight: Int
        ): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            val filePath: String = file.absolutePath
            BitmapFactory.decodeFile(filePath, options)
            options.inSampleSize =
                CalculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(filePath, options)
        }


        @Throws(java.lang.IllegalArgumentException::class)
        fun CalculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            require(!(reqHeight == 0 && reqWidth == 0)) { "Ширина и высота не могут быть нулевыми одновременно" }
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2
                while ((halfHeight / inSampleSize >= reqHeight || reqHeight == 0)
                    && (halfWidth / inSampleSize >= reqWidth || reqWidth == 0)
                ) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }
    }
}

fun InputStream.getFileSaver(fileSize: Long = 0L) = FileSaver(this, fileSize)

fun ZipInputStream.saveEntryToFile(path: String, entrySize: Long, tracker: ProgressTracker? = null) {
    var cancelled = false
    tracker?.initCancel { cancelled = true }
    val file = File(path.replace("\\", "/"))
    file.delete()
    file.parentFile.mkdirs()
    val fileOutput = FileOutputStream(file)
    val data = ByteArray(262144)
    var x = read(data)
    var readedBytesCount = 0L
    fileOutput.use {
        while (x > 0 && !cancelled) {
            fileOutput.write(data, 0, x)
            readedBytesCount += x
            tracker?.setProgress(readedBytesCount.toFloat() / entrySize)
            x = read(data)
        }
        if(cancelled) {
            file.delete()
            throw Exception()
        }
        fileOutput.flush()
        closeEntry()
    }
}