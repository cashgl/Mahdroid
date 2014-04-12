package com.example.mahdroid;

import java.util.ArrayList;

public class Function {

	public static boolean eat(Hand hand, Tile t){
		for (int i = 0; i< hand.getSize()-1; i++) {
			//eg. when u have 3 & 4 and u pick up 5, then 5 will be added to the end 
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue()+1 == hand.tileAt(i+1).getValue() && 
				hand.tileAt(i+1).getValue()+1 == t.getValue())
				return true;
			//eg. when u have 3 & 4 and u pick up 2, then 2 will be added to the front
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue()-1 == t.getValue() && 
				hand.tileAt(i).getValue()+1 == hand.tileAt(i+1).getValue())
				return true;
			//eg. when u have 3 & 5 and u pick up 4, then 4 will be added in between
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue()+1 == t.getValue() && 
				hand.tileAt(i).getValue()+2 == hand.tileAt(i+1).getValue())
				return true;
		}
		return false;
	}
	
	public static void performEat(Hand hand, Tile t) {
		for (int i = 0; i < hand.getSize()-1; i++) {
			//eg. when u have 3 & 4 and u pick up 5, then 5 will be added to the end 
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue()+1 == hand.tileAt(i+1).getValue() && 
				hand.tileAt(i+1).getValue()+1 == t.getValue()){
				//manipulate
				hand.functionedTiles(i, i+2);
			}
			//eg. when u have 3 & 4 and u pick up 2, then 2 will be added to the front
			if (hand.tileAt(i).getSuit() == t.getSuit() &&
				hand.tileAt(i).getValue()-1 == t.getValue() && 
				hand.tileAt(i).getValue()+1 == hand.tileAt(i+1).getValue()){
				//manipulate
				hand.functionedTiles(i, i+2);
			}
			//eg. when u have 3 & 5 and u pick up 4, then 4 will be added in between
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue()+1 == t.getValue() && 
				hand.tileAt(i).getValue()+2 == hand.tileAt(i+1).getValue()){
				//manipulate
				hand.functionedTiles(i, i+2);
			}
		}
	}

	public static boolean dou(Hand hand, Tile t){
		for (int i = 0; i< hand.getSize()-1; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() &&
				hand.tileAt(i+1).getSuit() == t.getSuit() &&
				hand.tileAt(i).getValue() == t.getValue() &&
				hand.tileAt(i+1).getValue() == t.getValue())
			return true;
		}
		return false;
	}
	
	public static void performDou(Hand hand, Tile t){
		/*for (int i = 0; i< activeHand.getSize(); i++) {
			if (activeHand.tileAt(i).getSuit() == t.getSuit() &&
					activeHand.tileAt(i).getValue() == t.getValue()) {
				
			}
		}*/
		for (int i = 0; i< hand.getSize()-1; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() &&
				hand.tileAt(i+1).getSuit() == t.getSuit() &&
				hand.tileAt(i).getValue() == t.getValue() &&
				hand.tileAt(i+1).getValue() == t.getValue())
				//manipulate hand
				hand.functionedTiles(i, i+2);
		}
	}

	public static boolean triple(Hand hand, Tile t){
		for (int i = 0; i< hand.getSize() - 2; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() &&
				hand.tileAt(i+2).getSuit() == t.getSuit() &&
				hand.tileAt(i).getValue() == hand.tileAt(i+2).getValue() &&
				hand.tileAt(i).getValue() == t.getValue())
				return true;
		}
		return false;
	}
	
	public static void performTriple(Hand hand, Tile t){
		for (int i = 0; i< hand.getSize() - 2; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i+2).getSuit() == t.getSuit() &&	
				hand.tileAt(i).getValue() == hand.tileAt(i+2).getValue() &&
				hand.tileAt(i).getValue() == t.getValue())
				//manipulate
				hand.functionedTiles(i, i+3);
		}
	}

	//This method is for one of the winning hand patterns
	public static boolean douWin(Hand hand, Tile t){
		//when hand size = 1
		if (hand.getSize() == 1 && hand.tileAt(0).getSuit() == t.getSuit() &&
				hand.tileAt(0).getValue() == t.getValue())
			return true;
		return false;
	}
	
	//helper method for win()
	/**
	public static Hand performDouWin(Hand hand, Tile t){
		for (int i = 0; i < hand.getSize(); i++){
			if (hand.tileAt(i).getSuit() == t.getSuit() &&
					hand.tileAt(i).getValue() == t.getValue()){
				hand.functionedTiles(i, i+1);
			}
		}
		return hand;
	}
	**/
	
	//helper method for win()
	public static boolean check(Hand activeHand, Tile t){
		Hand checkHand = new Hand();
		for (int i = 0; i < activeHand.getSize(); i++){
			checkHand.add(activeHand.tileAt(i));
		}
		
		//add the tile into activeHand
		checkHand.add(t);
		int pairCount = 0;
		int runCount = 0;
		int tripleCount = 0;
		//pair detector
		for (int i = 0; i < checkHand.getSize()-1; i++){
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i+1).getSuit() &&
				checkHand.tileAt(i).getValue() == checkHand.tileAt(i+1).getValue()){
				pairCount++;
			}
		}
		//run detector
		for (int i = 0; i < checkHand.getSize()-2; i++){
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i+1).getSuit() &&
				checkHand.tileAt(i+1).getSuit() == checkHand.tileAt(i+2).getSuit() &&
				checkHand.tileAt(i).getValue()+1 == checkHand.tileAt(i+1).getValue() &&
				checkHand.tileAt(i+1).getValue()+1 == checkHand.tileAt(i+2).getValue()){
				runCount++;
			}
		}
		//triple detector
		for (int i = 0; i < checkHand.getSize()-3; i++){
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i+1).getSuit() &&
				checkHand.tileAt(i+1).getSuit() == checkHand.tileAt(i+2).getSuit() &&
				checkHand.tileAt(i).getValue() == checkHand.tileAt(i+1).getValue() &&
				checkHand.tileAt(i+1).getValue() == checkHand.tileAt(i+2).getValue()){
				tripleCount++;
			}
		}
		return true;
	}
	
	
	
	public static boolean win(Hand activeHand, Tile t){
		//winning hand pattern: 1, win on: 1
		if (activeHand.getSize() == 1 && douWin(activeHand, t))
			return true;
		//winning hand pattern: 1 2 3 4, win on: 1 or 4
		//else if (activeHand.getSize() == 4 && douWin(activeHand, t))
			//return true;
		//winning hand pattern: 1 1 2 2, win on: 1 or 2
		else if (activeHand.getSize() == 4 && dou(activeHand, t))
			return true;
		//winning hand pattern: 1 1 2 3 4 5 6, win on: 1 or 4 or 7
		else if (activeHand.getSize() == 7 && eat(activeHand, t)){
			System.out.println("Executed");
			return true;
		}
		//winning hand pattern: 2 3 4 5 6 7 8, win on: 2 or 5 or 8
		//else if (activeHand.getSize() == 7 && douWin(activeHand, t))
			//return true;
		//winning hand pattern: 1 1 2 3 4 5 5, win on: 1 or 5
		else if (activeHand.getSize() == 7 && dou(activeHand, t)){
			System.out.println("Executed");
			return true;
		}
		//winning hand pattern: 1 1 2 2 2 3 4 5 7 8, win on: 6 or 9
		else if (activeHand.getSize() == 10 && eat(activeHand, t))
			return true;
		//winning hand pattern: 1 1 1 2 3 4 5 6 7 8, win on: 2 or 5 or 8
		//else if (activeHand.getSize() == 10 && douWin(activeHand, t))
			//return true;
		//winning hand pattern: 1 1 2 3 4 5 6 7 9 9,  win on: 1 or 9
		else if (activeHand.getSize() == 10 && dou(activeHand, t))
			return true;
		//winning hand pattern: 1 2 3 4 5 6 7 8 9 2 2 2 3, win on: 1 or 3 or 4
		else if (activeHand.getSize() == 13 && eat(activeHand, t))
			return true;
		//winning hand pattern: 1 2 3 4 5 6 7 8 9 2 2 2 5, win on: 5
		//else if (activeHand.getSize() == 13 && douWin(activeHand, t))
			//return true;
		//winning hand pattern: 1 1 1 2 3 4 5 6 7 8 9 9 9, win on: 1 or 9
		else if (activeHand.getSize() == 13 && dou(activeHand, t))
			return true;
		else 
			return false;
	}

	public static boolean skip(Hand h){

		return true;
	}

}