package com.example.mahdroid;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Game extends Activity {

	EditText suitField, valueField;
	Hand hand;
	ArrayList<Button> buttons;
	Deck deck;
	Hand playerHand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		deck = new Deck();
		playerHand = new Hand();

		buttons = new ArrayList<Button>();
		for (int i = 0x7f090001; i <= 0x7f09000e; i++) {
			buttons.add((Button)findViewById(i));
		}
		for (int i = 0; i <= 13; i++) {
			playerHand.add(deck.draw());
		}
		for (int i = 0; i <= 12; i++) {
			Tile tempT = playerHand.tileAt(i);
			Button tempB = buttons.get(i);
			if (tempT.getSuit() == 0) 
				tempB.setBackgroundColor(Color.CYAN);
			else if (tempT.getSuit() == 1)
				tempB.setBackgroundColor(Color.YELLOW);
			else if (tempT.getSuit() == 2)
				tempB.setBackgroundColor(Color.GREEN);
			else if (tempT.getSuit() == 3)
				tempB.setBackgroundColor(Color.RED);
			else if (tempT.getSuit() == 4)
				tempB.setBackgroundColor(Color.GRAY);
			tempB.setText("" + tempT.getValue());
			tempB.setOnClickListener(suit_valueListener);
			//buttons.get(i).setOnClickListener(colorListener);		
		}
		
		Button eatButton = (Button) findViewById(R.id.eatButton);
		eatButton.setOnTouchListener(functionOnTouch);
		
		Button doubleButton = (Button) findViewById(R.id.doubleButton);
		doubleButton.setOnTouchListener(functionOnTouch);
		
		Button tripleButton = (Button) findViewById(R.id.tripleButton);
		tripleButton.setOnTouchListener(functionOnTouch);
		
		Button winButton = (Button) findViewById(R.id.winButton);
		winButton.setOnTouchListener(functionOnTouch);

	}
	
	private OnClickListener suit_valueListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Button temp = (Button) v;
			int j = buttons.indexOf(temp);
			AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
	        builder.setTitle("Suit: " + playerHand.tileAt(j).getSuit() + 
	        		", Value: " + playerHand.tileAt(j).getValue());
	        builder.setPositiveButton("OK", null);

	        AlertDialog ad = builder.create();
	        ad.show();
			
		}
	};
	
	private OnTouchListener functionOnTouch = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Button temp = (Button) v;
			ColorDrawable d = (ColorDrawable) temp.getBackground();
			switch(event.getAction()){
		    case MotionEvent.ACTION_DOWN:
		        temp.setTextColor(d.getColor());
		        break;

		    case MotionEvent.ACTION_MOVE:
		        break;

		    case MotionEvent.ACTION_UP:
		        temp.setTextColor(Color.WHITE);

		        float x = v.getX(),y = v.getY();
		       // if (Math.abs(x - event.getX()) > 550 ||
		        		//Math.abs(y - event.getY()) < 100  ) {
		        String funct = temp.getText() + "";

		        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
		        builder.setTitle("Action performed: " + funct)
		        .setMessage("" + Math.abs(y - event.getY()));
		        builder.setPositiveButton("OK", null);

		        AlertDialog ad = builder.create();
		        ad.show();
		       // }
		        break;
		    }
		    return true;
		}
	};

}