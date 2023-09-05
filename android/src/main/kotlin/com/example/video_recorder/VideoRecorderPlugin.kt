package com.example.video_recorder

import android.app.Activity
import android.media.MediaRecorder
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/** VideoRecorderPlugin */
class VideoRecorderPlugin : FlutterPlugin, MethodChannel.MethodCallHandler {
    private lateinit var activity: Activity
    private lateinit var mediaRecorder: MediaRecorder
    private var isRecording = false

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        val channel = MethodChannel(binding.binaryMessenger, "video_recorder")
        channel.setMethodCallHandler(this)
        activity = binding.applicationContext as Activity
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        when (call.method) {
            "startRecording" -> {
                startRecording()
                result.success(null)
            }
            "stopRecording" -> {
                stopRecording()
                result.success(null)
            }
            else -> result.notImplemented()
        }
    }

    private fun startRecording() {
        if (!isRecording) {
            // Implement Android screen recording logic here
            // You will need to use MediaRecorder or another suitable library
            isRecording = true
        }
    }

    private fun stopRecording() {
        if (isRecording) {
            // Stop the screen recording
            isRecording = false
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        // Clean up resources if needed
    }
}
