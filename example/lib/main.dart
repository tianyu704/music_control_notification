import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:music_control_notification/music_control_notification.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    super.initState();
    initMusicNotification();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  initMusicNotification() async {
    MusicControlNotification.setListener('pause', () {
      print("pause");
    });

    MusicControlNotification.setListener('play', () {
      print("play");
    });

    MusicControlNotification.setListener('next', () {
      print("next");
    });

    MusicControlNotification.setListener('prev', () {
      print("prev");
    });

    MusicControlNotification.setListener('select', () {
      print("select");
    });
    MusicControlNotification.setListener('close', () {
      print("close");
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: <Widget>[
            FlatButton(
                child: Text("显示"),
                onPressed: () {
                  MusicControlNotification.show(title: "下雨声", play: false);
                }),
            FlatButton(
                child: Text("关闭"),
                onPressed: () {
                  MusicControlNotification.hide();
                }),
          ],
        ),
      ),
    );
  }
}
