package com.example.mahdroid;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity {

	ArrayList<Button> playerButtons, bot1Buttons, bot2Buttons, bot3Buttons;
	ArrayList<Player> players;
	Deck deck;
	Tile tempTile;
	Button eatButton, doubleButton, tripleButton, winButton, 
	skipButton, tempTileButton, discardButton;
	TextView gameStats;
	int currentRound, currentPlayer;
	boolean roundEnded, hasWon;

	Button newRoundButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		currentRound = 1; //Shows that the initial is the current round
		roundEnded = false;
		hasWon = false;

		deck = new Deck(); //Sets up the deck everyone will use

		//Randomly generates a player to start the game
		currentPlayer = randomPlayer();

		//Sets up each player and distributes a card to them
		setupPlayers();

		//The common 14th tile used by the current player
		drawTile();
		evaluateWin(tempTile, currentPlayer);

		deactivateButton(doubleButton);
		deactivateButton(eatButton);
		deactivateButton(skipButton);
		deactivateButton(tripleButton);

		if (currentPlayer != 0) {
			deactivatePlayerButtons();
			PerformTurnThread t = new PerformTurnThread();
			t.start();
		} else {
			setTileView(tempTileButton, tempTile);
			updateGameStats();
		}

		newRoundButton = (Button) findViewById(R.id.newRnd);
		newRoundButton.setVisibility(View.INVISIBLE);
		newRoundButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentPlayer == 0)
					newRound();

			}
		});
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

		players.add(new Player(deck));

		players.add(new Player(deck));

		players.add(new Player(deck));

		for (int i = 0; i <= 3; i++) {
			players.get(i).addObserver(new DiscardObserver());
		}

		//Associates the buttons to a player's hand
		setupHands();
	}

	private void setupHands() {
		//This associates the value of the hands
		if (players.get(0).getActiveSize() == 0 &&
				players.get(1).getActiveSize() == 0 &&
				players.get(2).getActiveSize() == 0 &&
				players.get(3).getActiveSize() == 0) {
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
					tb.setEnabled(false);
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

		gameStats = (TextView) findViewById(R.id.gameStats);
	}

	private void newRound() {
		newRoundButton.setVisibility(View.INVISIBLE);
		currentRound++;
		resetGame();

		roundEnded = false;
		deck = new Deck();

		setupPlayers();

		currentPlayer = randomPlayer();

		//The common 14th tile used by the current player
		drawTile();
		evaluateWin(tempTile, currentPlayer);

		deactivateButton(doubleButton);
		deactivateButton(eatButton);
		deactivateButton(skipButton);
		deactivateButton(tripleButton);

		if (currentPlayer != 0) {
			deactivatePlayerButtons();
			PerformTurnThread t = new PerformTurnThread();
			t.start();
		} else {
			setTileView(tempTileButton, tempTile);
			updateGameStats();
		}

		if (currentPlayer == 0)
			activatePlayerButtons();
		updateGameStats();
	}

	private void resetGame() {
		playerButtons = null; bot1Buttons = null; 
		bot2Buttons = null; bot3Buttons = null;

		for (int i = 0; i < 4; i++) {
			players.set(i, null);
		}

		deck = null;

		players = null;
		tempTile = null;

		setTileView(discardButton, null);

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
		runOnUiThread(new Thread() {
			@Override
			public void run() {
				super.run();
				Player p = players.get(0);
				for (int i = 0; i < p.getActiveSize(); i++)
					playerButtons.get(i).setEnabled(true);
				for (int i = p.getActiveSize(); i < playerButtons.size(); i++)
					playerButtons.get(i).setEnabled(false);
				tempTileButton.setEnabled(true);
			}
		});
	}

	private void deactivatePlayerButtons() {
		runOnUiThread(new Thread() {
			@Override
			public void run() {
				super.run();
				for (int i = 0; i < playerButtons.size(); i++) 
					playerButtons.get(i).setEnabled(false);
				tempTileButton.setEnabled(false);
			}
		});

	}

	private void discardTile(int i) {
		Player p = players.get(currentPlayer);
		int activeHandSize = p.getActiveSize();

		if (tempTile != null && i == 13) {
			p.discardTile(tempTile);
			tempTile = null;
		} else if (tempTile == null && i < activeHandSize) {
			p.discardTile(i);
		} else if (i < activeHandSize) {
			p.discardTile(i);
			p.addToHand(tempTile);
			tempTile = null;
		}
	}

	private void drawTile() {
		tempTile = deck.draw();

		if (deck.getSize() <= 0) {
			roundEnded = true;
		}
	}

	private String evaluateWin(Tile t, int currPlayer) {
		String handEval = players.get(currPlayer).evaluate(t);

		if (currPlayer == 0) {
			if (handEval.contains("w")) 
				activateButton(winButton);
			else
				deactivateButton(winButton);
		}
		else
			deactivateButton(winButton);

		return handEval;
	}

	private String evaluateNotWin(Tile t, int currPlayer) {
		String handEval = players.get(currPlayer).evaluate(t);
		if (currPlayer == 0) {
			handEval = players.get(currPlayer).evaluate(t);
			handEval = players.get(currPlayer).evaluate(t); //This needs to be deleted
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
		}
		return handEval;
	}
	private void updateGameStats() {
		String direction = "";
		if (currentPlayer == 0)
			direction = "South";
		else if (currentPlayer == 1)
			direction = "East";
		else if (currentPlayer == 2)
			direction = "North";
		else
			direction = "West";
		final String dir = direction;
		final String s = String.format("Current Player: %-13s " +
				"Tiles Remaining: %-8d        " +
				"Round: %d", direction, deck.getSize(), currentRound);

		runOnUiThread(new Thread() {
			@Override
			public void run() {
				super.run();
				if (hasWon)
					gameStats.setText(dir + " has won!");
				else if (roundEnded && deck.getSize() <= 0)
					gameStats.setText("No more tiles in the deck!");
				else
					gameStats.setText(s);
			}
		});

	}

	private void setFunctionedTileView(Button b, Tile t) {
		if (t != null) {
			if (t.getSuit() == 0) {
				//b.setBackgroundColor(Color.rgb(184,247,247)); //Faded cyan
				b.setTextColor(Color.CYAN);
				b.setBackgroundColor(Color.DKGRAY);
			}
			else if (t.getSuit() == 1) {
				//b.setBackgroundColor(Color.rgb(241,245,148)); //Faded yellow
				b.setTextColor(Color.YELLOW);
				b.setBackgroundColor(Color.DKGRAY);
			}
			else if (t.getSuit() == 2) {
				//b.setBackgroundColor(Color.rgb(161,247,156)); //Faded green
				b.setTextColor(Color.GREEN);
				b.setBackgroundColor(Color.DKGRAY);
			}
			else if (t.getSuit() == 3) {
				//b.setBackgroundColor(Color.rgb(244,142,148)); //Faded red
				b.setTextColor(Color.RED);
				b.setBackgroundColor(Color.DKGRAY);
			}
			else if (t.getSuit() == 4)
				b.setBackgroundColor(Color.GRAY);
			b.setText("" + t.getValue());
			b.setVisibility(View.VISIBLE);

			if (playerButtons.contains(b) || tempTileButton.equals(b))
				b.setOnClickListener(new TileValueListener());
		} else {
			b.setBackgroundColor(Color.GRAY);
			b.setText("-");
			b.setVisibility(View.INVISIBLE);
		}
	}

	private void setTileView(Button b, Tile t) {
		b.setTextColor(Color.BLACK);
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
			b.setVisibility(View.VISIBLE);
			if (playerButtons.contains(b) || tempTileButton.equals(b))
				b.setOnClickListener(new TileValueListener());
		} else {
			b.setBackgroundColor(Color.GRAY);
			b.setText("-");
			b.setVisibility(View.INVISIBLE);
		}
	}//End setTileView

	private void refreshHandUi(int c) {
		final int currPlayer = c;
		runOnUiThread(new Thread() {
			@Override
			public void run() {
				super.run();
				Player p = players.get(currPlayer);
				ArrayList<Button> buttons;
				Button b;
				Tile t;

				if (currPlayer == 0)
					buttons = playerButtons;
				else if (currPlayer == 1)
					buttons = bot1Buttons;
				else if (currPlayer == 2)
					buttons = bot2Buttons;
				else
					buttons = bot3Buttons;

				if (currPlayer == 0) {
					for (int i = 0; i < p.getActiveSize(); i++) {
						b = buttons.get(i);
						t = p.seeTileAt(i);
						setTileView(b, t);
					}
					for (int i = p.getActiveSize(); i < buttons.size(); i++) {
						b = buttons.get(i);
						t = p.seeTileAt(i);
						setFunctionedTileView(b, t);
					}
					if (tempTile != null) 
						setTileView(tempTileButton, tempTile);
					else if (p.getTotalSize() == 14)
						setFunctionedTileView(tempTileButton, p.seeTileAt(13));
					else if (p.seeTileAt(13) != null)
						setTileView(tempTileButton, p.seeTileAt(13));
					else
						setTileView(tempTileButton, null);

				} else {
					for (int i = 0; i < p.getActiveSize(); i++) {
						b = buttons.get(i);
						b.setBackgroundColor(getResources().getColor(R.color.grey));
						b.setText("");
					}
					for (int i = p.getActiveSize(); i < buttons.size(); i++) {
						b = buttons.get(i);
						t = p.seeTileAt(i);
						setFunctionedTileView(b, t);
					}
					/*for (int i = 0; i < buttons.size(); i++) {
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
					}*/
				}
			}
		});

	}

	private void performTurn() {
		//
		if (!roundEnded && !hasWon) {
			try {
				//Refreshes game stats so the human knows which player's turn it is
				updateGameStats();
				Thread.sleep(1975);
				Player p = players.get(currentPlayer);
				Tile lastDiscard = players.get((currentPlayer + 3)%4).lastDiscard();

				String handEval = evaluateNotWin(lastDiscard, 
						currentPlayer);

				Random rand = new Random();
				int r;
				//If skip isn't the only function available:
				if (handEval.length() > 1) {
					r = rand.nextInt(handEval.length());
					//Picks a random function to execute
					if (handEval.charAt(r) == 's') {
						drawTile();
					} else {
						p.callFunction(handEval.charAt(r)+"", lastDiscard);
					}
				}
				//If we don't execute a function, just draw a tile
				else {
					drawTile();
				}

				//
				if (!roundEnded && !hasWon) {
					//Selects a random card to discard
					r = rand.nextInt(p.getActiveSize() + 1);
					if (r == p.getActiveSize())
						r = 13;

					//Discards a tile regardless of if we did a function or not
					discardTile(r);

					Thread.sleep(25);
					refreshHandUi(currentPlayer);
					
					handEval = evaluateWin(tempTile,currentPlayer);
					if (handEval.contains("w"))
						hasWon = true;
					
					handEval = evaluateWin(lastDiscard, currentPlayer);
					if (handEval.contains("w"))
						hasWon = true;
					
					updateGameStats();

					Thread.sleep(250);
					currentPlayer = (currentPlayer + 1) %4;
					//Starts performTurn with the next player again
					if ((currentPlayer == 2 || currentPlayer == 3) && !hasWon) {
						performTurn();
					} 
					//If it is bot 3, we make the buttons clickable for the human player again
					else if (currentPlayer == 0 && !hasWon) {
						final Tile lastTile = players.get(3).lastDiscard();
						runOnUiThread(new Thread() {
							@Override
							public void run() {
								super.run();
								evaluateWin(lastTile, 0); 
								evaluateNotWin(lastTile, 0);
							}
						});

						//Sleeping the current thread for just a moment just in case
						Thread.sleep(50);

						//As long as the function tiles aren't enabled and temp tile isn't null
						if (tempTile == null  && !eatButton.isEnabled() && !doubleButton.isEnabled() &&
								!tripleButton.isEnabled()) {
							//Draw from the deck
							drawTile();
							//Let the player's tiles be active
							activatePlayerButtons();
							//Now, evaluate for win
							runOnUiThread(new Thread() { @Override public void run() { super.run();
							if (tempTile != null)
								evaluateWin(tempTile, 0); }});

						} else {
							deactivatePlayerButtons();
						}
						updateGameStats();
						refreshHandUi(currentPlayer);
					}
				}
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} //End if(!roundEnded)

		if (roundEnded) {
			runOnUiThread(new Thread() {
				@Override
				public void run() {
					super.run();
					deactivatePlayerButtons();
					newRoundButton.setVisibility(View.VISIBLE);
					currentPlayer = 0;
					updateGameStats();
				}
			});
			
			return;

		} //End if(roundEnded)
		
		if (hasWon) {
			runOnUiThread(new Thread() {
				@Override
				public void run() {
					super.run();
					deactivatePlayerButtons();
					newRoundButton.setVisibility(View.VISIBLE);
					currentPlayer = 0;
					updateGameStats();
				}
			});
		} //End if(hasWon)

	}

	private class TileValueListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			try {
				v.setEnabled(false);
				deactivatePlayerButtons();
				deactivateButton(doubleButton);
				deactivateButton(eatButton);
				deactivateButton(skipButton);
				deactivateButton(tripleButton);
				deactivateButton(winButton);

				//Finds the index of the button pressed so we can discard the
				//correct tile in the Hand
				if (v.equals(tempTileButton))
					discardTile(13);
				else
					discardTile(playerButtons.indexOf((Button) v));

				refreshHandUi(currentPlayer);

				Thread.sleep(20);

				currentPlayer = (currentPlayer + 1) %4;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			PerformTurnThread t = new PerformTurnThread();
			t.start();
		}

	}//EndSuitValueListener

	private class FunctionOnTouch implements OnTouchListener {

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
					Player p = players.get(currentPlayer), 
							prev = players.get((currentPlayer + 3) % 4);

					//This is the code that will actually execute the function.
					//However, it is only used in the case of the human player
					//since we will be automatically be doing this for the bots
					String functText = temp.getText() + "";
					if (functText.equalsIgnoreCase("win!")) {
						roundEnded = true;
						
						gameStats.setText("Congratulations! You won!");
						newRoundButton.setVisibility(View.VISIBLE);
						
					} else {
						if (functText.equalsIgnoreCase("double")) {
							//call double on current player
							p.callFunction(funct, prev.useLastDiscard());
						} else if (functText.equalsIgnoreCase("triple")) {
							//call triple on current player
							p.callFunction(funct, prev.useLastDiscard());
						} else if (functText.equalsIgnoreCase("eat")) {
							//call eat on current player
							p.callFunction(funct, prev.useLastDiscard());
						} 

						activatePlayerButtons();

						//Disables the TempTileButton so that when you discard,
						//you don't accidentally discard a functioned tile
						tempTileButton.setEnabled(false);
						if (functText.equals("triple")) {
							drawTile();
							tempTileButton.setEnabled(true);
							refreshHandUi(currentPlayer);
						}
						else if (functText.equalsIgnoreCase("skip")) {
							drawTile();
							tempTileButton.setEnabled(true);
							refreshHandUi(currentPlayer);
						}

						if (!functText.equals("triple") || !functText.equals("skip"))
							refreshHandUi(currentPlayer);
						
						if (evaluateWin(tempTile, currentPlayer).contains("w"))
							activateButton(winButton);
					}

					deactivateButton(doubleButton);
					deactivateButton(eatButton);
					deactivateButton(skipButton);
					deactivateButton(tripleButton);
					deactivateButton(winButton);
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

	private class DiscardObserver implements Observer {

		@Override
		public void update(Observable arg0, Object arg1) {
			runOnUiThread(new Thread() {
				@Override
				public void run() {
					super.run();
					Player p = players.get(currentPlayer);
					Tile t = p.lastDiscard();

					setTileView(discardButton, t);
				}
			});
		}
	}
}//End Game Class