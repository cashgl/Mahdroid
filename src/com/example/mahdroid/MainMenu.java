package com.example.mahdroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		TextView gameTitle = (TextView) findViewById(R.id.gameTitle);
		gameTitle.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				TextView temp = (TextView) v;

				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					temp.setText("麻将");
					break;

				case MotionEvent.ACTION_UP:
					temp.setText("Mahdroid");
					break;
				}
				return true;
			}
		});

		Button goToGame = (Button) findViewById(R.id.startGame);
		goToGame.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				TextView temp = (TextView) v;

				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					temp.setText("开始");
					break;

				case MotionEvent.ACTION_UP:
					temp.setText("Start Game");
					Intent intent = new Intent(MainMenu.this, Game.class);
					startActivity(intent);
					break;
				}
				return true;
			}
		});
	
		Button goToInstructions = (Button) findViewById(R.id.goToInstructions);
		goToInstructions.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				TextView temp = (TextView) v;

				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					temp.setText("说明");
					break;

				case MotionEvent.ACTION_UP:
					temp.setText("Instructions");
					Intent intent = new Intent(MainMenu.this, Instructions.class);
					startActivity(intent);
					break;
				}
				return true;
			}
		});
	}
}
