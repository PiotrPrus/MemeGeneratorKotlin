package com.example.piotrprus.memegeneratorkotlin

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.afterTextChanged(callback: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            callback.invoke(text.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    })
}

fun Bitmap.saveToGallery(context: Context, title: String, onSave: (Uri) -> Unit) {
    val url = MediaStore.Images.Media.insertImage(context.contentResolver, this, title, "")
    onSave.invoke(Uri.parse(url))
}