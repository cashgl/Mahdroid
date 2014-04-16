package com.example.mahdroid;

import java.util.Stack;


public class Discard {

	private Stack<Tile> pile;
	private int size;
	
	public Discard() {
		this.pile = new Stack<Tile>();
		this.size = 0;
	}
	
	public boolean add(Tile tile){
		if (this.pile.push(tile).equals(tile)) {
			this.size++;
			return true;
		}
		return false;
	}

	public Tile remove(){
		this.size--;
		return pile.pop();
	}
	
	public int getSize() {
		return this.size;
	}
	
	public Tile grabLast() {
		if (pile.size() != 0)
			return pile.peek();
		return null;
	}
	
	public Tile useLast() {
		if (pile.size() != 0)
			return pile.pop();
		return null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.size; i++) {
			sb.append(this.pile.get(i) + "\n");
		}
		return sb.toString();
		
	}
}
