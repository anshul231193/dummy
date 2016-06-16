package com.example.mms;

import java.text.DateFormat.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle.Control;

import com.example.mms.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SelectDoctor extends Activity{

	TextView Name,loc,special;
	Button next,prev;
	String tmp,start_time,day,id,date,dis,dr_id,spec;
	Cursor res,res1;
	SQLiteDatabase db;
	TextView[] slots = new TextView[20];
	int i;
	
	public String getWeek()
	{
		Date available = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			available = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//day of week
		Calendar c = Calendar.getInstance();
		c.setTime(available);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		Log.d(dayOfWeek+"","Day of week");
		if(dayOfWeek == 1)
			return "Sun";
		if(dayOfWeek == 2)
			return "Mon";
		if(dayOfWeek == 3)
			return "Tue";
		if(dayOfWeek == 4)
			return "Wed";
		if(dayOfWeek == 5)
			return "Thu";
		if(dayOfWeek == 6)
			return "Fri";
		if(dayOfWeek == 7)
			return "Sat";
		return "";
	}
	
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectdr);
		
		id = getIntent().getStringExtra("id");
		date = getIntent().getStringExtra("Date");
		dis = getIntent().getStringExtra("Disease");
		spec = getIntent().getStringExtra("special");
		
		Name = (TextView)findViewById(R.id.textView1);
		loc = (TextView)findViewById(R.id.textView2);
		special = (TextView)findViewById(R.id.textView5);
		
		for(int i=0;i<20;i++)
		{
			try
			{
				Class<id> clazz = R.id.class;
			    java.lang.reflect.Field f = clazz.getField("t" + Integer.toString(i+1));
			    int id = f.getInt(null);
				slots[i] = (TextView)findViewById(id);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		prev = (Button)findViewById(R.id.button1);
		next = (Button)findViewById(R.id.button2);
		day = getWeek();
	
		
		db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);
		res = db.rawQuery("SELECT * FROM DoctorProfile WHERE Date LIKE '%"+day+"%' AND upper(Address)='"+dis.toUpperCase()+"' AND upper(Dr_Speciality)='"+spec.toUpperCase()+"';",null);
		
		String dr_day,dr_time,temp;
		String[] str,ctr,time_app;
		if(res.moveToFirst())
		{
				int j;
				for(int k=0;k<20;k++)
					slots[k].setText("");
				dr_id = res.getString(0);
				Name.setText(res.getString(3));
				loc.setText(res.getString(4));
				special.setText(res.getString(9));
				dr_day = res.getString(10);
				str = dr_day.split(" ");
				for(j=0;j<str.length;j++)
					if(str[j].equals(day))
						break;
				dr_time = res.getString(11);
				ctr = dr_time.split(" ");
				temp = ctr[j];
				time_app = temp.split("-");
				int diff = 2*(Integer.parseInt(time_app[1])-Integer.parseInt(time_app[0]));
				int sum = Integer.parseInt(time_app[0]);
				tmp = start_time;
				start_time = time_app[0];
				Log.d(start_time+"","time");
				for(int k=0;k<diff;k++)
				{
					Log.d(start_time,"");
					slots[k].setText(start_time+"");
					if(k%2 == 0)
						start_time += ".30";
					else
					{
						sum += 1;
						start_time = Integer.toString(sum);
					}
				}
				res1 = db.rawQuery("SELECT * FROM AppointmentDetails WHERE Date_Availability LIKE '%"+date+"%' AND Dr_id='"+dr_id+"';", null);
				if(res1.moveToFirst())
				{	
					do
					{
						String timing_app = res1.getString(3);
						String[] split_time = timing_app.split(" ");
						String time_avail = split_time[1];
						
						for(int k=0;k<20;k++)
						{
							if(slots[k].getText().toString().equals(time_avail))
								slots[k].setVisibility(TRIM_MEMORY_UI_HIDDEN);
						}
					}while(res1.moveToNext());
				}
				
				next.setVisibility(1);
		}
		else
		{
			Name.setText("Sorry,No Doctor Available today");
			loc.setText("");
			special.setText("");
			for(int k=0;k<20;k++)
				slots[k].setText("");
		}
	
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(res.moveToNext())
				{
					displayNext();
				}
				else
				{
					Name.setText("No More Doctors");
					loc.setText("");
					special.setText("");
					next.setVisibility(0);
					for(int k=0;k<20;k++)
						slots[k].setVisibility(TRIM_MEMORY_UI_HIDDEN);
					prev.setVisibility(1);
				}
			}
		});
		
		prev.setOnClickListener(new OnClickListener() {
			
			String dr_day,dr_time,temp;
			String[] str,ctr,time_app;
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(res.moveToPrevious())
				{
					displayPrev();
				}
				else
				{
					Name.setText("No More Doctors");
					loc.setText("");
					special.setText("");
					for(int k=0;k<20;k++)
						slots[k].setVisibility(TRIM_MEMORY_UI_HIDDEN);
					next.setVisibility(1);
					prev.setVisibility(0);
				}
			}
		});
	}
		
	private void displayNext()
	{
		String dr_day,dr_time,temp;
		String[] str,ctr,time_app;
		
		int j;
		for(int k=0;k<20;k++)
		{
			slots[k].setVisibility(1);
			slots[k].setText("");
		}
		dr_id = res.getString(0);
		Name.setText(res.getString(3));
		loc.setText(res.getString(4));
		special.setText(res.getString(9));
		dr_day = res.getString(10);
		str = dr_day.split(" ");
		for(j=0;j<str.length;j++)
			if(str[j].equals(day))
				break;
		dr_time = res.getString(11);
		ctr = dr_time.split(" ");
		temp = ctr[j];
		time_app = temp.split("-");
		int diff = 2*(Integer.parseInt(time_app[1])-Integer.parseInt(time_app[0]));
		int sum = Integer.parseInt(time_app[0]);
		String start_time = time_app[0];
		Log.d(start_time+"","time");
		for(int k=0;k<diff;k++)
		{
			slots[k].setText(start_time+"");
			if(k%2 == 0)
				start_time += ".30";
			else
			{
				sum += 1;
				start_time = Integer.toString(sum);
			}
		}
		res1 = db.rawQuery("SELECT * FROM AppointmentDetails WHERE Date_Availability LIKE '%"+date+"%' AND Dr_id='"+dr_id+"';", null);
		if(res1.moveToFirst())
		{	
			do
			{
				String timing_app = res1.getString(3);
				String[] split_time = timing_app.split(" ");
				String time_avail = split_time[1];
				
				for(int k=0;k<20;k++)
				{
					Log.d(slots[k].getText().toString(),""+k);
					if(slots[k].getText().toString().equals(time_avail))
						slots[k].setVisibility(TRIM_MEMORY_UI_HIDDEN);
				}
			}while(res1.moveToNext());
		}	
		prev.setVisibility(1);
	}
	
	private void displayPrev()
	{
		String dr_day,dr_time,temp;
		String[] str,ctr,time_app;

		for(int k=0;k<20;k++)
		{
			slots[k].setVisibility(1);
			slots[k].setText("");
		}
		int j;
		dr_id = res.getString(0);
		Name.setText(res.getString(3));
		loc.setText(res.getString(4));
		special.setText(res.getString(9));
		dr_day = res.getString(10);
		str = dr_day.split(" ");
		for(j=0;j<str.length;j++)
			if(str[j].equals(day))
				break;
		dr_time = res.getString(11);
		ctr = dr_time.split(" ");
		temp = ctr[j];
		time_app = temp.split("-");
		int diff = 2*(Integer.parseInt(time_app[1])-Integer.parseInt(time_app[0]));
		int sum = Integer.parseInt(time_app[0]);
		String start_time = time_app[0];
		Log.d(start_time+"","time");
		for(int k=0;k<diff;k++)
		{
			slots[k].setText(start_time+"");
			if(k%2 == 0)
				start_time += ".30";
			else
			{
				sum += 1;
				start_time = Integer.toString(sum);
			}
		}
		res1 = db.rawQuery("SELECT * FROM AppointmentDetails WHERE Date_Availability LIKE '%"+date+"%' AND Dr_id='"+dr_id+"';", null);
		if(res1.moveToFirst())
		{	
			do
			{
				String timing_app = res1.getString(3);
				String[] split_time = timing_app.split(" ");
				String time_avail = split_time[1];
				
				for(int k=0;k<20;k++)
				{
					Log.d(slots[k].getText().toString(),""+k);
					if(slots[k].getText().toString().equals(time_avail))
						slots[k].setVisibility(TRIM_MEMORY_UI_HIDDEN);
				}
			}while(res1.moveToPrevious());
		}
		next.setVisibility(1);
	}
	
	public void pickSlot(View view)
	{
		for(i=0;i<20;i++)
			if(slots[i].getId() == view.getId())
				break;

		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(SelectDoctor.this);

		// Setting Dialog Title
		alertDialog2.setTitle("Confirm Appointment...");

		// Setting Dialog Message
		alertDialog2.setMessage("Are you sure you want to book this slot?");

		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("YES",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		                // Write your code here to execute after dialog
		            	db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);
		            	Log.d(start_time+tmp,"");
		            	db.execSQL("INSERT INTO AppointmentDetails VALUES('"+dr_id+"','"+id+"','Approved','"+date+" "+slots[i].getText().toString()+"','"+dis+"');");
		                Toast.makeText(getApplicationContext(),"Appointment Confirmed", Toast.LENGTH_SHORT).show();
		            }
		        });
		// Setting Negative "NO" Btn
		alertDialog2.setNegativeButton("NO",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		                // Write your code here to execute after dialog
		            	Log.d(start_time+tmp,"");
		                Toast.makeText(getApplicationContext(),"Choose a slot", Toast.LENGTH_SHORT).show();
		                dialog.cancel();
		            }
		        });

		// Showing Alert Dialog
		alertDialog2.show();
	}

}
