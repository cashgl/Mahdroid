//Goals: player discard, make functions work, get deck working, have evaluate work together
//
package com.example.mahdroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
        Button addButton = (Button) findViewById(R.id.addButton);
        suitField = (EditText) findViewById(R.id.suitField);
        valueField = (EditText) findViewById(R.id.valueField);
        
        addButton.setOnClickListener(new OnClickListener() {
			
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
		});
        
        hand = new Hand();
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
		Tile tile8 = new Tile(1, 2);
		hand.add(tile8);
		txt.append(hand.toString());
		
		
		
		if (Function.triple(hand))
			txt.append("true");
		else
			txt.append("false");
		txt.append("\n");

		hand.remove(tile3);
		if (Function.triple(hand))
			txt.append("true\n");
		else
			txt.append("false\n");
		txt.append("\n");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    
}
