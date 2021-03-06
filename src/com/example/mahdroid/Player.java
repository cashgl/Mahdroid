package com.example.mahdroid;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * @author Grayson
 *
 */
public class Player extends Observable{
	private Deck deck;
	private Hand hand;
	private Discard discard;
	private int numEat, numDouble, numTriple,
	randomEat, randomDouble, randomTriple;
	
	public Player(Deck d) {
		deck = d;
		hand = new Hand();
		discard = new Discard();
		numEat = 0;
		numDouble = 0;
		numTriple = 0;
	}
	
	public Tile drawTile(){
		Tile t = deck.draw();
		hand.add(t);
		return t;
	}
	
	public void addToHand(Tile t) {
		hand.add(t);
	}
	
	public Tile drawTempTile() {
		return deck.draw();
	}
	
	public Tile lastDiscard(){
		return discard.grabLast();
	}
	
	public Tile useLastDiscard() {
		return discard.useLast();
	}
	
	/**
	 * Takes the index of the hand and discards that tile
	 * @param i
	 */
	public boolean discardTile(int i) {
		Tile t;
		if (i >= 0 && i < 13) {
			discard.add(hand.removeAt(i));
			//hand.add(tempTile);
			//tempTile = null;
			triggerObservers();
			return true;
		}
		return false;
		/*else {
			discard.add(tempTile);
			tempTile = null;
			
		}*/
		
		
	}
	
	public boolean discardTile(Tile t) {
		if (t != null) {
			discard.add(t);
			triggerObservers();
			return true;
		}
		return false;
	}
	
	/**
	 * @param t
	 * @return
	 */
	public String evaluate(Tile t){
		StringBuilder s = new StringBuilder();
		if (t == null) {}
		else {
			numEat = Function.eat(hand, t);
			numDouble = Function.dou(hand, t);
			numTriple = Function.triple(hand, t);
			if (numEat > 0)
				s.append("e");
			if (numDouble > 0)
				s.append("d");
			if (numTriple > 0)
				s.append("t");
			if (Function.skip(hand))
				s.append("s");
			if (Function.win(hand, t))
				s.append("w");
		}
		
		return s.toString();
	}
	
	public int getActiveSize() {
		if (hand != null) {
			return hand.getActiveSize();
		}
		return 0;
	}
	
	public int getTotalSize() {
		if (hand != null)
			return hand.getTotalSize();
		return 0;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Tile seeTileAt(int index) {
		if (hand != null) {
			return hand.tileAt(index);
		}
		return null;
	}
	
	public ArrayList<Tile> seeActiveHand() {
		return hand.getActiveTiles();
	}
	
	public ArrayList<Tile> seeFunctionedHand() {
		return hand.getFunctionedTiles();
	}
	
	public boolean callFunction(String function, Tile t){
		Random rand = new Random();
		//System.out.println("Player-callFunction numEat: " + numEat);
		//System.out.println("Player-callFunction numDouble: " + numDouble);
		//System.out.println("Player-callFunction numTriple: " + numTriple);
		
		if (numEat != 0){
			randomEat = rand.nextInt(numEat)+1;
		}
		if (numDouble != 0){
			randomDouble = rand.nextInt(numDouble)+1;
		}
		if (numTriple != 0){
			randomTriple = rand.nextInt(numTriple)+1;
		}
		
		if (function.equals("e")) {
			Function.performEat(hand, t, randomEat);
		}
		else if (function.equals("d")){
			Function.performDou(hand, t, randomDouble);
		}
		else if (function.equals("t")){
			Function.performTriple(hand, t,  randomTriple); 
		}

		return false;
	}
	
	private class TriggerObserversThread extends Thread {
		@Override
		public void run() {
			super.run();
			triggerObservers();
		}
	}
	
	private void triggerObservers() {
		 
        setChanged();
        notifyObservers();
    }
}
