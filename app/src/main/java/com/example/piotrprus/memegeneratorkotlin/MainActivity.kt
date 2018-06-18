package com.example.piotrprus.memegeneratorkotlin

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val CODE_PICK_IMAGE: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_choose_meme_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select meme picture"), CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(CODE_PICK_IMAGE == requestCode && Activity.RESULT_OK == resultCode)
            if(data != null && data.data != null) {
                val imageUri = intent.data
            }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
