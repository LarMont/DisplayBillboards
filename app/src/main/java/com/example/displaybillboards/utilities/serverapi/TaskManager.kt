package com.example.displaybillboards.utilities.serverapi

import com.example.displaybillboards.models.Billboard
import com.example.displaybillboards.utilities.getServerApi

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