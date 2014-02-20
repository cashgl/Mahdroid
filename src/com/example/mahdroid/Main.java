package com.example.mahdroid;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hand hand = new Hand();
		Tile tile1 = new Tile(1, 1);
		Tile tile2 = new Tile(3, 5);
		Tile tile3 = new Tile(1, 2);
		Tile tile4 = new Tile(2, 3);
		Tile tile5 = new Tile(1, 2);
		Tile tile6 = new Tile(2, 2);
		Tile tile7 = new Tile(2, 4);
		
		hand.add(tile1);
		hand.add(tile2);
		hand.add(tile3);
		hand.add(tile4);
		hand.add(tile5);
		hand.add(tile6);
		hand.add(tile7);
		System.out.println(hand);
		
		Tile tile8 = new Tile(1, 2);
		hand.add(tile8);
		
		System.out.println(Function.triple(hand));

		hand.remove(tile3);
		System.out.println(Function.triple(hand));

	}

}
