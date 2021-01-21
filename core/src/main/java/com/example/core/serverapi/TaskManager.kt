package com.example.core.serverapi

import com.example.core.getServerApi
import com.example.core.models.Billboard

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