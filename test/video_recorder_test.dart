import 'package:flutter_test/flutter_test.dart';
import 'package:video_recorder/video_recorder.dart';
import 'package:video_recorder/video_recorder_platform_interface.dart';
import 'package:video_recorder/video_recorder_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockVideoRecorderPlatform
    with MockPlatformInterfaceMixin
    implements VideoRecorderPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final VideoRecorderPlatform initialPlatform = VideoRecorderPlatform.instance;

  test('$MethodChannelVideoRecorder is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelVideoRecorder>());
  });

  test('getPlatformVersion', () async {
    VideoRecorder videoRecorderPlugin = VideoRecorder();
    MockVideoRecorderPlatform fakePlatform = MockVideoRecorderPlatform();
    VideoRecorderPlatform.instance = fakePlatform;

    expect(await videoRecorderPlugin.getPlatformVersion(), '42');
  });
}
