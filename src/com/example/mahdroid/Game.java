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
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Game extends Activity {

	EditText suitField, valueField;
	Hand hand;
	ArrayList<Button> playerButtons, bot3Tiles;
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
		playerButtons = new ArrayList<Button>();
		bot3Tiles = new ArrayList<Button>();

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

		for (int i = 0; i <= 13; i++) {
			playerHand.add(deck.draw());
		}
		for (int i = 0; i <= 12; i++) {
			Tile tempT = playerHand.tileAt(i);
			Button tempB = playerButtons.get(i);
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

		bot3Tiles.add((Button)findViewById(R.id.botTile3_0));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_1));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_2));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_3));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_4));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_5));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_6));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_7));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_8));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_9));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_10));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_11));
		bot3Tiles.add((Button)findViewById(R.id.botTile3_12));
		
		for (int i = 0; i <= 12; i++) {
			Button temp = bot3Tiles.get(i);
			if (i % 2 == 0) {
				temp.setBackgroundColor(Color.MAGENTA);
				temp.setText("2");
			}
			else {
				temp.setBackgroundColor(Color.BLUE);
				temp.setText("1");
			}
			bot3Tiles.add(temp);

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