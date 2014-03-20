package com.example.mahdroid;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Instructions extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions);
		
		WebView instruct = (WebView) findViewById(R.id.webView1);
		instruct.getSettings().setJavaScriptEnabled(true);
		instruct.loadUrl("http://mahjongtime.com/hong-kong-mahjong-rules.html");
	}

}
