import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MusicControlNotification {
  static const MethodChannel _channel =
      const MethodChannel('music_control_notification');

  static Map<String, Function> _listeners = new Map();

  static Future<dynamic> _myUtilsHandler(MethodCall methodCall) async {
    _listeners.forEach((event, callback) {
      if (methodCall.method == event) {
        callback();
        return true;
      }
    });
  }

  static Future show({@required title, play = true}) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'title': title,
      'play': play
    };
    await _channel.invokeMethod('show', params);
    _channel.setMethodCallHandler(_myUtilsHandler);
  }

  static Future hide() async {
    await _channel.invokeMethod('hide');
  }

  static setListener(String event, Function callback) {
    _listeners.addAll({event: callback});
  }
}
