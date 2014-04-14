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
import android.widget.TextView;

public class Game extends Activity {

	ArrayList<Button> playerButtons, bot1Buttons, bot2Buttons, bot3Buttons;
	ArrayList<Player> players;
	Player player, bot1, bot2, bot3;
	Deck deck;
	Tile tempTile;
	Button eatButton, doubleButton, tripleButton, winButton, 
		skipButton, tempTileButton, discardButton;
	TextView gameStats;
	int currentRound, currentPlayer;
	boolean hasWon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		currentRound = 1; //Shows that the initial is the current round
		hasWon = false;

		deck = new Deck(); //Sets up the deck everyone will use

		//Randomly generates a player to start the game
		currentPlayer = randomPlayer();

		//Sets up each player and distributes a card to them
		setupPlayers();
		
		//The common 14th tile used by the current player
		tempTile = deck.draw();
		evaluateHand(tempTile);
		
		gameStats = (TextView) findViewById(R.id.gameStats);
		
		if (currentPlayer != 0) {
			deactivatePlayerButtons();
			PerformTurnThread t = new PerformTurnThread();
			t.start();
		} else {
			setTileView(tempTileButton, tempTile);
			gameStats.setText(getGameStats());
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
	}//End onBackPressed

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
		if (player.getActiveSize() == 0 &&
				bot1.getActiveSize() == 0 &&
				bot2.getActiveSize() == 0 &&
				bot3.getActiveSize() == 0) {
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

		tempTileButton = (Button) findViewById(R.id.playerTileTemp);

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
		for (int k = 0; k <= 3; k++) {
			Player tempPlayer = players.get(k);
			Tile tt;
			Button tb;
			if (k == 0) {
				for (int i = 0; i <= 12; i++) {
					tt = tempPlayer.seeTileAt(i);
					tb = playerButtons.get(i);

					setTileView(tb, tt);
				}
			}
			else {
				for (int i = 0; i <= 12; i++) {
					if (k == 1) 
						tb = bot1Buttons.get(i);
					else if (k == 2)
						tb = bot2Buttons.get(i);
					else
						tb = bot3Buttons.get(i);
					tb.setText("");
					tb.setBackgroundColor(getResources().getColor(R.color.grey));
					tb.setClickable(false);
				}
			}
			setTileView(tempTileButton, tempTile);
			discardButton = (Button) findViewById(R.id.bot2Discard_Button2);
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
	
	private void activatePlayerButtons() {
		for (int i = 0; i <= 12; i++)
			playerButtons.get(i).setClickable(true);
		tempTileButton.setClickable(true);
	}
	
	private void deactivatePlayerButtons() {
		for (int i = 0; i <= 12; i++) 
			playerButtons.get(i).setClickable(false);
		tempTileButton.setClickable(false);
	}

	private void discardTile(int i) {
		Player p = players.get(currentPlayer);
		int activeHandSize = p.getActiveSize();

		if (tempTile != null && i == 13) {
			p.discardTile(tempTile);
			tempTile = null;
		} else if (i < activeHandSize) {
			p.discardTile(i);
			p.addToHand(tempTile);
			tempTile = null;
		}
		
		setTileView(discardButton, p.lastDiscard());

		refreshHandUi();
	}

	private String evaluateHand(Tile t) {
		String handEval = players.get(currentPlayer).evaluate(t);
		if (currentPlayer == 0) {
			if (handEval.contains("w")) 
				activateButton(winButton);
			else
				deactivateButton(winButton);

			handEval = players.get(currentPlayer).evaluate(t);
			handEval = players.get(currentPlayer).evaluate(t); //This needs to be deleted
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
			//Activates the triple button if the hand has triple
			if (handEval.contains("t")) 
				activateButton(tripleButton);
			else
				deactivateButton(tripleButton);
			//Activates the skip button if any of the functions are available, else not
			if (handEval.contains("e") || handEval.contains("d") || handEval.contains("t"))
				activateButton(skipButton);
			else
				deactivateButton(skipButton);
		} else {
			deactivateButton(doubleButton);
			deactivateButton(eatButton);
			deactivateButton(skipButton);
			deactivateButton(tripleButton);
			deactivateButton(winButton);
		}

		//System.out.println("Temp Tile - Suit: " + tempTile.getSuit() + 
		//	", Value: " + tempTile.getValue());
		//System.out.println("Player " + currentPlayer + " result: " + handEval);

		return handEval;
	}

	private String getGameStats() {
		String direction = "";
		if (currentPlayer == 0)
			direction = "South";
		else if (currentPlayer == 1)
			direction = "East";
		else if (currentPlayer == 2)
			direction = "North";
		else
			direction = "West";
		return String.format("Current Player: %s        Round: %d", direction, currentRound);
	}

	private void setTileView(Button b, Tile t) {
		if (t != null) {
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
			b.setText("" + t.getValue());
			b.setOnClickListener(new TileValueListener(t.getSuit(),t.getValue()));
			b.setVisibility(View.VISIBLE);
		} else {
			b.setBackgroundColor(Color.GRAY);
			b.setText("-");
			b.setVisibility(View.INVISIBLE);
		}
	}//End setTileView

	private void refreshHandUi() {
		Player p = players.get(currentPlayer);
		ArrayList<Button> buttons;
		Button b;
		Tile t;

		if (currentPlayer == 0)
			buttons = playerButtons;
		else if (currentPlayer == 1)
			buttons = bot1Buttons;
		else if (currentPlayer == 2)
			buttons = bot2Buttons;
		else
			buttons = bot3Buttons;

		if (currentPlayer == 0) {
			for (int i = 0; i < buttons.size(); i++) {
				b = buttons.get(i);
				t = p.seeTileAt(i);
				setTileView(b, t);
			}
			if (tempTile != null) 
				setTileView(tempTileButton, tempTile);
			else
				setTileView(tempTileButton, null);

		} else {
			for (int i = 0; i < buttons.size(); i++) {
				b = buttons.get(i);
				if (i < p.getActiveSize()) {
					t = p.seeTileAt(i);
					b.setBackgroundColor(getResources().getColor(R.color.grey));
					b.setText("");
				} else if (i < p.getTotalSize()) {
					t = p.seeTileAt(i);
					setTileView(b, t);
				} else {
					t = null;
					setTileView(b, t);
				}
			}
		}
	}

	private void performTurn() {
		//Refreshes game stats so the human knows which player's turn it is
		runOnUiThread(new UpdateViewsThread(getGameStats()));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//String handEval = evaluateHand(players.get((currentPlayer + 3)%4).lastDiscard());
		String handEval = evaluateHand(tempTile);
		
		//Selects a random card to discard
		Random rand = new Random();

		tempTile = players.get(currentPlayer).drawTempTile();

		System.out.println("Player " + currentPlayer + " has taken their turn!");
		currentPlayer = (currentPlayer + 1) %4;
		//Starts performTurn with the next player again
		if (currentPlayer == 2 || currentPlayer == 3) {
			performTurn();
		} 
		//If it is bot 3, we make the buttons clickable for the human player again
		else if (currentPlayer == 0) {
			runOnUiThread(new UpdateViewsThread("eval"));
			activatePlayerButtons();
			if (players.get(currentPlayer).getTotalSize() < 12)
				tempTile = players.get(currentPlayer).drawTempTile();
			runOnUiThread(new UpdateViewsThread(getGameStats()));
			runOnUiThread(new UpdateViewsThread());
		}
	}
	
	private class TileValueListener implements OnClickListener {
		int suit, value;

		public TileValueListener(int s, int v) {
			suit = s;
			value = v;
		}

		@Override
		public void onClick(View v) {
			if (currentPlayer != 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
				builder.setTitle("Player: " + currentPlayer + ", Suit: " + suit + 
						", Value: " + value);
				builder.setPositiveButton("OK", null);

				AlertDialog ad = builder.create();
				ad.show();
			}

			//Finds the index of the button pressed so we can discard the
			//correct tile in the Hand
			if (v.equals(tempTileButton))
				discardTile(13);
			else
				discardTile(playerButtons.indexOf((Button) v));

			refreshHandUi();
			deactivateButton(doubleButton);
			deactivateButton(eatButton);
			deactivateButton(skipButton);
			deactivateButton(tripleButton);
			deactivateButton(winButton);
			deactivatePlayerButtons();

			System.out.println("Player " + currentPlayer + " has taken their turn!");
			currentPlayer = (currentPlayer + 1) %4;
			
			PerformTurnThread t = new PerformTurnThread();
			t.start();
		}

	}//EndSuitValueListener

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

	}//End FunctionOnTouch

	private class PerformTurnThread extends Thread {
		@Override
		public void run() {
			performTurn();
		}
	}//End PerformTurnThread
	
	private class UpdateViewsThread extends Thread {
		boolean eval = false, refreshUI = false, gameStat = false;
		String s = "";
		public UpdateViewsThread(String str) {
			if (str.equals("eval")) {
				eval = true; refreshUI = false; gameStat = false;
			} else {
				eval = false; refreshUI = false; gameStat = true;
				s = str;
			}
			
		}
		public UpdateViewsThread() {
			eval = false; refreshUI = true; gameStat = false;
		}
		@Override
		public void run() {
			super.run();
			if (gameStat)
				gameStats.setText(s);
			else if (eval)
				evaluateHand(tempTile);
			else
				refreshHandUi();
		}
	}//End UpdateTextViewThread
}//End Game Class