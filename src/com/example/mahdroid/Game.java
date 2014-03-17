//Goals: player discard, make functions work, have evaluate work together
//
package com.example.mahdroid;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Game extends Activity {
	
	EditText suitField, valueField;
	TextView txt;
	Hand hand;
	/////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        txt = (TextView) findViewById(R.id.textView1);
        txt.setMovementMethod(new ScrollingMovementMethod());
        //Button addButton = (Button) findViewById(R.id.addButton);
        //suitField = (EditText) findViewById(R.id.suitField);
        //valueField = (EditText) findViewById(R.id.valueField);
        
        /*addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(suitField.getText().length()==0){
                    suitField.setError("Field cannot be left blank.");
                }
				else if (valueField.getText().length()==0)
					valueField.setError("Field cannot be left blank.");
				else {
					int suit = Integer.parseInt(suitField.getText().toString());
					int value = Integer.parseInt(valueField.getText().toString());
					
					Tile temp = new Tile(suit,value);
					hand.add(temp);
					txt.setText("\n" + hand + "\n");
				}
			}
		});*/
        
        hand = new Hand();
		Tile tile111 = new Tile(1, 1);
		Tile tile112 = new Tile(3, 5);
		Tile tile113 = new Tile(1, 2);
		Tile tile14 = new Tile(2, 3);
		Tile tile15 = new Tile(1, 2);
		Tile tile16 = new Tile(2, 2);
		Tile tile17 = new Tile(2, 4);
		
		hand.add(tile111);
		hand.add(tile112);
		hand.add(tile113);
		hand.add(tile14);
		hand.add(tile15);
		hand.add(tile16);
		hand.add(tile17);
		Tile tile18 = new Tile(1, 2);
		hand.add(tile18);
		txt.append(hand.toString());
		
		

		Tile t = new Tile(1,2);
		if (Function.triple(hand, t))
			txt.append("true");
		else
			txt.append("false");
		txt.append("\n");

		hand.remove(tile113);
		if (Function.triple(hand, t))
			txt.append("true\n");
		else
			txt.append("false\n");
		txt.append("\n");
		
		Deck deck = new Deck();
		txt.append(deck.toString());
		
		ArrayList<Button> tilesInHand = new ArrayList<Button>();
		Button tile0 = (Button) findViewById(R.id.button0);
		tilesInHand.add(tile0);
		Button tile1 = (Button) findViewById(R.id.button1);
		tilesInHand.add(tile1);
		Button tile2 = (Button) findViewById(R.id.button2);
		tilesInHand.add(tile2);
		Button tile3 = (Button) findViewById(R.id.button3);
		tilesInHand.add(tile3);
		Button tile4 = (Button) findViewById(R.id.button4);
		tilesInHand.add(tile4);
		Button tile5 = (Button) findViewById(R.id.button5);
		tilesInHand.add(tile5);
		Button tile6 = (Button) findViewById(R.id.button6);
		tilesInHand.add(tile6);
		Button tile7 = (Button) findViewById(R.id.button7);
		tilesInHand.add(tile7);
		Button tile8 = (Button) findViewById(R.id.button8);
		tilesInHand.add(tile8);
		Button tile9 = (Button) findViewById(R.id.button9);
		tilesInHand.add(tile9);
		Button tile10 = (Button) findViewById(R.id.button10);
		tilesInHand.add(tile10);
		Button tile11 = (Button) findViewById(R.id.button11);
		tilesInHand.add(tile11);
		Button tile12 = (Button) findViewById(R.id.button12);
		tilesInHand.add(tile12);
		
		deck = new Deck();
		for (int i = 0; i<tilesInHand.size(); i++) {
			Tile ti = deck.draw();
			tilesInHand.get(i).setText(ti.getSuit() + "\n" + ti.getValue());
		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    
}