import 'package:flutter/services.dart';
import 'package:video_recorder/video_recorder_platform_interface.dart';

class VideoRecorder {
  static const MethodChannel _channel = MethodChannel('video_recorder');

  static Future<void> startRecording() async {
    await _channel.invokeMethod('startRecording');
  }

  static Future<void> stopRecording() async {
    await _channel.invokeMethod('stopRecording');
  }

  Future<String?> getPlatformVersion() {
    return VideoRecorderPlatform.instance.getPlatformVersion();
  }
}
