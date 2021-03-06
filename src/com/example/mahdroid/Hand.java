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
			if (activeHand.get(i).getSuit() == suit) {

				if(activeHand.get(i).getValue() >= value) {
					activeHand.add(i, temp);
					activeSize++;
					temp = null;
				}
			}
			else if (activeHand.get(i).getSuit() > suit) {
				activeHand.add(i, temp);
				activeSize++;
				temp = null;
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
			for (int i = a; i <= b; i++) {
				functionedHand.add(activeHand.remove(a));
				activeSize--;
				functionedSize++;
			}
			return true;
		}
	}
	
	public ArrayList<Tile> getActiveTiles() {
		return activeHand;
	}

	public ArrayList<Tile> getFunctionedTiles() {
		return functionedHand;
	}

	public int getActiveSize() {
		return this.activeSize;
	}
	
	public int getFunctionedSize() {
		return this.functionedSize;
	}
	
	public int getTotalSize() {
		return this.functionedSize + this.activeSize;
	}

	public Tile tileAt(int i) {
		if (i >= activeSize + functionedSize)
			return null;
		else if (i < 0)
			return null;
		
		if (i < activeSize)
			return activeHand.get(i);
		
		i = i - activeSize;
		return functionedHand.get(i);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.activeSize; i++) {
			Tile temp = activeHand.get(i);
			s.append("(" + temp.getSuit() + "," + temp.getValue() + ") ");
		}
		return s.toString();
	}

}
