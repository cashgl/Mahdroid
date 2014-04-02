package com.example.mahdroid;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	ArrayList<Button> playerButtons, bot1Buttons, bot2Buttons, bot3Buttons;
	Deck deck;
	Hand playerHand, bot1Hand, bot2Hand, bot3Hand;
	int currentRound, currentPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		deck = new Deck();

		//Randomly generates a player to start the game
		currentPlayer = randomPlayer();

		//Fix this method so that it distributes 
		//cards one by one to each player
		createHands();

	}
	
	@Override
	public void onBackPressed() {
		//This method confirms if the user wants to exit the activity
	    new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Quitting Game")
	        .setMessage("Are you sure you want to quit the game?\nThis will erase all progress")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();    
	        }
	    })
	    .setNegativeButton("No", null)
	    .setCancelable(false)
	    .show();
	}

	private OnClickListener suit_valueListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Button temp = (Button) v;
			int j = playerButtons.indexOf(temp);
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
		//Associates the buttons to a player's hand
		associateHands();

		//Instantiates the players' hands
		playerHand = new Hand();
		bot1Hand = new Hand();
		bot2Hand = new Hand();
		bot3Hand = new Hand();
		
		//Deals the tiles to each player
		for (int i = 0; i <= 12; i++) {
			playerHand.add(deck.draw());
			bot1Hand.add(deck.draw());
			bot2Hand.add(deck.draw());
			bot3Hand.add(deck.draw());
		}

		//This outer loop controls the deal for each player
//////////This won't stay since we won't be seeing other players' hands
		for (int k = 0; k <= 3; k++) {
			Tile tempT;
			Button tempB;
			for (int i = 0; i <= 12; i++) {
				if (k == 0) {
					tempT = playerHand.tileAt(i);
					tempB = playerButtons.get(i);
				} else if (k == 1) {
					tempT = bot1Hand.tileAt(i);
					tempB = bot1Buttons.get(i);
				} else if (k == 2) {
					tempT = bot2Hand.tileAt(i);
					tempB = bot2Buttons.get(i);
				} else {
					tempT = bot3Hand.tileAt(i);
					tempB = bot3Buttons.get(i);
				}
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
		}

		//Creates the function buttons and associates their action listener
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

	private void associateHands() {
		playerButtons = new ArrayList<Button>();
		playerButtons.add((Button)findViewById(R.id.playerTile0));
		playerButtons.add((Button)findViewById(R.id.playerTile1));
		playerButtons.add((Button)findViewById(R.id.playerTile2));
		playerButtons.add((Button)findViewById(R.id.playerTile3));
		playerButtons.add((Button)findViewById(R.id.playerTile4));
		playerButtons.add((Button)findViewById(R.id.playerTile5));
		playerButtons.add((Button)findViewById(R.id.playerTile6));
		playerButtons.add((Button)findViewById(R.id.playerTile7));
		playerButtons.add((Button)findViewById(R.id.playerTile8));
		playerButtons.add((Button)findViewById(R.id.playerTile9));
		playerButtons.add((Button)findViewById(R.id.playerTile10));
		playerButtons.add((Button)findViewById(R.id.playerTile11));
		playerButtons.add((Button)findViewById(R.id.playerTile12));

		bot1Buttons = new ArrayList<Button>();
		bot1Buttons.add((Button)findViewById(R.id.botTile1_0));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_1));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_2));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_3));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_4));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_5));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_6));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_7));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_8));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_9));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_10));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_11));
		bot1Buttons.add((Button)findViewById(R.id.botTile1_12));

		bot2Buttons = new ArrayList<Button>();
		bot2Buttons.add((Button)findViewById(R.id.botTile2_0));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_1));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_2));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_3));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_4));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_5));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_6));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_7));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_8));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_9));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_10));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_11));
		bot2Buttons.add((Button)findViewById(R.id.botTile2_12));

		bot3Buttons = new ArrayList<Button>();
		bot3Buttons.add((Button)findViewById(R.id.botTile3_0));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_1));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_2));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_3));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_4));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_5));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_6));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_7));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_8));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_9));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_10));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_11));
		bot3Buttons.add((Button)findViewById(R.id.botTile3_12));
	}

}