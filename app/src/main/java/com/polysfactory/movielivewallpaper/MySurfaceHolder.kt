package com.polysfactory.movielivewallpaper

import android.graphics.Canvas
import android.graphics.Rect
import android.view.SurfaceHolder

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
