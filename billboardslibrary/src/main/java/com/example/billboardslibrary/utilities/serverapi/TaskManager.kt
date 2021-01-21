package com.example.billboardslibrary.utilities.serverapi

import com.example.billboardslibrary.models.Billboard
import com.example.billboardslibrary.utilities.getServerApi

interface TaskManager {
    suspend fun getBillboardsList() : ArrayList<Billboard>?
}

class TaskManagerImpl : RetrofitCallback(), TaskManager {
    override suspend fun getBillboardsList(): ArrayList<Billboard>? {
        return safeApiCall(
            call = { getServerApi().getBillboardsListAsync().await()},
            errorMessage = "Error Fetching Billboards"
        )
    }
}