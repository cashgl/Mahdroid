package com.example.mahdroid;

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
		if (i >= 0 && i < 13)
			discard.add(hand.removeAt(i));
		else {
			discard.add(tempTile);
			tempTile = null;
		}
	}
	
	public void evaluate(){
		
		
	}
	
	public boolean callFunction(String function){
		if (function.contains("e"))
			return Function.eat(hand, tempTile);
		else if (function.contains("d"))
			return Function.dou(hand, tempTile);
		else if (function.contains("t"))
			return Function.triple(hand, tempTile);
		else if (function.contains("w"))
			return Function.win(hand, tempTile);
		return false;
	}
}
