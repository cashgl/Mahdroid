package com.example.mahdroid;

import android.util.Log;


public class Function {

	public static int eat(Hand hand, Tile t) {
		int eatCount = 0;
		for (int i = 0; i < hand.getActiveSize()-1; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() &&
					hand.tileAt(i+1).getSuit() == t.getSuit() &&
					hand.tileAt(i).getValue()-1 == t.getValue() &&
					hand.tileAt(i+1).getValue()-2 == t.getValue()) {
				eatCount++;
			}
			else if ( hand.tileAt(i).getSuit() == t.getSuit() &&
					hand.tileAt(i+1).getSuit() == t.getSuit() &&
					hand.tileAt(i).getValue()+1 == t.getValue() &&
					hand.tileAt(i+1).getValue()-1 == t.getValue()) {
				eatCount++;
			}
			else if (hand.tileAt(i).getSuit() == t.getSuit() &&
					hand.tileAt(i+1).getSuit() == t.getSuit() &&
					hand.tileAt(i).getValue()+2 == t.getValue() &&
					hand.tileAt(i+1).getValue()+1 == t.getValue()) {
				eatCount++;
			}
		}

		return eatCount;
	}

	public static void performEat(Hand hand, Tile t, int num) { 
		int current = 1;
		for (int i = 0; i < hand.getActiveSize()-1; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() &&
					hand.tileAt(i+1).getSuit() == t.getSuit() &&
					hand.tileAt(i).getValue()-1 == t.getValue() &&
					hand.tileAt(i+1).getValue()-2 == t.getValue()) {
				if (current == num) {
					hand.add(t);
					if (hand.tileAt(i).equals(hand.tileAt(i+1))) {
						hand.functionedTiles(i, i+1);
						hand.functionedTiles(i+1, i+1);
					} 
					else if (hand.tileAt(i+1).equals(hand.tileAt(i+2))) {
						hand.functionedTiles(i, i+1);
						hand.functionedTiles(i+1, i+1);
					} else
						hand.functionedTiles(i, i+2);
				} else
					current++;
			}
			else if ( hand.tileAt(i).getSuit() == t.getSuit() &&
					hand.tileAt(i+1).getSuit() == t.getSuit() &&
					hand.tileAt(i).getValue()+1 == t.getValue() &&
					hand.tileAt(i+1).getValue()-1 == t.getValue()) {
				if (current == num) {
					hand.add(t);
					hand.functionedTiles(i, i+2);
				} else
					current++;
			}
			else if (hand.tileAt(i).getSuit() == t.getSuit() &&
					hand.tileAt(i+1).getSuit() == t.getSuit() &&
					hand.tileAt(i).getValue()+2 == t.getValue() &&
					hand.tileAt(i+1).getValue()+1 == t.getValue()) {
				if (current == num) {
					hand.add(t);
					if (hand.tileAt(i+2).equals(hand.tileAt(i+1))) {
						hand.functionedTiles(i, i+1);
						hand.functionedTiles(i+1, i+1);
					} else
						hand.functionedTiles(i, i+2);
				} else
					current++;
			}
		}
	}

	public static int dou(Hand hand, Tile t) {
		int doubleCount = 0;
		for (int i = 0; i < hand.getActiveSize() - 1; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
					hand.tileAt(i + 1).getSuit() == t.getSuit() && 
					hand.tileAt(i).getValue() == t.getValue() && 
					hand.tileAt(i + 1).getValue() == t.getValue()){
				doubleCount++;
			}
		}
		return doubleCount;
	}

	public static void performDou(Hand hand, Tile t, int num) {
		int current = 1;
		for (int i = 0; i < hand.getActiveSize() - 1; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
					hand.tileAt(i + 1).getSuit() == t.getSuit() && 
					hand.tileAt(i).getValue() == t.getValue() && 
					hand.tileAt(i + 1).getValue() == t.getValue()){
				if (current == num) {
					hand.add(t);
					hand.functionedTiles(i, i+2);
				} else
					current++;
			}
		}
	}

	public static int triple(Hand hand, Tile t) {
		int tripleCount = 0;
		for (int i = 0; i < hand.getActiveSize() - 2; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
					hand.tileAt(i).getSuit() == hand.tileAt(i+2).getSuit() && 
					hand.tileAt(i).getValue() == t.getValue() &&
					hand.tileAt(i).getValue() == hand.tileAt(i+2).getValue()){
				tripleCount++;
			}
		}
		return tripleCount;
	}

	public static void performTriple(Hand hand, Tile t, int num) {
		int current = 0;
		for (int i = 0; i < hand.getActiveSize() - 2; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
					hand.tileAt(i).getSuit() == hand.tileAt(i+2).getSuit() && 
					hand.tileAt(i).getValue() == t.getValue() &&
					hand.tileAt(i).getValue() == hand.tileAt(i+2).getValue()){
				if (current == num) 
					hand.functionedTiles(i, i+2);
				else
					current++;
			}
		}
	}

	// This method is for one of the winning hand patterns
	public static boolean douWin(Hand hand, Tile t) {
		// when hand size = 1
		if (hand.getActiveSize() == 1 && 
				hand.tileAt(0).getSuit() == t.getSuit() && 
				hand.tileAt(0).getValue() == t.getValue())
			return true;
		if (hand.getActiveSize() == 4) {
			if (hand.tileAt(0).getSuit() == t.getSuit() && 
					hand.tileAt(0).getValue() == t.getValue() || 
					hand.tileAt(2).getSuit() == t.getSuit() && 
					hand.tileAt(2).getValue() == t.getValue()){
				return true;
			}
		}
		return false;
	}

	// helper method for win()
	public static boolean check(Hand activeHand, Tile t) {
		Hand checkHand = new Hand();
		boolean result;
		boolean specialCaseResult;
		// special case(1 1 2 2), win on 1 or 2
		if (activeHand.getActiveSize() == 4 && 
				activeHand.tileAt(0).getSuit() == activeHand.tileAt(1).getSuit() && 
				activeHand.tileAt(2).getSuit() == activeHand.tileAt(3).getSuit() && 
				activeHand.tileAt(0).getValue() == activeHand.tileAt(1).getValue() && 
				activeHand.tileAt(2).getValue() == activeHand.tileAt(3).getValue()) {
			specialCaseResult = douWin(activeHand, t);
			if (specialCaseResult){
				return true;
			}else{
				return false;
			}
		}
		// make a copy of the activeHand
		for (int i = 0; i < activeHand.getActiveSize(); i++) {
			checkHand.add(activeHand.tileAt(i));
		}
		// add the tile into activeHand
		checkHand.add(t);	
		// remove the pair
		for (int i = 0; i < checkHand.getActiveSize() - 1; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
					checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue()) {
				//System.out.println("Removed pair: " + checkHand.removeAt(i));
				//System.out.println("Removed pair: " + checkHand.removeAt(i));
				checkHand.removeAt(i);
				checkHand.removeAt(i);
				break;
			}
		}			
		// remove the triple
		for (int i = 0; i < checkHand.getActiveSize() - 2; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
					checkHand.tileAt(i + 1).getSuit() == checkHand.tileAt(i + 2).getSuit() && 
					checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue() && 
					checkHand.tileAt(i + 1).getValue() == checkHand.tileAt(i + 2).getValue()) {
				//System.out.println("Removed triple: " + checkHand.removeAt(i));
				//System.out.println("Removed triple: " + checkHand.removeAt(i));
				//System.out.println("Removed triple: " + checkHand.removeAt(i));
				checkHand.removeAt(i);
				checkHand.removeAt(i);
				checkHand.removeAt(i);
			}
		}		
		// remove the four
		for (int i = 0; i < checkHand.getActiveSize() - 3; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
					checkHand.tileAt(i + 1).getSuit() == checkHand.tileAt(i + 2).getSuit() && 
					checkHand.tileAt(i + 2).getSuit() == checkHand.tileAt(i + 3).getSuit() && 
					checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue() && 
					checkHand.tileAt(i + 1).getValue() == checkHand.tileAt(i + 2).getValue() && 
					checkHand.tileAt(i + 2).getValue() == checkHand.tileAt(i + 3).getValue()) {
				//System.out.println("Removed four: " + checkHand.removeAt(i));
				//System.out.println("Removed four: " + checkHand.removeAt(i));
				//System.out.println("Removed four: " + checkHand.removeAt(i));
				//System.out.println("Removed four: " + checkHand.removeAt(i));
				checkHand.removeAt(i);
				checkHand.removeAt(i);
				checkHand.removeAt(i);
				checkHand.removeAt(i);
			}
		}

		result = runCheck(checkHand);

		if (result == true) {
			return true;
		} else{
			return false;
		}
	}

	// helper method for win()
	public static boolean check1(Hand activeHand, Tile t) {
		//System.out.println("Check 1 executd");
		Hand checkHand = new Hand();
		boolean result;
		boolean specialCaseResult;
		// special case(1 1 2 2), win on 1 or 2
		if (activeHand.getActiveSize() == 4 && 
				activeHand.tileAt(0).getSuit() == activeHand.tileAt(1).getSuit() && 
				activeHand.tileAt(2).getSuit() == activeHand.tileAt(3).getSuit() && 
				activeHand.tileAt(0).getValue() == activeHand.tileAt(1).getValue() && 
				activeHand.tileAt(2).getValue() == activeHand.tileAt(3).getValue()) {
			specialCaseResult = douWin(activeHand, t);
			if (specialCaseResult){
				return true;
			} else{
				return false;
			}
		}
		// make a copy of the activeHand
		for (int i = 0; i < activeHand.getActiveSize(); i++) {
			checkHand.add(activeHand.tileAt(i));
		}
		// add the tile into activeHand
		checkHand.add(t);		
		// remove the triple
		for (int i = 0; i < checkHand.getActiveSize() - 2; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
					checkHand.tileAt(i + 1).getSuit() == checkHand.tileAt(i + 2).getSuit() && 
					checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue() && 
					checkHand.tileAt(i + 1).getValue() == checkHand.tileAt(i + 2).getValue()) {
				//System.out.println("Removed triple: " + checkHand.removeAt(i));
				//System.out.println("Removed triple: " + checkHand.removeAt(i));
				//System.out.println("Removed triple: " + checkHand.removeAt(i));
				checkHand.removeAt(i);
				checkHand.removeAt(i);
				checkHand.removeAt(i);
			}
		}		
		// remove the four
		for (int i = 0; i < checkHand.getActiveSize() - 3; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
					checkHand.tileAt(i + 1).getSuit() == checkHand.tileAt(i + 2).getSuit() && 
					checkHand.tileAt(i + 2).getSuit() == checkHand.tileAt(i + 3).getSuit() && 
					checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue() && 
					checkHand.tileAt(i + 1).getValue() == checkHand.tileAt(i + 2).getValue() && 
					checkHand.tileAt(i + 2).getValue() == checkHand.tileAt(i + 3).getValue()) {
				//System.out.println("Removed four: " + checkHand.removeAt(i));
				//System.out.println("Removed four: " + checkHand.removeAt(i));
				//System.out.println("Removed four: " + checkHand.removeAt(i));
				//System.out.println("Removed four: " + checkHand.removeAt(i));
				checkHand.removeAt(i);
				checkHand.removeAt(i);
				checkHand.removeAt(i);
				checkHand.removeAt(i);
			}
		}
		// remove the pair
		for (int i = 0; i < checkHand.getActiveSize() - 1; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
					checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue()) {
				//System.out.println("Removed pair: " + checkHand.removeAt(i));
				//System.out.println("Removed pair: " + checkHand.removeAt(i));
				checkHand.removeAt(i);
				checkHand.removeAt(i);
				break;
			}
		}

		result = runCheck(checkHand);

		if (result == true) {
			return true;
		} else{
			return false;
		}
	}

	// helper method for win() special win case(7 pairs)
	public static boolean check2(Hand activeHand, Tile t) {
		Hand checkHand = new Hand();
		// make a copy of the activeHand
		for (int i = 0; i < activeHand.getActiveSize(); i++) {
			checkHand.add(activeHand.tileAt(i));
		}
		// add the tile into activeHand
		checkHand.add(t);
		// remove the pair
		for (int i = 0; i < checkHand.getActiveSize() - 1; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
					checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue()) {
				checkHand.removeAt(i);
				checkHand.removeAt(i);
				i = i - 1;
			}//end if
			else {
				if (checkHand.getActiveSize() < 6){
					return false;
				}
				runCheck(checkHand);
				if (checkHand.getActiveSize() == 6){
					i = 0;
					if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i+1).getSuit() &&
							checkHand.tileAt(i+1).getSuit() == checkHand.tileAt(i+3).getSuit() &&
							checkHand.tileAt(i).getValue()+1== checkHand.tileAt(i+1).getValue() &&
							checkHand.tileAt(i+1).getValue()+1 == checkHand.tileAt(i+3).getValue() &&
							checkHand.tileAt(i+2).getSuit() == checkHand.tileAt(i+4).getSuit() &&
							checkHand.tileAt(i+4).getSuit() == checkHand.tileAt(i+5).getSuit() &&
							checkHand.tileAt(i+2).getValue()+1 == checkHand.tileAt(i+4).getValue() &&
							checkHand.tileAt(i+4).getValue()+1 == checkHand.tileAt(i+5).getValue()){
						return true;
					}//end if
					else if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i+2).getSuit() &&
							checkHand.tileAt(i+2).getSuit() == checkHand.tileAt(i+4).getSuit() &&
							checkHand.tileAt(i).getValue()+1== checkHand.tileAt(i+2).getValue() &&
							checkHand.tileAt(i+2).getValue()+1 == checkHand.tileAt(i+4).getValue() &&
							checkHand.tileAt(i+1).getSuit() == checkHand.tileAt(i+3).getSuit() &&
							checkHand.tileAt(i+3).getSuit() == checkHand.tileAt(i+5).getSuit() &&
							checkHand.tileAt(i+1).getValue()+1 == checkHand.tileAt(i+3).getValue() &&
							checkHand.tileAt(i+3).getValue()+1 == checkHand.tileAt(i+5).getValue()){
						return true;
					}//end else if
					else {
						return false;
					}//end else
				}//end if
			}//end else
		}//end for

		if (checkHand.getActiveSize() == 0) {
			return true;
		} else{
			return false;
		}
	}

	public static boolean runCheck(Hand activeHand) {
		int i = 0;
		if (activeHand.tileAt(i).getSuit() != activeHand.tileAt(i + 1).getSuit() || 
				activeHand.tileAt(i + 1).getSuit() != activeHand.tileAt(i + 2).getSuit() || 
				activeHand.tileAt(i).getValue() + 1 != activeHand.tileAt(i + 1).getValue() || 
				activeHand.tileAt(i + 1).getValue() + 1 != activeHand.tileAt(i + 2).getValue()) {
			return false;
		} 
		else {
			//System.out.println("Removed: " + activeHand.removeAt(i));
			//System.out.println("Removed: " + activeHand.removeAt(i));
			//System.out.println("Removed: " + activeHand.removeAt(i));
			activeHand.removeAt(i);
			activeHand.removeAt(i);
			activeHand.removeAt(i);
			if (activeHand.getActiveSize() >= 3){
				return runCheck(activeHand);
			}
			else if (activeHand.getActiveSize() > 0 && activeHand.getActiveSize() < 3){
				return false;
			}
		}
		return true;
	}

	public static boolean win(Hand activeHand, Tile t) {
		// winning hand pattern: 1, win on: 1
		if (activeHand.getActiveSize() == 1 && douWin(activeHand, t))
			return true;
		// winning hand pattern: 1 1 2 2, win on: 1 or 2
		else if (activeHand.getActiveSize() == 4 && check(activeHand, t)){
			return true;
		}
		// winning hand pattern: 1 1 2 3 4 5 6, win on: 1 or 4 or 7
		else if (activeHand.getActiveSize() == 7 && check(activeHand, t) || 
				activeHand.getActiveSize() == 7 && check1(activeHand, t) ||
				activeHand.getActiveSize() == 7 && check2(activeHand, t)) {
			return true;
		}
		// winning hand pattern: 1 1 2 2 2 3 4 5 7 8, win on: 6 or 9
		else if (activeHand.getActiveSize() == 10 && check(activeHand, t) ||
				activeHand.getActiveSize() == 10 && check1(activeHand, t) ||
				activeHand.getActiveSize() == 10 && check2(activeHand, t)){
			return true;
		}
		// winning hand pattern: 1 2 3 4 5 6 7 8 9 2 2 2 3, win on: 1 or 3 or 4
		else if (activeHand.getActiveSize() == 13 && check(activeHand, t) ||
				activeHand.getActiveSize() == 13 && check1(activeHand, t) ||
				activeHand.getActiveSize() == 13 && check2(activeHand, t)){
			return true;
		}
		else
			return false;
	}

	public static boolean skip(Hand h) {

		return true;
	}

}