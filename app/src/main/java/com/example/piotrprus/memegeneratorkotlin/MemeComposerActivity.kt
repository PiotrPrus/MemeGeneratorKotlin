package com.example.piotrprus.memegeneratorkotlin

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_meme_composer.*
import java.util.jar.Manifest

class MemeComposerActivity: AppCompatActivity() {
    object constants {
        val EXTRA_IMAGE = "extra_img"
        val PERMISSION_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme_composer)
        text_input.afterTextChanged {
            text: String -> meme_txt.text = text
        }
        val imageUri = intent.getParcelableExtra<Uri>(constants.EXTRA_IMAGE)
        image.setImageURI(imageUri)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.meme_composer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.save_meme) {
            val result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (PackageManager.PERMISSION_GRANTED == result) {
                onPermissionGranted.invoke()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), constants.PERMISSION_CODE)
            }
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            constants.PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermissionGranted.invoke()
                 } else {
                    onPermissionRefused.invoke()
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private val onPermissionGranted = {
        val bitmap = Bitmap.createBitmap(meme_layout.width, meme_layout.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        meme_layout.draw(canvas)

        val onSave = {
            url: Uri? ->
            if (url != null) {
                Navigator.redirectToMemeResult(this, url)
            }
        }

        bitmap.saveToGallery(this, text_input.toString().toLowerCase()+System.currentTimeMillis(), onSave)
    }

    private val onPermissionRefused = {
        Toast.makeText(this, "Storage write permission required!", Toast.LENGTH_LONG).show()
    }

}