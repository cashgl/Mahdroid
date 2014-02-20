package com.example.mahdroid;

import java.util.ArrayList;


public class Hand {
	
	private ArrayList<Tile> exposedHand;
	private ArrayList<Tile> hiddenHand;
	private int size;

	public Hand() {
		this.exposedHand = new ArrayList<Tile>();
		this.hiddenHand = new ArrayList<Tile>();
		this.size = 0;
	}
	
	public void evaluate(){
		
		
	}
	
	public boolean remove(Tile tile){
		size--;
		return exposedHand.remove(tile);
	}
	
	public void add(Tile add){
		
		int suit = add.getSuit();
		int value = add.getValue();
		
		Tile temp = add;
		int i = 0;
		
		while (temp != null && i < size) {
			if (exposedHand.get(i).getSuit() >= suit) {
				if (exposedHand.get(i).getValue() >= value) {
					exposedHand.add(i, temp);
					size++;
					temp = null;
				}
			}
			i++;
		}
		//If we look through the entire hand and it's greater than all values,
		//we add the tile to the end of the hand
		if (temp != null) {
			exposedHand.add(add);
			size++;
		}
		
	}
	
	public int getSize() {
		return this.size;
	}
	
	public Tile tileAt(int i) {
		return exposedHand.get(i);
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.size; i++) {
			Tile temp = exposedHand.get(i);
			s.append("Suit: " + temp.getSuit() + ", Value: " + temp.getValue() + "\n");
		}
		return s.toString();
	}

}
