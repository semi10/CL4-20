package com.FSDragon.cl4_20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
        Button current_loop = (Button) findViewById(R.id.bt_current_loop_select);
        Button thermo = (Button) findViewById(R.id.bt_temperature_select);
		
        current_loop.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent current_loop_intent = new Intent("com.FSDragon.cl4_20.CURRENT_LOOP"); 
					startActivity(current_loop_intent);					
				}
	      });
        
        thermo.setOnClickListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View v) {
					Intent thermo_intent = new Intent("com.FSDragon.cl4_20.THERMO"); 
					startActivity(thermo_intent);					
				}
	      });
        
        
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
}
