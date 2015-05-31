package com.FSDragon.cl4_20;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;


public class current_loop extends Activity{
	EditText min_x, curr_x, curr_mA, curr_p, max_x;
	TextView upper_values, lower_values;
	int convertionId = R.id.etCurr_x;
	boolean neg_corr =  false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_loop);
        
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        
        min_x = (EditText) findViewById(R.id.etMin_x);
        curr_x = (EditText) findViewById(R.id.etCurr_x);
        curr_mA = (EditText) findViewById(R.id.etCurr_mA);
        curr_p = (EditText) findViewById(R.id.etCurr_p);
        max_x = (EditText) findViewById(R.id.etMax_x);
        upper_values = (TextView) findViewById(R.id.tvupper_values);
        lower_values = (TextView) findViewById(R.id.tvlower_values);
        
        Button calculate = (Button) findViewById(R.id.bt_calculate);
        Button clear = (Button) findViewById(R.id.bt_clear);
        CheckBox correlation = (CheckBox) findViewById(R.id.checkBox1);
        
        
        curr_x.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				convertionId = R.id.etCurr_x;
				curr_x.setBackgroundColor(Color.GREEN);
				curr_x.setFocusableInTouchMode(true);
				curr_x.requestFocus();
				
				curr_mA.setBackgroundColor(Color.YELLOW);
				curr_mA.setFocusable(false);
				
				curr_p.setBackgroundColor(Color.YELLOW);
				curr_p.setFocusable(false);
			}
		});
        
        curr_mA.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				convertionId = R.id.etCurr_mA;
				curr_x.setBackgroundColor(Color.YELLOW);
				curr_x.setFocusable(false);
				
				curr_mA.setBackgroundColor(Color.GREEN);
				curr_mA.setFocusableInTouchMode(true);
				curr_mA.requestFocus();
				
				curr_p.setBackgroundColor(Color.YELLOW);
				curr_p.setFocusable(false);
			}
		});
        
        curr_p.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				convertionId = R.id.etCurr_p;
				curr_x.setBackgroundColor(Color.YELLOW);
				curr_x.setFocusable(false);
				
				curr_mA.setBackgroundColor(Color.YELLOW);
				curr_mA.setFocusable(false);
				
				curr_p.setBackgroundColor(Color.GREEN);
				curr_p.setFocusableInTouchMode(true);
				curr_p.requestFocus();
			}
		});
  
      correlation.setOnClickListener(new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (((CheckBox) v).isChecked()) {
				upper_values.setText("Max:");
				lower_values.setText("Min:");
				neg_corr = true;
			}
			else{  
				upper_values.setText("Min:");
				lower_values.setText("Max:");
				neg_corr = false; 
			}
		}
    	  
      });
        
      calculate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				double min_x_val;
			    double max_x_val;
			    
				if (min_x.getText().toString().isEmpty()){
					min_x.requestFocus();
					return;
				}
				
				if (max_x.getText().toString().isEmpty()) {
					max_x.requestFocus();
					return;
				}
				
				if (neg_corr){
					min_x_val = Double.valueOf(max_x.getText().toString());
				    max_x_val = Double.valueOf(min_x.getText().toString());					
				}
				else{
					min_x_val = Double.valueOf(min_x.getText().toString());
				    max_x_val = Double.valueOf(max_x.getText().toString());
				}				
				
				if (min_x_val == 10 && max_x_val == 10){
					Toast toast = Toast.makeText(current_loop.this, "Created By: FSDragon", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
					toast.show();
					return;
				}
				
				if (min_x_val >= max_x_val){
					Toast toast = Toast.makeText(current_loop.this, "Maximum X > Minimum X !", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
					toast.show();
					return;
				}
				
				switch (convertionId){
				
				case R.id.etCurr_x:
					if (curr_x.getText().toString().isEmpty()){
						curr_x.requestFocus();
						return;
					}
					
					double curr_x_val = Double.valueOf(curr_x.getText().toString());
					

					if(curr_x_val < min_x_val || curr_x_val > max_x_val ){
						Toast toast = Toast.makeText(current_loop.this, "Current X value outside maximum & minimum values!", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
						toast.show();
						return;
					}
					
					if (neg_corr){
						curr_mA.setText(String.format("%.2f",(20-(curr_x_val-min_x_val)*16/(max_x_val-min_x_val))));
						curr_p.setText(String.format("%.2f",(100-(curr_x_val-min_x_val)*100/(max_x_val-min_x_val))));							
					}
					else{
						curr_mA.setText(String.format("%.2f",(curr_x_val-min_x_val)*16/(max_x_val-min_x_val)+4));
						curr_p.setText(String.format("%.2f",(curr_x_val-min_x_val)*100/(max_x_val-min_x_val)));						
					}

					break;
					
				case R.id.etCurr_mA:
					if (curr_mA.getText().toString().isEmpty()) {
						curr_mA.requestFocus();
						return;
					}
					
					double curr_mA_val = Double.valueOf(curr_mA.getText().toString());
					
					if(curr_mA_val < 4.0 || curr_mA_val > 20.0 ){
						Toast.makeText(current_loop.this, "Current mA value must be between 4 & 20 mA !", Toast.LENGTH_LONG).show();
					    return;
					}
					
					curr_p.setText(String.format("%.2f",(curr_mA_val-4)*100/16));
					if(neg_corr){
						curr_x.setText(String.format("%.2f",(curr_mA_val-4)*(min_x_val-max_x_val)/16+max_x_val));
					}
					else{
						curr_x.setText(String.format("%.2f",(curr_mA_val-4)*(max_x_val-min_x_val)/16+min_x_val));
					}
					
					
					break;
					
				case R.id.etCurr_p:
					if (curr_p.getText().toString().isEmpty()) {
						curr_p.requestFocus();
						return;
					}
					
					double curr_p_val = Double.valueOf(curr_p.getText().toString());
					
					if(curr_p_val < 0.0 || curr_p_val > 100.0 ){
						Toast.makeText(current_loop.this, "Current % value must be between 0 & 100 % !", Toast.LENGTH_LONG).show();
					    return;
					}
					curr_mA.setText(String.format("%.2f",curr_p_val*16/100 +4));
					
					if (neg_corr){
						curr_x.setText(String.format("%.2f",((curr_p_val*(min_x_val-max_x_val)/100+max_x_val))));
					}
					else{
						curr_x.setText(String.format("%.2f",(curr_p_val*(max_x_val-min_x_val)/100+min_x_val)));
					}
					break;
				}
			}
		}); 
    
        clear.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		min_x.setText("");
        		curr_x.setText("");
        		curr_mA.setText("");
        		curr_p.setText("");
        		max_x.setText("");
        	}
		
	}); 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}