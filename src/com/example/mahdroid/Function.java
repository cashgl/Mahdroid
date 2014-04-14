package com.example.mahdroid;

import java.util.ArrayList;

public class Function {

	public static boolean eat(Hand hand, Tile t) {
		for (int i = 0; i < hand.getActiveSize() - 1; i++) {
			// eg. when u have 3 & 4 and u pick up 5, then 5 will be added to
			// the end
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue() + 1 == hand.tileAt(i + 1).getValue() && 
				hand.tileAt(i + 1).getValue() + 1 == t.getValue())
				return true;
			// eg. when u have 3 & 4 and u pick up 2, then 2 will be added to
			// the front
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue() - 1 == t.getValue() && 
				hand.tileAt(i).getValue() + 1 == hand.tileAt(i + 1).getValue())
				return true;
			// eg. when u have 3 & 5 and u pick up 4, then 4 will be added in
			// between
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue() + 1 == t.getValue() && 
				hand.tileAt(i).getValue() + 2 == hand.tileAt(i + 1).getValue())
				return true;
		}
		return false;
	}

	public static void performEat(Hand hand, Tile t) {
		for (int i = 0; i < hand.getActiveSize() - 1; i++) {
			// eg. when u have 3 & 4 and u pick up 5, then 5 will be added to
			// the end
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue() + 1 == hand.tileAt(i + 1).getValue() && 
				hand.tileAt(i + 1).getValue() + 1 == t.getValue()) {
				// manipulate
				hand.functionedTiles(i, i + 2);
			}
			// eg. when u have 3 & 4 and u pick up 2, then 2 will be added to
			// the front
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue() - 1 == t.getValue() && 
				hand.tileAt(i).getValue() + 1 == hand.tileAt(i + 1).getValue()) {
				// manipulate
				hand.functionedTiles(i, i + 2);
			}
			// eg. when u have 3 & 5 and u pick up 4, then 4 will be added in
			// between
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue() + 1 == t.getValue() && 
				hand.tileAt(i).getValue() + 2 == hand.tileAt(i + 1).getValue()) {
				// manipulate
				hand.functionedTiles(i, i + 2);
			}
		}
	}

	public static boolean dou(Hand hand, Tile t) {
		for (int i = 0; i < hand.getActiveSize() - 1; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i + 1).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue() == t.getValue() && 
				hand.tileAt(i + 1).getValue() == t.getValue())
				return true;
		}
		return false;
	}

	public static void performDou(Hand hand, Tile t) {
		/*
		 * for (int i = 0; i< activeHand.getSize(); i++) { if
		 * (activeHand.tileAt(i).getSuit() == t.getSuit() &&
		 * activeHand.tileAt(i).getValue() == t.getValue()) {
		 * 
		 * } }
		 */
		for (int i = 0; i < hand.getActiveSize() - 1; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i + 1).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue() == t.getValue() && 
				hand.tileAt(i + 1).getValue() == t.getValue())
				// manipulate hand
				hand.functionedTiles(i, i + 2);
		}
	}

	public static boolean triple(Hand hand, Tile t) {
		for (int i = 0; i < hand.getActiveSize() - 2; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i + 2).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue() == hand.tileAt(i + 2).getValue() && 
				hand.tileAt(i).getValue() == t.getValue())
				return true;
		}
		return false;
	}

	public static void performTriple(Hand hand, Tile t) {
		for (int i = 0; i < hand.getActiveSize() - 2; i++) {
			if (hand.tileAt(i).getSuit() == t.getSuit() && 
				hand.tileAt(i + 2).getSuit() == t.getSuit() && 
				hand.tileAt(i).getValue() == hand.tileAt(i + 2).getValue() && 
				hand.tileAt(i).getValue() == t.getValue())
				// manipulate
				hand.functionedTiles(i, i + 3);
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
				System.out.println("Specail case is true!");
				return true;
			}
		}
		return false;
	}

	// helper method for win()
	/**
	 * public static Hand performDouWin(Hand hand, Tile t){ for (int i = 0; i <
	 * hand.getSize(); i++){ if (hand.tileAt(i).getSuit() == t.getSuit() &&
	 * hand.tileAt(i).getValue() == t.getValue()){ hand.functionedTiles(i, i+1);
	 * } } return hand; }
	 **/

	// helper method for win()
	public static boolean check(Hand activeHand, Tile t) {
		System.out.println("Check executd");
		Hand checkHand = new Hand();
		int pairCount = 0;
		boolean result;
		boolean specialCaseResult;
		boolean removedPair;
		// special case(1 1 2 2), win on 1 or 2
		if (activeHand.getActiveSize() == 4 && 
			activeHand.tileAt(0).getSuit() == activeHand.tileAt(1).getSuit() && 
			activeHand.tileAt(2).getSuit() == activeHand.tileAt(3).getSuit() && 
			activeHand.tileAt(0).getValue() == activeHand.tileAt(1).getValue() && 
			activeHand.tileAt(2).getValue() == activeHand.tileAt(3).getValue()) {
			System.out.println("Specail case!");
			specialCaseResult = douWin(activeHand, t);
			if (specialCaseResult){
				System.out.println("Comfirmed");
				return true;
			}
			else
				return false;
		}
		// make a copy of the activeHand
		for (int i = 0; i < activeHand.getActiveSize(); i++) {
			checkHand.add(activeHand.tileAt(i));
		}
		// add the tile into activeHand
		checkHand.add(t);
		System.out.print(checkHand.toString());
		System.out.println("Active size:" + checkHand.getActiveSize());
		
		// remove the pair
		for (int i = 0; i < checkHand.getActiveSize() - 1; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
				checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue()) {
				System.out.println();
				// System.out.println("i: " + i);
				// System.out.println("i: " + (i+1));
				System.out.println("Removed pair: " + checkHand.removeAt(i));
				System.out.println("Removed pair: " + checkHand.removeAt(i));
				pairCount++;
				removedPair = true;
				break;
			}
			System.out.println("Pair count: " + pairCount);
			System.out.println(checkHand.toString());
		}
				
		// remove the triple
		for (int i = 0; i < checkHand.getActiveSize() - 2; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
				checkHand.tileAt(i + 1).getSuit() == checkHand.tileAt(i + 2).getSuit() && 
				checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue() && 
				checkHand.tileAt(i + 1).getValue() == checkHand.tileAt(i + 2).getValue()) {
				System.out.println("Removed triple: " + checkHand.removeAt(i));
				System.out.println("Removed triple: " + checkHand.removeAt(i));
				System.out.println("Removed triple: " + checkHand.removeAt(i));
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
				System.out.println("Removed four: " + checkHand.removeAt(i));
				System.out.println("Removed four: " + checkHand.removeAt(i));
				System.out.println("Removed four: " + checkHand.removeAt(i));
				System.out.println("Removed four: " + checkHand.removeAt(i));
			}
		}
		
		// if (pairCount == 1){
		result = runCheck(checkHand);
		System.out.println("Result: " + result);
		// }
		if (result == true) {
			System.out.println("yeah");
			return true;
		} else{
			System.out.println("false");
			return false;
		}
	}
	
	// helper method for win()
	public static boolean check1(Hand activeHand, Tile t) {
		System.out.println("Check 1 executd");
		Hand checkHand = new Hand();
		int pairCount = 0;
		boolean result;
		boolean specialCaseResult;
		boolean removedPair;
		// special case(1 1 2 2), win on 1 or 2
		if (activeHand.getActiveSize() == 4 && 
			activeHand.tileAt(0).getSuit() == activeHand.tileAt(1).getSuit() && 
			activeHand.tileAt(2).getSuit() == activeHand.tileAt(3).getSuit() && 
			activeHand.tileAt(0).getValue() == activeHand.tileAt(1).getValue() && 
			activeHand.tileAt(2).getValue() == activeHand.tileAt(3).getValue()) {
			System.out.println("Specail case!");
			specialCaseResult = douWin(activeHand, t);
			if (specialCaseResult){
				System.out.println("Comfirmed");
				return true;
			}
			else
				return false;
		}
		// make a copy of the activeHand
		for (int i = 0; i < activeHand.getActiveSize(); i++) {
			checkHand.add(activeHand.tileAt(i));
		}
		// add the tile into activeHand
		checkHand.add(t);
		System.out.print(checkHand.toString());
		System.out.println("Active size:" + checkHand.getActiveSize());
					
		// remove the triple
		for (int i = 0; i < checkHand.getActiveSize() - 2; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
				checkHand.tileAt(i + 1).getSuit() == checkHand.tileAt(i + 2).getSuit() && 
				checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue() && 
				checkHand.tileAt(i + 1).getValue() == checkHand.tileAt(i + 2).getValue()) {
				System.out.println("Removed triple: " + checkHand.removeAt(i));
				System.out.println("Removed triple: " + checkHand.removeAt(i));
				System.out.println("Removed triple: " + checkHand.removeAt(i));
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
				System.out.println("Removed four: " + checkHand.removeAt(i));
				System.out.println("Removed four: " + checkHand.removeAt(i));
				System.out.println("Removed four: " + checkHand.removeAt(i));
				System.out.println("Removed four: " + checkHand.removeAt(i));
			}
		}
			
		// remove the pair
		for (int i = 0; i < checkHand.getActiveSize() - 1; i++) {
			if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
				checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue()) {
				System.out.println();
				// System.out.println("i: " + i);
				// System.out.println("i: " + (i+1));
				System.out.println("Removed pair: " + checkHand.removeAt(i));
				System.out.println("Removed pair: " + checkHand.removeAt(i));
				pairCount++;
				removedPair = true;
				break;
			}
			System.out.println("Pair count: " + pairCount);
			System.out.println(checkHand.toString());
		}
			
		// if (pairCount == 1){
		result = runCheck(checkHand);
		System.out.println("Result: " + result);
		if (result == true) {
			System.out.println("yeah");
			return true;
		} 
		else{
			System.out.println("false");
			return false;
		}
	}
	
	// helper method for win() special win case(7 pairs)
		public static boolean check2(Hand activeHand, Tile t) {
			System.out.println("Check 2 executd");
			Hand checkHand = new Hand();
			int pairCount = 0;
			// make a copy of the activeHand
			for (int i = 0; i < activeHand.getActiveSize(); i++) {
				checkHand.add(activeHand.tileAt(i));
			}
			// add the tile into activeHand
			checkHand.add(t);
			System.out.print(checkHand.toString());
			System.out.println("Active size:" + checkHand.getActiveSize());
			
			// remove the pair
			for (int i = 0; i < checkHand.getActiveSize() - 1; i++) {
				if (checkHand.tileAt(i).getSuit() == checkHand.tileAt(i + 1).getSuit() && 
					checkHand.tileAt(i).getValue() == checkHand.tileAt(i + 1).getValue()) {
					System.out.println();
					// System.out.println("i: " + i);
					// System.out.println("i: " + (i+1));
					//System.out.println("Removed pair: " + checkHand.removeAt(i));
					//System.out.println("Removed pair: " + checkHand.removeAt(i));
					pairCount++;
				}
				System.out.println("Pair count: " + pairCount);
				System.out.println(checkHand.toString());
			}
			if (pairCount == 7) {
				System.out.println("yeah");
				return true;
			} else{
				System.out.println("false");
				return false;
			}
		}

	public static boolean runCheck(Hand activeHand) {
		System.out.println("Run Check: " + "\n" + activeHand.toString());
		int i = 0;
		if (activeHand.tileAt(i).getSuit() != activeHand.tileAt(i + 1).getSuit() || 
			activeHand.tileAt(i + 1).getSuit() != activeHand.tileAt(i + 2).getSuit() || 
			activeHand.tileAt(i).getValue() + 1 != activeHand.tileAt(i + 1).getValue() || 
			activeHand.tileAt(i + 1).getValue() + 1 != activeHand.tileAt(i + 2).getValue()) {
			System.out.println("dont continue");
			return false;
		} 
		else {
			System.out.println("Removed: " + activeHand.removeAt(i));
			System.out.println("Removed: " + activeHand.removeAt(i));
			System.out.println("Removed: " + activeHand.removeAt(i));
			if (activeHand.getActiveSize() >= 3){
				System.out.println("continue");
				return runCheck(activeHand);
			}
			else if (activeHand.getActiveSize() > 0 && activeHand.getActiveSize() < 3)
				return false;
		}
		return true;
	}

	public static boolean win(Hand activeHand, Tile t) {
		// winning hand pattern: 1, win on: 1
		if (activeHand.getActiveSize() == 1 && douWin(activeHand, t))
			return true;
		// winning hand pattern: 1 1 2 2, win on: 1 or 2
		else if (activeHand.getActiveSize() == 4 && check(activeHand, t)){
			System.out.println("You really win!");
			return true;
		}
		// winning hand pattern: 1 1 2 3 4 5 6, win on: 1 or 4 or 7
		else if (activeHand.getActiveSize() == 7 && check(activeHand, t) || 
				activeHand.getActiveSize() == 7 && check1(activeHand, t) ||
				activeHand.getActiveSize() == 7 && check2(activeHand, t)) {
			System.out.println("Executed");
			return true;
		}
		// winning hand pattern: 1 1 2 2 2 3 4 5 7 8, win on: 6 or 9
		else if (activeHand.getActiveSize() == 10 && check(activeHand, t) ||
				activeHand.getActiveSize() == 10 && check1(activeHand, t) ||
				activeHand.getActiveSize() == 10 && check2(activeHand, t)){
			System.out.println("Executed");
			return true;
		}
		// winning hand pattern: 1 2 3 4 5 6 7 8 9 2 2 2 3, win on: 1 or 3 or 4
		else if (activeHand.getActiveSize() == 13 && check(activeHand, t) ||
				activeHand.getActiveSize() == 13 && check1(activeHand, t) ||
				activeHand.getActiveSize() == 13 && check2(activeHand, t)){
			System.out.println("Executed");
			return true;
		}
		else
			return false;
	}

	public static boolean skip(Hand h) {

		return true;
	}

}