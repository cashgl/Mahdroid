package com.example.mahdroid;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Grayson
 *
 */
public class Player {
	private Deck deck;
	private Hand hand;
	private Discard discard;
	private int numEat, numDouble, numTriple;
	
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
		if (function.equals("e")) {
			int n = rand.nextInt(numEat)+1;
			Function.performEat(hand, t);
		}
		else if (function.equals("d")){
			int n = rand.nextInt(numDouble)+1;
			Function.performDou(hand, t);
		}
		else if (function.equals("t")){
			int n = rand.nextInt(numDouble)+1;
			Function.performTriple(hand, t); 
		}

		return false;
	}
}
