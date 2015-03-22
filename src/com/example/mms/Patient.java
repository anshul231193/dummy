package com.example.mms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Patient extends Activity{
	
	TextView label;
	Button feedback,logout,update;
	String id;
	
	public void onBackPressed()
	{																																																																																																																																																																																			
	}
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patient);
		
		label = (TextView)findViewById(R.id.textView1);
		
		Intent i = getIntent();
		String name = i.getStringExtra("name");
		id = i.getStringExtra("id");
		
		label.setText("Welcome "+name);
		
		feedback = (Button)findViewById(R.id.button1);
		logout = (Button)findViewById(R.id.button2);
		update = (Button)findViewById(R.id.button3);
		
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(i);
			}
		});
		//Feedback to be done later
		feedback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), Feedback.class);
				startActivity(i);
			}
		});
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i1 = new Intent(getApplicationContext(), Update.class);
				i1.putExtra("id", id);
				Log.d(id+"", "counter");
				startActivity(i1);
			}
		});
	}

}
