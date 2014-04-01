package com.example.mahdroid;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

public class Game extends Activity {

	EditText suitField, valueField;
	Hand hand;
	ArrayList<Button> buttons;
	Deck deck;
	Hand playerHand;
	int currentRound, currentPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		deck = new Deck();
		playerHand = new Hand();
		
		currentPlayer = randomPlayer();

		//Fix this method so that it distributes 
		//cards one by one to each player
		createHands();

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

		    	float x = Math.abs(event.getX()),y = Math.abs(event.getY());
		    	double dist = Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
		    	if (dist < 300) {
		    		String funct = temp.getText() + "";

		    		AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
		    		builder.setTitle("Action performed: " + funct)
		    			.setMessage(dist + "");
		    		builder.setPositiveButton("OK", null);

		    		AlertDialog ad = builder.create();
		    		ad.show();
		    	}
		    	break;
			}
			return true;
		}
	};
	
	private void createHands() {
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
	
	private int randomPlayer() {
		Random r = new Random();
		return r.nextInt(4);
	}

}