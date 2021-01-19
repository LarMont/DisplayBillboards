package com.example.displaybillboards.utilities.serverapi

import com.example.displaybillboards.utilities.getServerApi
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.io.File

interface FileTaskManager {
    fun downloadImage(imagePath: String,
                      onComplete: (File) -> Unit, onError: ((Throwable) -> Unit)? = taskErrorHandler,
                      tracker: ProgressTracker? = null): Disposable?
}

class FileTaskManagerImpl : FileTaskManager {
    override fun downloadImage(
        imagePath: String,
        onComplete: (File) -> Unit,
        onError: ((Throwable) -> Unit)?,
        tracker: ProgressTracker?
    ): Disposable? {
        val result = PublishSubject.create<File>()
        getServerApi().getBillboardImage(imagePath)
            .enqueue(object : RetrofitFileCallback(result, tracker) {
                override fun getFileType() = FileType.IMAGE
                override fun getFileName() = imagePath
            })
        return result.subscribe({ onComplete(it) }, { onError?.invoke(it) })
    }
}