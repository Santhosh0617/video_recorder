#include "include/video_recorder/video_recorder_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "video_recorder_plugin.h"

void VideoRecorderPluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  video_recorder::VideoRecorderPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
