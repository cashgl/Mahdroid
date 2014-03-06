package com.example.mahdroid;

public class Function {

	public static boolean eat(Hand h){
		for (int i = 0; i< h.getSize() - 2; i++) {
			if (h.tileAt(i).getValue() == h.tileAt(i+1).getValue() && 
					h.tileAt(i+1).getValue() == h.tileAt(i+2).getValue())
				return true;
		}
		return false;
	}

	public static boolean dou(Hand h){
		for (int i = 0; i< h.getSize() - 2; i++) {
			if (h.tileAt(i).equals(h.tileAt(i+1)))
				return true;
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
		if (h.getSize() == 2 && dou(h))
			return true;
		else if (h.getSize() >= 5 && dou(h) && eat(h))
			return true;
		else if (h.getSize() >= 5 && dou(h) && triple(h))
			return true;
		else if (h.getSize() >= 8 && dou(h) && eat(h) && triple(h))
			return true;
		else 
			return false;
	}

	public static boolean skip(Hand h){

		return true;
	}

}
