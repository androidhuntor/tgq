package com.example.webview_test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.Button;
import android.widget.FrameLayout;

public class EswVideoActivity extends Activity implements OnClickListener {

	private WebView webView;
	private Button back_btn;
	private FrameLayout video;
	private CustomViewCallback customViewCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_esw_video);
		intiview();
		LoadUrl();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		webView.onResume();
	}

	// 初始化
	private void intiview() {
		// TODO Auto-generated method stub
		webView = (WebView) findViewById(R.id.webview);
		back_btn = (Button) findViewById(R.id.back_btn);
		// 声明video，把之后的视频放到这里面去
		video = (FrameLayout) findViewById(R.id.video);
		webView.setWebViewClient(new MyWebViewClient());
		back_btn.setOnClickListener(this);
	}

	// 加载web
	@SuppressLint({ "SetJavaScriptEnabled", "InlinedApi", "NewApi" })
	private void LoadUrl() {
		// TODO Auto-generated method stubs
		// 设置WebView属性，能够执行Javascript脚本
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setWebChromeClient(new DefaultWebChromeClient()); // 播放视频
		webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.loadUrl("http://www.iqiyi.com/a_19rrhaymat.html");
	}

	private class DefaultWebChromeClient extends WebChromeClient {
		// 一个回调接口使用的主机应用程序通知当前页面的自定义视图已被撤职

		// 进入全屏的时候
		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			// 赋值给callback
			customViewCallback = callback;
			// 设置webView隐藏
			webView.setVisibility(View.GONE);
			back_btn.setVisibility(View.VISIBLE);
			// 将video放到当前视图中
			video.addView(view);
			// 横屏显示
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			// 设置全屏
			setFullScreen();
		}

		// 退出全屏的时候
		@Override
		public void onHideCustomView() {
			if (customViewCallback != null) {
				// 隐藏掉
				customViewCallback.onCustomViewHidden();
			}
			// 用户当前的首选方向
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// 退出全屏
			quitFullScreen();
			// 设置WebView可见
			webView.setVisibility(View.VISIBLE);
			back_btn.setVisibility(View.GONE);
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
		}
	}

	/**
	 * 设置全屏
	 */
	private void setFullScreen() {
		// 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 全屏下的状态码：1098974464
		// 窗口下的状态吗：1098973440
	}

	/**
	 * 退出全屏
	 */
	private void quitFullScreen() {
		// 声明当前屏幕状态的参数并获取
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow()
				.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	// 关联webview 类
	class MyWebViewClient extends WebViewClient {

		// 加载结束的时候
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
		}

	}

	// 手机返回键监听
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// 如果是全屏状态 按返回键则变成非全屏状态，否则执行返回操作
			if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				quitFullScreen();
			} else {
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					finish();
				}
			}

			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		webView.onPause();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.back_btn:
			if (customViewCallback != null) {
				// 隐藏掉
				customViewCallback.onCustomViewHidden();
			}
			// 用户当前的首选方向
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// 退出全屏
			quitFullScreen();
			// 设置WebView可见
			webView.setVisibility(View.VISIBLE);
//			expend_headerV.setVisibility(View.VISIBLE);
//			back_btn_video.setVisibility(View.GONE);
			video.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

}
