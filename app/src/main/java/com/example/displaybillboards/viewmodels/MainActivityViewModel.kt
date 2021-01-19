package com.example.displaybillboards.viewmodels

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModel
import com.example.displaybillboards.utilities.getFileTaskManager
import com.example.displaybillboards.utilities.getTaskManager
import com.example.displaybillboards.utilities.serverapi.BASE_URL

class MainActivityViewModel : ViewModel() {

    fun onClick(view: View) {
        getTaskManager().getBillboardsList({ billboards ->
            val imagePath = billboards[0].poster.replace(BASE_URL, "")
            getFileTaskManager().downloadImage(imagePath, {
                it.toURI()
                val image =
                    BitmapFactory.decodeFile(it.absolutePath, BitmapFactory.Options().apply {
                        inJustDecodeBounds = false
                    })
                (view as ImageButton).setImageBitmap(image)
            })
        })
    }
}