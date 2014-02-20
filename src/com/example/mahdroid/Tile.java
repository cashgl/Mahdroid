package com.example.mahdroid;

public class Tile {
	//private ImageView pic;
	private int suit;
	private int value;
	
	public Tile(int suit, /*ImageView pic,*/ int value){
		//this.pic = pic;
		this.suit = suit;
		this.value = value;
	}
	
	public int getValue(){
		
		return value;
	}
	
	public int getSuit(){
		
		return suit; 
	}
	
	public boolean equals(Tile t) {
		if (t.getSuit() == this.suit && t.getValue() == this.value)
			return true;
		return false;
	}
	/**
	public ImageView getPic(){
		
		return pic;
	}
	**/
	
}
