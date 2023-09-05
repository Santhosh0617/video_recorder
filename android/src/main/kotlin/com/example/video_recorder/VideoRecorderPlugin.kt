package com.example.video_recorder

import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Environment
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.io.File
import java.io.IOException
import java.util.UUID

/** VideoRecorderPlugin */
class VideoRecorderPlugin : FlutterPlugin, MethodChannel.MethodCallHandler {
    private lateinit var activity: Activity
    private lateinit var mediaRecorder: MediaRecorder
    private var isRecording = false
    private var outputFilePath: String? = null

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        val channel = MethodChannel(binding.binaryMessenger, "video_recorder")
        channel.setMethodCallHandler(this)
        activity = binding.applicationContext as Activity
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        when (call.method) {
            "startRecording" -> {
                if (startRecording()) {
                    result.success(null)
                } else {
                    result.error("RECORDING_ERROR", "Failed to start recording", null)
                }
            }
            "stopRecording" -> {
                val path = stopRecording()
                if (path != null) {
                    result.success(path)
                } else {
                    result.error("RECORDING_ERROR", "Failed to stop recording", null)
                }
            }
            else -> result.notImplemented()
        }
    }

    private fun startRecording(): Boolean {
        if (!isRecording) {
            if (checkPermissions()) {
                mediaRecorder = MediaRecorder()
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

                outputFilePath = getOutputFilePath()
                mediaRecorder.setOutputFile(outputFilePath)

                try {
                    mediaRecorder.prepare()
                    mediaRecorder.start()
                    isRecording = true
                    return true
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return false
    }

    private fun stopRecording(): String? {
        if (isRecording) {
            try {
                mediaRecorder.stop()
                mediaRecorder.reset()
                mediaRecorder.release()
                isRecording = false
                return outputFilePath
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    private fun checkPermissions(): Boolean {
        val permissions = arrayOf(
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val grantedPermissions = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, permissions, 0)
            } else {
                grantedPermissions.add(permission)
            }
        }

        return grantedPermissions.size == permissions.size
    }

    private fun getOutputFilePath(): String {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        dir.mkdirs()
        val fileName = "video_${UUID.randomUUID()}.mp4"
        return File(dir, fileName).absolutePath
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        // Clean up resources if needed
    }
}
