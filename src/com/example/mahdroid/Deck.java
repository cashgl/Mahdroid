package com.example.mahdroid;

import java.util.ArrayList;
import java.util.Queue;

import android.widget.ImageView;



public class Deck {

	private Queue<Tile> deck;
	private int size;
	private ArrayList<Integer> defaultValue;
	private ArrayList<ImageView> defualtPic;
	private ArrayList<Integer> defaultSuit;
	
	public Deck() {
		// TODO Auto-generated constructor stub
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
}
