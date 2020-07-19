package com.mao.ijkplayerdemo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import tv.danmaku.ijk.media.example.application.Settings
import tv.danmaku.ijk.media.example.widget.media.AndroidMediaController
import tv.danmaku.ijk.media.player.IjkMediaPlayer


/**
 * 简单使用 ijkplayer  demo 提供的  IjkVideoView
 */

class MainActivity : AppCompatActivity() {


    private var setting:Settings? = null
    private var mAndroidMediaController:AndroidMediaController? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setting = Settings(this)

        mAndroidMediaController = AndroidMediaController(this,false)

        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")

        //https
        val mVideoPath1 = "https://www.apple.com/105/media/us/iphone-x/2017/01df5b43-28e4-4848-bf20-490c34a926a7/films/feature/iphone-x-feature-tpl-cc-us-20170912_1920x1080h.mp4"
        //rtmp
        val mVideoPath2 = "rtmp://58.200.131.2:1935/livetv/hunantv"
        //hls
        val mVideoPath3 = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8"
        //rtsp
        val mVideoPath4 = "rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov"

        mVideoView.setMediaController(mAndroidMediaController)
        mVideoView.setHudView(video_msg)

        editVideoPath.setText(mVideoPath3)


        btplay.setOnClickListener{

            if (TextUtils.isEmpty(editVideoPath.text)) {
                Toast.makeText(this, "视频地址不能为空", Toast.LENGTH_LONG).show();
            } else {
                mVideoView.setVideoURI(Uri.parse(editVideoPath.text.toString().trim()))
                mVideoView.start()
            }

        }


    }

    override fun onDestroy() {
        super.onDestroy()

        mVideoView.stopPlayback()
        mVideoView.release(true)
        mVideoView.stopBackgroundPlay()
        IjkMediaPlayer.native_profileEnd()
    }
}