package com.polysfactory.movielivewallpaper

import android.graphics.Canvas
import android.graphics.Rect
import android.media.MediaPlayer
import android.net.Uri
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder

class MovieLiveWallpaperService : WallpaperService() {
    override fun onCreateEngine(): WallpaperService.Engine {
        return VideoLiveWallpaperEngine()
    }

    private enum class PlayerState {
        NONE, PREPARING, READY, PLAYING
    }

    inner class VideoLiveWallpaperEngine : WallpaperService.Engine() {
        private var mp: MediaPlayer? = null
        private var playerState: PlayerState = PlayerState.NONE
        private var width: Int = 0
        private var height: Int = 0
        private var mySurfaceHolder: MySurfaceHolder? = null


        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            Log.d("AppLog", "onSurfaceCreated")
            mp = MediaPlayer()
            mySurfaceHolder = MySurfaceHolder(holder)
            mp!!.setDisplay(mySurfaceHolder)
            mp!!.isLooping = true
            mp!!.setVolume(0.0f, 0.0f)
            mp!!.setOnPreparedListener { mp ->
                playerState = PlayerState.READY
                setPlay(true)
            }
            try {
                mp!!.setDataSource(this@MovieLiveWallpaperService, Uri.parse("http://techslides.com/demos/sample-videos/small.mp4"))
//                    mp!!.setDataSource(this@MovieLiveWallpaperService, Uri.parse("android.resource://" + packageName + "/" + R.raw.small))
            } catch (e: Exception) {
            }
        }


        override fun onSurfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            this.width = width
            this.height = height
        }


        override fun onDestroy() {
            super.onDestroy()
            if (mp == null)
                return
            mp!!.stop()
            mp!!.release()
            playerState = PlayerState.NONE
        }

        private fun setPlay(play: Boolean) {
            if (mp == null)
                return
            if (play == mp!!.isPlaying)
                return
            when {
                !play -> {
                    mp!!.pause()
                    playerState = PlayerState.READY
                }
                mp!!.isPlaying -> return
                playerState == PlayerState.READY -> {
                    Log.d("AppLog", "ready, so starting to play")
                    mp!!.start()
                    playerState = PlayerState.PLAYING
                }
                playerState == PlayerState.NONE -> {
                    Log.d("AppLog", "not ready, so preparing")
                    mp!!.prepareAsync()
                    playerState = PlayerState.PREPARING
                }
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            Log.d("AppLog", "onVisibilityChanged:$visible $playerState")
            if (mp == null)
                return
            setPlay(visible)
        }

    }

    class MySurfaceHolder(private val surfaceHolder: SurfaceHolder) : SurfaceHolder {
        override fun addCallback(callback: SurfaceHolder.Callback) = surfaceHolder.addCallback(callback)

        override fun getSurface() = surfaceHolder.surface!!

        override fun getSurfaceFrame() = surfaceHolder.surfaceFrame

        override fun isCreating(): Boolean = surfaceHolder.isCreating

        override fun lockCanvas(): Canvas = surfaceHolder.lockCanvas()

        override fun lockCanvas(dirty: Rect): Canvas = surfaceHolder.lockCanvas(dirty)

        override fun removeCallback(callback: SurfaceHolder.Callback) = surfaceHolder.removeCallback(callback)

        override fun setFixedSize(width: Int, height: Int) = surfaceHolder.setFixedSize(width, height)

        override fun setFormat(format: Int) = surfaceHolder.setFormat(format)

        override fun setKeepScreenOn(screenOn: Boolean) {}

        override fun setSizeFromLayout() = surfaceHolder.setSizeFromLayout()

        override fun setType(type: Int) = surfaceHolder.setType(type)

        override fun unlockCanvasAndPost(canvas: Canvas) = surfaceHolder.unlockCanvasAndPost(canvas)
    }
}
