package com.example.mahdroid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import android.widget.ImageView;



public class Deck {

	private Queue<Tile> deck;
	private int size;
	private ArrayList<Integer> defaultValue;
	private ArrayList<ImageView> defualtPic;
	private ArrayList<Integer> defaultSuit;
	
	public Deck() {
		deck = new LinkedList<Tile>();
		ArrayList<Tile> tempDeck = new ArrayList<Tile>();
		//Creates 4 copies of all tiles
		for (int i = 1; i<= 4; i++){
			//Creating the standard tiles
			for (int suit = 0; suit <= 2; suit++) {
				for (int value = 1; value <= 9; value++) {
					tempDeck.add(new Tile(suit,value));
				}
			}
			//Creating the wind tiles
			for (int value = 1; value <= 4; value++) {
				tempDeck.add(new Tile(3,value));
			}
			//Creating the dragon tiles
			for (int value = 1; value <= 3; value++) {
				tempDeck.add(new Tile(4,value));
			}
		}
		
		Random rand = new Random();
		int sizeLeft = 136;
		
		while (tempDeck.size() != 0) {
			deck.offer(tempDeck.remove(rand.nextInt(sizeLeft)));
			sizeLeft--;
			this.size++;
		}
	}

	
	public Tile draw(Tile tile){
		
		return tile;
	}
	
	/**
	 * This deals tiles to all the players at the beginning
	 */
	public void deal(){
		//ArrayList bamboo
		
	}

	/**
	 * This shuffles all the tiles after each round
	 */
	public void shuffle(){
		
		
	}
	
	public int getSize() {
		return this.size;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		while (!this.deck.isEmpty()) {
			sb.append(deck.poll().toString() + "\n");
		}
		return sb.toString();
	}
}
