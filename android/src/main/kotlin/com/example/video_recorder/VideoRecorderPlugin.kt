package com.example.video_recorder

import android.app.Activity
import android.media.MediaRecorder
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.io.IOException

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
                startRecording()
                result.success(null)
            }
            "stopRecording" -> {
                stopRecording()
                result.success(outputFilePath)
            }
            else -> result.notImplemented()
        }
    }

    private fun startRecording() {
        if (!isRecording) {
            mediaRecorder = MediaRecorder()
            
            // Set the source and output format
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            
            // Set the video and audio encoding parameters
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            
            // Set the output file path
            outputFilePath = getOutputFilePath() // Define this method to get a unique file path
            mediaRecorder.setOutputFile(outputFilePath)
            
            try {
                mediaRecorder.prepare()
                mediaRecorder.start()
                isRecording = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun stopRecording() {
        if (isRecording) {
            try {
                mediaRecorder.stop()
                mediaRecorder.release()
                isRecording = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    private fun getOutputFilePath(): String {
        // Define a method to generate a unique output file path
        // You can use Environment.getExternalStorageDirectory() to get the external storage directory
        // and append a unique filename to it.
        // For example:
        // val dir = Environment.getExternalStorageDirectory()
        // val fileName = "video_${System.currentTimeMillis()}.mp4"
        // return File(dir, fileName).absolutePath
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        // Clean up resources if needed
    }
}
