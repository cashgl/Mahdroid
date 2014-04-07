package com.example.mahdroid;

import java.util.ArrayList;

/**
 * @author Grayson
 *
 */
public class Player {
	private Deck deck;
	private Hand hand;
	private Discard discard;
	private Tile tempTile;
	
	public Player(Deck d) {
		deck = d;
		hand = new Hand();
		discard = new Discard();
	}
	
	public Tile drawTile(){
		tempTile = deck.draw();
		hand.add(tempTile);
		return tempTile;
	}
	
	public Tile lastDiscard(){
		return discard.grabLast();
	}
	
	/**
	 * Takes the index of the hand and discards that tile
	 * @param i
	 */
	public void discardTile(int i){
		if (i >= 0 && i < 13) {
			discard.add(hand.removeAt(i));
			hand.add(tempTile);
			tempTile = null;
		}
		else {
			discard.add(tempTile);
			tempTile = null;
		}
	}
	
	/**
	 * @param discardTile
	 * @return
	 */
	public String evaluate(Tile discardTile){
		StringBuilder s = new StringBuilder();
		if (Function.eat(hand, discardTile))
			s.append("e");
		if (Function.dou(hand, discardTile))
			s.append("d");
		if (Function.triple(hand, discardTile))
			s.append("t");
		if (Function.skip(hand))
			s.append("s");
		if (Function.win(hand, tempTile))
			s.append("w");
		
		return s.toString();
	}
	
	public int getHandSize() {
		if (hand != null) {
			return hand.getSize();
		}
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
	
	public boolean callFunction(String function){
		if (function.equals("e"))
			Function.performEat(hand, tempTile);
	/*	else if (function.equals("d"))
			Function.performDouble(hand, tempTile);
		else if (function.equals("t"))
			Function.performTriple(hand, tempTile);  */

		return false;
	}
}
