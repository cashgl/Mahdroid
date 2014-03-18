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
		tempTile = deck.draw();
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
	
	public String evaluate(){
		StringBuilder s = new StringBuilder();
		if (Function.eat(hand, tempTile))
			s.append("e");
		if (Function.dou(hand, tempTile))
			s.append("d");
		if (Function.triple(hand, tempTile))
			s.append("t");
		if (Function.skip(hand))
			s.append("s");
		if (Function.win(hand, tempTile))
			s.append("w");
		
		return s.toString();
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
