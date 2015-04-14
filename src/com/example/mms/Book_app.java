package com.example.mms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Book_app extends Activity{
	
	EditText date,dis,special;
	Button btn;
	String id;
	TextView label;
	
	@SuppressLint("SimpleDateFormat")
	public boolean validate(String b,String s,String loc)
	{
		int f1 = 0;
		if(b.equals("") || s.equals("") || loc.equals(""))
			f1 = 1; 
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(date);
		Log.d(today+"&"+b,"SUP");
		
		try {
			Date curr = formatter.parse(today);
			Date app = formatter.parse(b);
			int comparison = curr.compareTo(app);
			if(comparison > 0)
				f1 = 1;
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			f1 = 1;
			e.printStackTrace();
		}
		
		if(f1 == 0)
			return true;
		else
			return false;

	}
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookapp);
		
		special = (EditText)findViewById(R.id.editText3);
		date = (EditText)findViewById(R.id.editText1);
		dis = (EditText)findViewById(R.id.editText2);
		btn = (Button)findViewById(R.id.button1);
		label = (TextView)findViewById(R.id.textView3);
		
		id = getIntent().getStringExtra("id");
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(validate(date.getText().toString(),special.getText().toString(),dis.getText().toString()))
				{
					Intent i = new Intent(getApplicationContext(), SelectDoctor.class);
					i.putExtra("id", id);
					i.putExtra("Date", date.getText().toString());
					i.putExtra("Disease", dis.getText().toString());
					i.putExtra("special", special.getText().toString());
					startActivity(i);
					label.setText("Select Doctor");
				}
				else
					label.setText("Invalid Date/Required fields");
			}
		});
	}

}
