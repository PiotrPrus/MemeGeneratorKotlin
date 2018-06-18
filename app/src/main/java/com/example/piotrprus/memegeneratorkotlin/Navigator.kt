package com.example.piotrprus.memegeneratorkotlin

import android.content.Context
import android.content.Intent
import android.net.Uri

class Navigator {
    companion object {
        fun redirectToMemeComposerScreen(activity: Context, imageUrl: Uri){
            val intent = Intent(activity, MemeComposerActivity::class.java)
            intent.putExtra(MemeComposerActivity.constants.EXTRA_IMAGE, imageUrl)
            activity.startActivity(intent)
        }
    }
}