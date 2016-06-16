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
	Button feedback,logout,update,records,book,check;
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
		records = (Button)findViewById(R.id.button6);
		book = (Button)findViewById(R.id.button4);
		check = (Button)findViewById(R.id.button5);
		
		check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),Check.class);
				i.putExtra("id", id);
				startActivity(i);
			}
		});
		
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
		
		records.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i2 = new Intent(getApplicationContext(),Records.class);
				i2.putExtra("id", id);
				startActivity(i2);
			}
		});
		
		book.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i3 = new Intent(getApplicationContext(),Book_app.class);
				i3.putExtra("id", id);
				startActivity(i3);
			}
		});
		
	}

}
