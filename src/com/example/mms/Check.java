package com.example.mms;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Check extends Activity{

	TextView Name,status,date,dis,time;
	Button next,prev,reject;
	Cursor res,res1,res2;
	String id;
	String dr_id;
	SQLiteDatabase db;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);
		
		id = getIntent().getStringExtra("id");
		
		Name = (TextView)findViewById(R.id.textView1);
		status = (TextView)findViewById(R.id.textView2);
		date = (TextView)findViewById(R.id.textView3);
		dis = (TextView)findViewById(R.id.textView4);
		time = (TextView)findViewById(R.id.textView5);
		next = (Button)findViewById(R.id.button1);
		prev = (Button)findViewById(R.id.button2);
		reject = (Button)findViewById(R.id.button3);
		
		db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);
		res = db.rawQuery("SELECT * FROM AppointmentDetails where Pat_id='"+id+"';", null);
		
		if(res.moveToFirst())
		{
			dr_id = res.getString(0);
			res1 = db.rawQuery("SELECT * FROM DoctorProfile WHERE Dr_id="+dr_id, null);
			res1.moveToFirst();
			Name.setText(res1.getString(3));
			status.setText(res.getString(2));
			date.setText(res.getString(3));
			dis.setText(res.getString(4));
			next.setVisibility(1);
		}
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(res.moveToNext())
				{
					dr_id = res.getString(0);
					res2 = db.rawQuery("SELECT * FROM DoctorProfile WHERE Dr_id="+dr_id, null);
					res2.moveToFirst();
					Name.setText(res2.getString(3));
					status.setText(res.getString(2));
					date.setText(res.getString(3));
					dis.setText(res.getString(4));
					prev.setVisibility(1);
				}
				else
				{
					Name.setText("No More Records");
					status.setText("");
					date.setText("");
					dis.setText("");
					time.setText("");
					prev.setVisibility(1);
				}
			}
		});

		prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(res.moveToPrevious())
				{
					dr_id = res.getString(0);
					res2 = db.rawQuery("SELECT * FROM DoctorProfile WHERE Dr_id="+dr_id, null);
					res2.moveToFirst();
					Name.setText(res2.getString(3));
					status.setText(res.getString(2));
					date.setText(res.getString(3));
					dis.setText(res.getString(4));
					prev.setVisibility(1);
				}
				else
				{
					Name.setText("No More Records");
					status.setText("");
					date.setText("");
					dis.setText("");
					time.setText("");
					next.setVisibility(1);
				}
			}
		});

		reject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(Check.this);

				// Setting Dialog Title
				alertDialog2.setTitle("Cancel Appointment...");

				// Setting Dialog Message
				alertDialog2.setMessage("Are you sure you want to Cancel this slot?");

				// Setting Positive "Yes" Btn
				alertDialog2.setPositiveButton("YES",
				        new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				                // Write your code here to execute after dialog
				            	db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);
				            	db.execSQL("DELETE FROM AppointmentDetails WHERE Date_Availability='"+date.getText().toString()+"';");
				                Toast.makeText(getApplicationContext(),"Appointment Cancelled", Toast.LENGTH_SHORT).show();
				            }
				        });
				// Setting Negative "NO" Btn
				alertDialog2.setNegativeButton("NO",
				        new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				                // Write your code here to execute after dialog
				                Toast.makeText(getApplicationContext(),"Appointment not cancelled", Toast.LENGTH_SHORT).show();
				                dialog.cancel();
				            }
				        });

				// Showing Alert Dialog
				alertDialog2.show();
			}
		});
	}
	
}
