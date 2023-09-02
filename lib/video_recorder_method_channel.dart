import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'video_recorder_platform_interface.dart';

/// An implementation of [VideoRecorderPlatform] that uses method channels.
class MethodChannelVideoRecorder extends VideoRecorderPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('video_recorder');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
