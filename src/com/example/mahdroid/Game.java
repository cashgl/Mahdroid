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
import android.widget.RelativeLayout.LayoutParams;

public class Game extends Activity {

	ArrayList<Button> playerButtons, bot1Buttons, bot2Buttons, bot3Buttons;
	ArrayList<Player> players;
	Player player, bot1, bot2, bot3;
	Discard playerDiscard, bot1Discard, bot2Discard, bot3Discard;
	Deck deck;
	Tile tempTile, discardedTile;
	Button eatButton, doubleButton, tripleButton, winButton, skipButton, tempTileButton;
	int currentRound, currentPlayer;
	boolean playersTurn, hasWon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		currentRound = 1; //Shows that the initial is the current round

		deck = new Deck(); //Sets up the deck everyone will use

		//Randomly generates a player to start the game
		currentPlayer = randomPlayer();
		if (currentPlayer == 0) 
			playersTurn = true;
		else
			playersTurn = false;

		//Sets up each player and distributes a card to them
		setupPlayers();
		
		//The common 14th tile used by the current player
		tempTile = deck.draw();
		tempTileButton = (Button) findViewById(R.id.playerTileTemp);
		setTileView(tempTileButton, tempTile);
		
		currentPlayer = 0;
		String handEval = players.get(currentPlayer).evaluate(tempTile);
		if (handEval.contains("w")) 
			activateButton(winButton);
		else
			deactivateButton(winButton);
		
		handEval = players.get(currentPlayer).evaluate(players.get((currentPlayer + 3)%4).lastDiscard());
		//Activates the eat button if hand has eat
		if (handEval.contains("e"))
			activateButton(eatButton);	
		else
			deactivateButton(eatButton);
		//Activates the double button if the hand has double
		if (handEval.contains("d"))
			activateButton(doubleButton);
		else
			deactivateButton(doubleButton);
		//Activates the double button if the hand has triple
		if (handEval.contains("t")) 
			activateButton(tripleButton);
		else
			deactivateButton(tripleButton);
		//Activates the double button if the hand has triple
		if (handEval.contains("s"))
			activateButton(skipButton);
		else
			deactivateButton(skipButton);
		
		System.out.println("Temp Tile - Suit: " + tempTile.getSuit() + 
				", Value: " + tempTile.getValue());
		System.out.println("Player " + currentPlayer + " result: " + handEval);

		//player needs to discard a tile
		//playerDiscard.add(players.get(0).seeTileAt(i)); //add the tile to discard
		//players.get(0).discardTile(i);	//need a position index 
		
		//bot1's action(evaluation)
		currentPlayer = 1;
		String handEval1 = players.get(currentPlayer).evaluate(players.get(0).lastDiscard());
		if (handEval1.contains("e")){
	   		players.get(currentPlayer).callFunction("e");
	   		bot1Discard.add(players.get(currentPlayer).seeTileAt(0));
			discardedTile = bot1Discard.grabLast();	//for now, the bot always discards tile at index 0
			currentPlayer = 2; //update currrentPlayer to keep track of who is going to evaluate next
		}	
	    if (handEval1.contains("d")){
		   	players.get(currentPlayer).callFunction("d");
		   	bot1Discard.add(players.get(currentPlayer).seeTileAt(0));
		   	discardedTile = bot1Discard.grabLast();	//for now, the bot always discards tile at index 0
			currentPlayer = 2; //update currrentPlayer to keep track of who is going to evaluate next
	    }
		if (handEval1.contains("t")){
	    	players.get(currentPlayer).callFunction("t");	
	    	bot1Discard.add(players.get(currentPlayer).seeTileAt(0));
	    	discardedTile = bot1Discard.grabLast();	//for now, the bot always discards tile at index 0
			currentPlayer = 2; //update currrentPlayer to keep track of who is going to evaluate next
		}
		if (handEval1.contains("w")){
		    //current round ends
		}
		//bot2's action(evaluation)
		currentPlayer = 2;
		String handEval2 = players.get(currentPlayer).evaluate(players.get(0).lastDiscard());
	    if (handEval2.contains("d")){
	    	players.get(currentPlayer).callFunction("d");
	    	bot2Discard.add(players.get(currentPlayer).seeTileAt(0));
	    	discardedTile = bot2Discard.grabLast();	//for now, the bot always discards tile at index 0
			currentPlayer = 3; //update currrentPlayer to keep track of who is going to evaluate next
	    }
		if (handEval2.contains("t")){
	    	players.get(currentPlayer).callFunction("t");
	    	bot2Discard.add(players.get(currentPlayer).seeTileAt(0));
	    	discardedTile = bot2Discard.grabLast();	//for now, the bot always discards tile at index 0
			currentPlayer = 3; //update currrentPlayer to keep track of who is going to evaluate next
		}
		if (handEval2.contains("w")){
		    //current round ends
		}
		//bot3's action(evaluation)
		currentPlayer = 3;
		String handEval3 = players.get(currentPlayer).evaluate(players.get(0).lastDiscard());
		if (handEval3.contains("d")){
			players.get(currentPlayer).callFunction("d");	
			bot3Discard.add(players.get(currentPlayer).seeTileAt(0));
			discardedTile = bot3Discard.grabLast();	//for now, the bot always discards tile at index 0
			currentPlayer = 0; //update currrentPlayer to keep track of who is going to evaluate next
		}
		if (handEval3.contains("t")){
			players.get(currentPlayer).callFunction("t");
			bot3Discard.add(players.get(currentPlayer).seeTileAt(0));
			discardedTile = bot3Discard.grabLast();	//for now, the bot always discards tile at index 0
			currentPlayer = 0; //update currrentPlayer to keep track of who is going to evaluate next
		}
		if (handEval3.contains("w")){
			//current round ends
		}

		//DONT TOUCH THIS!!!
		//I'm trying to figure out the discard piles
		Button temp = (Button) findViewById(R.id.bot2Discard_Button2);

		LayoutParams botDiscard1Params = (LayoutParams) findViewById(R.id.botDiscard1).getLayoutParams(),
				botDiscard2Params = (LayoutParams) findViewById(R.id.botDiscard2).getLayoutParams(),
				playerDiscardParams = (LayoutParams) findViewById(R.id.playerDiscard).getLayoutParams();

		int discardHeight = botDiscard1Params.height;
		temp.setText("" + discardHeight);
		botDiscard2Params.height = discardHeight;
		playerDiscardParams.height = discardHeight;

		//This is going to be the basis of 
		//the rounds. Threading will be important
		//so that we don't lock up the UI
		Thread t = new MyThread();
		t.start();
	}

	private void setupPlayers() {
		//This creates the players. We will need to have them
		//solely in the ArrayList players for more efficient play.
		//It merely has a double pointer to it at the moment
		if (players != null)
			players = null;
		players = new ArrayList<Player>();
		players.add(new Player(deck));
		player = players.get(0);

		players.add(new Player(deck));
		bot1 = players.get(1);

		players.add(new Player(deck));
		bot2 = players.get(2);

		players.add(new Player(deck));
		bot3 = players.get(3);

		//Associates the buttons to a player's hand
		setupHands();
	}

	private void setupHands() {
		//This associates the value of the hands
		if (player.getHandSize() == 0 &&
				bot1.getHandSize() == 0 &&
				bot2.getHandSize() == 0 &&
				bot3.getHandSize() == 0) {
			for (int i = 0; i <= 3; i++) {
				for (int j = 0; j <= 12; j++) {
					players.get(i).drawTile();
				}
			}
		}

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

		//This outer loop controls the deal for each player
		//////////This won't stay since we won't be seeing other players' hands
		for (int k = 0; k <= 3; k++) {
			Player tempPlayer = players.get(k);
			Tile tempTile;
			Button tempButton;
			if (k == 0) {
				for (int i = 0; i <= 12; i++) {
					tempTile = tempPlayer.seeTileAt(i);
					tempButton = playerButtons.get(i);

					if (tempTile.getSuit() == 0) 
						tempButton.setBackgroundColor(Color.CYAN);
					else if (tempTile.getSuit() == 1)
						tempButton.setBackgroundColor(Color.YELLOW);
					else if (tempTile.getSuit() == 2)
						tempButton.setBackgroundColor(Color.GREEN);
					else if (tempTile.getSuit() == 3)
						tempButton.setBackgroundColor(Color.RED);
					else if (tempTile.getSuit() == 4)
						tempButton.setBackgroundColor(Color.GRAY);
					tempButton.setText("" + tempTile.getValue());
					tempButton.setOnClickListener(new SuitValueListener(k, tempTile.getSuit(),tempTile.getValue()));
					//buttons.get(i).setOnClickListener(colorListener);	
				}
			}
			else {
				for (int i = 0; i <= 12; i++) {
					if (k == 1) 
						tempButton = bot1Buttons.get(i);
					else if (k == 2)
						tempButton = bot2Buttons.get(i);
					else
						tempButton = bot3Buttons.get(i);
					tempButton.setText("");
					tempButton.setBackgroundColor(Color.rgb(168, 168, 168));
					tempButton.setClickable(false);
				}
			}
		}

		//Creates the function buttons and associates their action listener
		//that is reused for each function
		OnTouchListener functionOnTouch = new FunctionOnTouch();
		eatButton = (Button) findViewById(R.id.eatButton);
		eatButton.setOnTouchListener(functionOnTouch);

		doubleButton = (Button) findViewById(R.id.doubleButton);
		doubleButton.setOnTouchListener(functionOnTouch);

		tripleButton = (Button) findViewById(R.id.tripleButton);
		tripleButton.setOnTouchListener(functionOnTouch);

		winButton = (Button) findViewById(R.id.winButton);
		winButton.setOnTouchListener(functionOnTouch);

		skipButton = (Button) findViewById(R.id.skipButton);
		skipButton.setOnTouchListener(functionOnTouch);
	}

	private int randomPlayer() {
		Random r = new Random();
		return r.nextInt(4);
	}
	
	private void activateButton(Button b) {
		b.setEnabled(true);
		b.setTextColor(Color.WHITE);
	}
	
	private void deactivateButton(Button b) {
		b.setEnabled(false);
		ColorDrawable d = (ColorDrawable) b.getBackground();
		b.setTextColor(d.getColor());
	}
	
	private void setTileView(Button b, Tile t) {
		if (t.getSuit() == 0) 
			b.setBackgroundColor(Color.CYAN);
		else if (t.getSuit() == 1)
			b.setBackgroundColor(Color.YELLOW);
		else if (t.getSuit() == 2)
			b.setBackgroundColor(Color.GREEN);
		else if (t.getSuit() == 3)
			b.setBackgroundColor(Color.RED);
		else if (t.getSuit() == 4)
			b.setBackgroundColor(Color.GRAY);
		b.setText("" + tempTile.getValue());
		b.setOnClickListener(new SuitValueListener(0, tempTile.getSuit(),tempTile.getValue()));
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

	private class SuitValueListener implements OnClickListener {
		int suit, value, player;

		public SuitValueListener(int p, int s, int v) {
			player = p;
			suit = s;
			value = v;
		}

		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
			builder.setTitle("Player: " + player + ", Suit: " + suit + 
					", Value: " + value);
			builder.setPositiveButton("OK", null);

			AlertDialog ad = builder.create();
			ad.show();

		}

	}

	private class FunctionOnTouch implements OnTouchListener {
		int playerTurn;
		
		public FunctionOnTouch() {
			playerTurn = currentPlayer;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Button temp = (Button) v;
			ColorDrawable d = (ColorDrawable) temp.getBackground();
			
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				temp.setTextColor(d.getColor());
				break;
	
			case MotionEvent.ACTION_UP:
				String funct = temp.getText().charAt(0) + "";
					
				temp.setTextColor(Color.WHITE);
				float x = Math.abs(event.getX()),y = Math.abs(event.getY());
				double dist = Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
				if (dist < 300) {
					//This is the code that will actually execute the function.
					//However, it is only used in the case of the human player
					//since we will be automatically be doing this for the bots
					String functText = temp.getText() + "";
					if (functText.equalsIgnoreCase("double")) {
						//call double on current player
					} else if (functText.equalsIgnoreCase("triple")) {
						//call triple on current player
					} else if (functText.equalsIgnoreCase("eat")) {
						//call eat on current player
					}
	
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
	
	}

	private class MyThread extends Thread {
		boolean b, firstRun = false;
		public MyThread() { firstRun = true; }
		public MyThread(boolean bool) { b = bool; }
	
		@Override
		public void run() {
			if (firstRun) {
				hasWon = false;
				try {
					boolean b = true;
					while (!hasWon) {
						runOnUiThread(new MyThread(b));
						if (b == true)
							b = false;
						else
							b = true;
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				if (b) {
					bot1Buttons.get(3).setBackgroundColor(Color.BLUE);
				} else {
					bot1Buttons.get(3).setBackgroundColor(Color.RED);
				}
			}
		}
	}

}