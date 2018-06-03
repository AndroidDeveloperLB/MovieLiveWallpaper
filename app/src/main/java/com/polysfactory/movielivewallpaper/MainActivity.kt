package com.polysfactory.movielivewallpaper

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun prepareLiveWallpaperIntent(showAllLiveWallpapers: Boolean): Intent {
            val liveWallpaperIntent = Intent()
            if (showAllLiveWallpapers || Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                liveWallpaperIntent.action = WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER
            } else {
                liveWallpaperIntent.action = WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
                val p = MovieLiveWallpaperService::class.java.`package`.name
                val c = MovieLiveWallpaperService::class.java.canonicalName
                liveWallpaperIntent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, ComponentName(p, c))
            }
            return liveWallpaperIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        startActivity(prepareLiveWallpaperIntent(false))
        finish()
    }
}
