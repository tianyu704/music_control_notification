# music_control_notification

Android端通知栏音乐控制

## Getting Started

```
music_control_notification: ^0.0.1
```
or
```
music_control_notification:
    git:
      url: "https://github.com/tianyu704/music_control_notification"
```


```
  @override
  void initState() {
    super.initState();
    initMusicNotification();
  }

  // 设置监听
  initMusicNotification() {
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
  /// 隐藏通知
  Future<void> _hide() async {
    try {
      await MusicControlNotification.hide();
    } on PlatformException {}
  }
  /// 显示通知
  Future<void> _show(title, bool play) async {
    try {
      await MusicControlNotification.show(title: title, play: play);
    } on PlatformException {}
  }
```
