package com.example.mahdroid;

public class Function {

	public static boolean eat(Hand h){
		
		return true;
	}
	
	public static boolean dou(Hand h){
		int i = 0;
		boolean canDouble = false;
		while (i < h.getSize() - 1 && !canDouble && h.getSize() > 1) {
			if (h.tileAt(i).equals(h.tileAt(i+1)))
				return true;
			i++;
		}
		return false;
	}
	
	public static boolean triple(Hand h){
		for (int i = 0; i< h.getSize() - 2; i++) {
			if (h.tileAt(i).equals(h.tileAt(i+2)))
					return true;
		}
		return false;
	}
	
	public static boolean win(Hand h){
		
		
		return true;
	}
	
	public static boolean skip(Hand h){
		
		return true;
	}
	
}
