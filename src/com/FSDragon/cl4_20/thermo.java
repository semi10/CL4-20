package com.FSDragon.cl4_20;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class thermo extends Activity{
	EditText temperature, resistance;
	int pt_type = 100;
	
	int convertionId = R.id.etTemperature;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.thermo);
			
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
			
			temperature = (EditText) findViewById(R.id.etTemperature);
			resistance = (EditText) findViewById(R.id.etResistance);
	        Button clear = (Button) findViewById(R.id.bt_clear);
			
	        temperature.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					convertionId = R.id.etTemperature;
					temperature.setBackgroundColor(Color.GREEN);
					temperature.setFocusableInTouchMode(true);
					temperature.requestFocus();
					
					resistance.setBackgroundColor(Color.YELLOW);
					resistance.setFocusable(false);

				}
			});
	        
	        temperature.setOnKeyListener(new OnKeyListener(){

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
						ConvertToResistance();
						return true;
					}
					else{
						return false;
					}	
				}
		
	        	
	        });
	        
	        resistance.setOnClickListener(new View.OnClickListener() {
				
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						convertionId = R.id.etResistance;
						resistance.setBackgroundColor(Color.GREEN);
						resistance.setFocusableInTouchMode(true);
						resistance.requestFocus();
						
						temperature.setBackgroundColor(Color.YELLOW);
						temperature.setFocusable(false);

					}
			});
	        
	        resistance.setOnKeyListener(new OnKeyListener(){

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
						ConvertToTemperature();
						return true;
					}
					else{
						return false;
					}	
				}
	        });
	        
	        clear.setOnClickListener(new View.OnClickListener() {
	        	@Override
	        	public void onClick(View v) {
	        		// TODO Auto-generated method stub
	        		temperature.setText("");
	        		resistance.setText("");
	        	}
			
		}); 	                    
	        	       	        
		}		
		
	    public void select_pt(View view){
	    	RadioButton pt100 = (RadioButton) findViewById(R.id.rbt_pt100);
	    	RadioButton pt1000 = (RadioButton) findViewById(R.id.rbt_pt1000);
	    	if (pt100.isChecked()) pt_type = 100;
	    	else if (pt1000.isChecked()) pt_type = 1000;
	    };
			
        void ConvertToTemperature(){
            double Pt100[] = {80.31, 82.29,  84.27,	 86.25,	 88.22,	 90.19,	 92.16,	 94.12,	 96.09,	 98.04,
                    			100, 101.95, 103.9,	105.85,	107.79,	109.73,	111.67,	113.61,	115.54,	117.47,
                    	      119.4, 121.32, 123.24, 125.16, 127.07, 128.98, 130.89, 132.8,	134.7,	136.6,
                    		  138.5, 140.39, 142.29, 144.18, 146.07, 147.95, 149.83, 151.71, 153.58, 155.46,
                    		  157.33, 159.19, 161.05, 162.91, 164.77, 166.63, 168.48, 170.33, 172.17, 174.02,
                    		  175.86, 177.69, 179.53, 181.36, 183.19, 185.01, 186.84, 188.66, 190.47};
            double t = -50;
            int i = 0;
            int dt = 5;
            
            
            
				if (resistance.getText().toString().isEmpty()) {
					    Toast toast = Toast.makeText(thermo.this, "Enter Resistance value", Toast.LENGTH_LONG);
					    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
					    toast.show();
					    return;
					}

					double resistance_val = Double.valueOf(resistance.getText().toString());
				
					if (pt_type == 100 && (resistance_val < 80.31 || resistance_val > 190.46)){
					    Toast toast = Toast.makeText(thermo.this, "80.31 < Resistance < 190.46", Toast.LENGTH_LONG);
					    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
					    toast.show();
					    return;
					}
					else if (pt_type == 1000 && (resistance_val < 803.1 || resistance_val > 1904.6)){
					    Toast toast = Toast.makeText(thermo.this, "803.1 < Resistance < 1904.6", Toast.LENGTH_LONG);
					    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
					    toast.show();
					    return;
					}
					
					if(pt_type == 1000) resistance_val = resistance_val /10;

            if (resistance_val > Pt100[i = 0])
              while (250 > t) {
                if (resistance_val < Pt100[++i]){
                	 t = t + (resistance_val - Pt100[i-1]) * dt / (Pt100[i] - Pt100[i-1]);
                	 break;
                }
                else t += dt;
              };
         
              temperature.setText(String.format("%.2f",(t)));

        };
        
        void ConvertToResistance(){
            double Pt100[] = {-50.91,	-45.72, -40.67, -35.62,	-30.56,	-25.49,	-20.43,	-15.31,	-10.22,	-5.1,
                                   0, 5.13, 10.26, 15.38, 20.54, 25.69, 30.85, 36, 41.18, 46.37,
                               51.56, 56.76, 61.97, 67.18, 72.42, 77.66, 82.89, 88.13, 93.39, 98.66,
                              113.95, 109.23, 114.52, 119.86, 125.13, 130.45, 135.78, 141.11, 146.46, 151.81,
                              157.16, 162.54, 167.92, 173.3, 178.7, 184.11, 189.54, 194.94, 200.38, 205.84,
                              211.3, 216.76, 222.22, 227.7, 233.19, 238.3};
            
            double r = 80;
            int i = 0; 
            int dr = 2;
            
				if (temperature.getText().toString().isEmpty()) {
					    Toast toast = Toast.makeText(thermo.this, "Enter Temperature value", Toast.LENGTH_LONG);
					    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
					    toast.show();
					    return;
					}

					double temperature_val = Double.valueOf(temperature.getText().toString());
				
					if (pt_type == 100 && (temperature_val < -50.91 || temperature_val > 238.2)){
					    Toast toast = Toast.makeText(thermo.this, "-50.91 < Temperature < 238.2", Toast.LENGTH_LONG);
					    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
					    toast.show();
					    return;
					}
					
 
            if (temperature_val > Pt100[i = 0])
              while (238.3 > r) {
                if (temperature_val < Pt100[++i]){
                	r = r + (temperature_val - Pt100[i-1]) * dr / (Pt100[i] - Pt100[i-1]);
                	break;
                }
                else r += dr;
              };
         
              if (pt_type == 100) resistance.setText(String.format("%.2f",(r)));
              else if(pt_type == 1000) resistance.setText(String.format("%.1f",(r)*10));
        };
         
}
