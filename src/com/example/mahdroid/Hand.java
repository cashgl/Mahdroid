package com.example.mahdroid;

import java.util.ArrayList;


public class Hand {
	
	private ArrayList<Tile> activeHand;
	private ArrayList<Tile> functionedHand;
	private int activeSize, functionedSize;

	public Hand() {
		this.activeHand = new ArrayList<Tile>();
		this.functionedHand = new ArrayList<Tile>();
		this.activeSize = 0;
		this.functionedSize = 0;
	}
	
	public String evaluate(){
		return "";
		
	}
	
	public boolean remove(Tile tile){
		if (activeHand.remove(tile)) {
			activeSize--;
			return true;
		}
		return false;
	}
	
	public Tile removeAt(int i) {
		if (i >= activeHand.size())
			return null;
		else {
			activeSize--;
			return activeHand.remove(i);
		}
	}
	
	public void add(Tile add){
		
		int suit = add.getSuit();
		int value = add.getValue();
		
		Tile temp = add;
		int i = 0;
		
		while (temp != null && i < activeSize) {
			if (activeHand.get(i).getSuit() >= suit) {
				if (activeHand.get(i).getValue() >= value) {
					activeHand.add(i, temp);
					activeSize++;
					temp = null;
				}
			}
			i++;
		}
		//If we look through the entire hand and it's greater than all values,
		//we add the tile to the end of the hand
		if (temp != null) {
			activeHand.add(add);
			activeSize++;
		}
	}
	
	public boolean functionedTiles(int a, int b) {
		if (b < a || a < 0 || b < 0 || b >= activeHand.size())
			return false;
		else {
			for (int i = a; i <= (b-a); i++) {
				functionedHand.add(activeHand.remove(a));
				activeSize--;
				functionedSize++;
			}
			return true;
		}
	}
	
	/*public ArrayList<Tile> seeFunctionedTiles() {
		ArrayList<Tile> temp = (ArrayList<Tile>) functionedHand.clone();
		return temp;
	}*/
	
	public int getSize() {
		return this.activeSize;
	}
	
	public Tile tileAt(int i) {
		return activeHand.get(i);
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.activeSize; i++) {
			Tile temp = activeHand.get(i);
			s.append("Suit: " + temp.getSuit() + ", Value: " + temp.getValue() + "\n");
		}
		return s.toString();
	}

}
