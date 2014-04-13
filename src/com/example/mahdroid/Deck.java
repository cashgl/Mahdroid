package com.example.mahdroid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Deck {

	private Queue<Tile> deck;
	private int size;
	
	public Deck() {
		this.deck = new LinkedList<Tile>();
		this.size = 0;
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
		}
		
		//Instantiates random object and how many cards are left in
		//the temporary deck
		Random rand = new Random();
		int sizeLeft = 124;
		
		//Shuffles the deck
		while (tempDeck.size() != 0) {
			deck.offer(tempDeck.remove(rand.nextInt(sizeLeft)));
			sizeLeft--;
			this.size++;
		}
	}

	/**
	 * This method gives the user a tile from the deck.
	 * @return an individual Tile
	 */
	public Tile draw(){
		this.size--;
		return deck.poll();
	}

	/**
	 * This shuffles all the tiles after each round
	 */
	public void shuffle(){
		
		//Do to save on memory / garbage collection
	}
	
	/**
	 * This returns the size of the deck.
	 * @return size of deck
	 */
	public int getSize() {
		return this.size;
	}
	
	public String toString() {
		return deck.toString();
	}
}
