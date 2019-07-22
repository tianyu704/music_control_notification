import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:music_control_notification/music_control_notification.dart';

void main() {
  const MethodChannel channel = MethodChannel('music_control_notification');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

//  test('getPlatformVersion', () async {
//    expect(await MusicControlNotification.platformVersion, '42');
//  });
}
