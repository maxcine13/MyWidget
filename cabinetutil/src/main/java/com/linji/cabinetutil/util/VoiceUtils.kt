package com.linji.cabinetutil.util

import android.content.Context
import android.media.MediaPlayer

class VoiceUtils private constructor(context: Context, raw: Int) {
    private var mp: MediaPlayer? = null

    init {
        mp = MediaPlayer.create(context, raw)
    }

    fun play() {
        if (mp!!.isPlaying)return
        mp!!.start()
        mp!!.setOnCompletionListener { stop() }
    }

    private fun stop() {
        mp!!.release()
        mp = null
        instance = null
    }

    companion object {

        private var instance: VoiceUtils? = null

        @Synchronized
        fun getInstance(context: Context, raw: Int): VoiceUtils {
            if (instance == null) {
                instance = VoiceUtils(context, raw)
            }
            return instance as VoiceUtils
        }
    }
}
