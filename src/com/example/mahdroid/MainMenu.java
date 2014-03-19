package com.example.mahdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		Button goToGame = (Button) findViewById(R.id.startGame);
		goToGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this, Game.class);
				startActivity(intent);
				
			}
		});
		
		Button goToInstructions = (Button) findViewById(R.id.goToInstructions);
		goToInstructions.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this, Instructions.class);
				startActivity(intent);
				
			}
		});
	}
}
