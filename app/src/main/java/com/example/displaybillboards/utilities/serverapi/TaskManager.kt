package com.example.displaybillboards.utilities.serverapi

import android.util.Log
import android.widget.Toast
import com.example.displaybillboards.constants.ERROR_LOG_TAG
import com.example.displaybillboards.models.Billboard
import com.example.displaybillboards.utilities.BaseApplication
import com.example.displaybillboards.utilities.getServerApi
import com.example.displaybillboards.utilities.getTaskWorker

val taskErrorHandler: (Throwable) -> Unit = {
    val errorText = "error task: ${it.message}"
    Log.e(ERROR_LOG_TAG, errorText)
    Toast.makeText(BaseApplication.getApplicationContext(), errorText, Toast.LENGTH_SHORT).show()
}

interface TaskManager {
    fun getBillboardsList(
        onComplete: (ArrayList<Billboard>) -> Unit,
        onError: ((Throwable) -> Unit)? = taskErrorHandler
    )
}

class TaskManagerImpl : TaskManager {
    override fun getBillboardsList(
        onComplete: (ArrayList<Billboard>) -> Unit,
        onError: ((Throwable) -> Unit)?
    ) {
        getTaskWorker().convertCallToObservable(
            getServerApi().getBillboardsList(),
            onComplete, onError
        )
    }
}