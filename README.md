# WebViewTest
自己的WebView示例

webView.clearCache(true);
如果某个页面进过一次，再次进入的话资源文件会加载地很快。但是调用了clearCache(true)后，进入后又要重新加载资源文件了
视频:one.mp4

webView.clearHistory();
未调用该方法前，是可以使用goBack()等前进后退的，调用后无法前进后退了，canGoBack的值也从true变成了false。
就像是被切断了返回路径
