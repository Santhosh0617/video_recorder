import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'video_recorder_method_channel.dart';

abstract class VideoRecorderPlatform extends PlatformInterface {
  /// Constructs a VideoRecorderPlatform.
  VideoRecorderPlatform() : super(token: _token);

  static final Object _token = Object();

  static VideoRecorderPlatform _instance = MethodChannelVideoRecorder();

  /// The default instance of [VideoRecorderPlatform] to use.
  ///
  /// Defaults to [MethodChannelVideoRecorder].
  static VideoRecorderPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [VideoRecorderPlatform] when
  /// they register themselves.
  static set instance(VideoRecorderPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
