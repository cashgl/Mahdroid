package com.example.mahdroid;

import java.util.ArrayList;

public class Function {

	public static boolean eat(Hand activeHand, Tile t){
		for (int i = 0; i< activeHand.getSize()-1; i++) {
			//eg. when u have 3 & 4 and u pick up 5, then 5 will be added to the end 
			if (activeHand.tileAt(i).getValue()+1 == activeHand.tileAt(i+1).getValue() && 
					activeHand.tileAt(i+1).getValue()+1 == t.getValue() && 
					activeHand.tileAt(i).getSuit() == t.getSuit())
				return true;
			//eg. when u have 3 & 4 and u pick up 2, then 2 will be added to the front
			if (activeHand.tileAt(i).getValue()-1 == t.getValue() && 
					activeHand.tileAt(i).getValue()+1 == activeHand.tileAt(i+1).getValue() && 
					activeHand.tileAt(i).getSuit() == t.getSuit())
				return true;
			//eg. when u have 3 & 5 and u pick up 4, then 4 will be added in between
			if (activeHand.tileAt(i).getValue()+1 == t.getValue() && 
					activeHand.tileAt(i).getValue()+2 == activeHand.tileAt(i+1).getValue() && 
					activeHand.tileAt(i).getSuit() == t.getSuit())
				return true;
		}
		return false;
	}
	
	public static ArrayList<Tile> performEat(Hand activeHand, Tile t) {
		ArrayList<Tile> exposedHand = new ArrayList<Tile>();
		for (int i = 0; i< activeHand.getSize()-1; i++) {
			//eg. when u have 3 & 4 and u pick up 5, then 5 will be added to the end 
			if (activeHand.tileAt(i).getValue()+1 == activeHand.tileAt(i+1).getValue() && 
					activeHand.tileAt(i+1).getValue()+1 == t.getValue() && 
					activeHand.tileAt(i).getSuit() == t.getSuit()){
				//manipulate
				exposedHand.add(activeHand.tileAt(i));
				exposedHand.add(activeHand.tileAt(i+1));
				exposedHand.add(t);
				activeHand.remove(activeHand.tileAt(i));
				activeHand.remove(activeHand.tileAt(i+1));
			}
			//eg. when u have 3 & 4 and u pick up 2, then 2 will be added to the front
			if (activeHand.tileAt(i).getValue()-1 == t.getValue() && 
					activeHand.tileAt(i).getValue()+1 == activeHand.tileAt(i+1).getValue() && 
					activeHand.tileAt(i).getSuit() == t.getSuit()){
				//manipulate
				exposedHand.add(t);
				exposedHand.add(activeHand.tileAt(i));
				exposedHand.add(activeHand.tileAt(i+1));
				activeHand.remove(activeHand.tileAt(i));
				activeHand.remove(activeHand.tileAt(i+1));
			}
			//eg. when u have 3 & 5 and u pick up 4, then 4 will be added in between
			if (activeHand.tileAt(i).getSuit() == t.getSuit() && 
					activeHand.tileAt(i).getValue()+1 == t.getValue() && 
					activeHand.tileAt(i).getValue()+2 == activeHand.tileAt(i+1).getValue()){
				//manipulate
				exposedHand.add(activeHand.tileAt(i));
				exposedHand.add(t);
				exposedHand.add(activeHand.tileAt(i+1));
				activeHand.remove(activeHand.tileAt(i));
				activeHand.remove(activeHand.tileAt(i+1));
			}
		}
		//return exposed hand
		return exposedHand;
	}

	public static boolean dou(Hand activeHand, Tile t){
		for (int i = 0; i< activeHand.getSize()-1; i++) {
			if (activeHand.tileAt(i).getSuit() == t.getSuit() &&
					activeHand.tileAt(i+1).getSuit() == t.getSuit() &&
					activeHand.tileAt(i).getValue() == t.getValue() &&
					activeHand.tileAt(i+1).getValue() == t.getValue() )
				return true;
		}
		return false;
	}
	
	public static ArrayList<Tile> performDou(Hand activeHand, Tile t){
		/*for (int i = 0; i< activeHand.getSize(); i++) {
			if (activeHand.tileAt(i).getSuit() == t.getSuit() &&
					activeHand.tileAt(i).getValue() == t.getValue()) {
				
			}
		}*/
		
		
		//This section is going to be removed... it doesn't utilize the functionedTiles
		//method from Hand
		ArrayList<Tile> exposedHand = new ArrayList<Tile>();
		for (int i = 0; i< activeHand.getSize() - 2; i++) {
			if (t.getSuit() == activeHand.tileAt(i).getSuit() && 
					activeHand.tileAt(i).getValue() == activeHand.tileAt(i+1).getValue() && 
					t.getValue() == activeHand.tileAt(i).getValue()){
				//manipulate
				exposedHand.add(activeHand.tileAt(i));
				exposedHand.add(activeHand.tileAt(i+1));
				exposedHand.add(t);
				activeHand.remove(activeHand.tileAt(i));
				activeHand.remove(activeHand.tileAt(i+1));
			}
		}
		
	    //return exposed hand
		return exposedHand;
	}

	public static boolean triple(Hand activeHand, Tile t){
		for (int i = 0; i< activeHand.getSize() - 1; i++) {
			if (activeHand.tileAt(i).getSuit() == t.getSuit() &&
				activeHand.tileAt(i+1).getSuit() == t.getSuit() &&
				activeHand.tileAt(i).getValue() == t.getValue() &&
				activeHand.tileAt(i+1).getValue() == t.getValue())
				return true;
			/*if (activeHand.tileAt(i).getValue() == activeHand.tileAt(i+2).getValue() && 
					t.getSuit() == activeHand.tileAt(i).getSuit() && 
					t.getValue() == activeHand.tileAt(i).getValue())
				return true;*/
		}
		return false;
	}
	
	public static ArrayList<Tile> performTriple(Hand activeHand, Tile t){
		ArrayList<Tile> exposedHand = new ArrayList<Tile>();
		for (int i = 0; i< activeHand.getSize() - 2; i++) {
			if (activeHand.tileAt(i).getValue() == activeHand.tileAt(i+2).getValue() && 
					t.getSuit() == activeHand.tileAt(i).getSuit() && 
					t.getValue() == activeHand.tileAt(i).getValue()){
				//manipulate
				exposedHand.add(activeHand.tileAt(i));
				exposedHand.add(activeHand.tileAt(i+1));
				exposedHand.add(activeHand.tileAt(i+2));
				exposedHand.add(t);
				activeHand.remove(activeHand.tileAt(i));
				activeHand.remove(activeHand.tileAt(i+1));
				activeHand.remove(activeHand.tileAt(i+2));
			}
		}
	
		//return exposed hand
		return exposedHand;
	}

	//This method is for one of the winning hand patterns
	public static boolean douWin(Hand activeHand, Tile t){
		for (int i = 0; i< activeHand.getSize(); i++) {
			if (activeHand.tileAt(i).getValue() == t.getValue() && 
				activeHand.tileAt(i).getSuit() == t.getSuit())
				return true;
		}
		return false;
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
		else if (activeHand.getSize() == 7 && eat(activeHand, t))
			return true;
		//winning hand pattern: 2 3 4 5 6 7 8, win on: 2 or 5 or 8
		//else if (activeHand.getSize() == 7 && douWin(activeHand, t))
			//return true;
		//winning hand pattern: 1 1 2 3 4 5 5, win on: 1 or 5
		else if (activeHand.getSize() == 7 && dou(activeHand, t))
			return true;
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