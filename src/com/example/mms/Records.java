package com.example.mms;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Records extends Activity{
	
	TextView dr_id,dr_name,special,app_date,dis_type,bp,sugar,pres;
	String id;
	Button next,prev;
	SQLiteDatabase db;
	Cursor res;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		dr_id = (TextView)findViewById(R.id.textView1);
		dr_name = (TextView)findViewById(R.id.textView2);
		special = (TextView)findViewById(R.id.textView3);
		app_date = (TextView)findViewById(R.id.textView4);
		dis_type = (TextView)findViewById(R.id.textView5);
		bp = (TextView)findViewById(R.id.textView6);
		sugar = (TextView)findViewById(R.id.textView7);
		pres = (TextView)findViewById(R.id.textView8);
		next = (Button)findViewById(R.id.button1);
		prev = (Button)findViewById(R.id.button2);
		
		id = getIntent().getStringExtra("id");
		
		db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS Records(Dr_id int,Pat_id int,Dr_name varchar(40),Dr_speciality varchar(40),Date_app varchar(40),Disease_type varchar(40),BP varchar(40),Sugar varchar(40),Prescription varchar(40));");
		res = db.rawQuery("SELECT * FROM Records WHERE Pat_id="+id, null);
		
		if(res.moveToFirst())
		{
			dr_id.setText("Doctor id: "+res.getString(0));
			dr_name.setText("Doctor Name: "+res.getString(2));
			special.setText("Doctor Speciality: "+res.getString(3));
			app_date.setText("Appointment Date: "+res.getString(4));
			dis_type.setText("Disease: "+res.getString(5));
			bp.setText("B.P.: "+res.getString(6));
			sugar.setText("Sugar: "+res.getString(7));
			pres.setText("Prescription: "+res.getString(8));
			next.setVisibility(1);
		}
		else
		{
			dr_id.setText("No Records Found");
		}
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(res.moveToNext())
				{
					Log.d(res.getString(2), "SUP");
					dr_id.setText("Doctor id: "+res.getString(0));
					dr_name.setText("Doctor Name: "+res.getString(2));
					special.setText("Doctor Speciality: "+res.getString(3));
					app_date.setText("Appointment Date: "+res.getString(4));
					dis_type.setText("Disease: "+res.getString(5));
					bp.setText("B.P.: "+res.getString(6));
					sugar.setText("Sugar: "+res.getString(7));
					pres.setText("Prescription: "+res.getString(8));
					prev.setVisibility(1);		
				}
				else
				{
					dr_id.setText("No More Records");
					dr_name.setText("");
					special.setText("");
					app_date.setText("");
					dis_type.setText("");
					bp.setText("");
					sugar.setText("");
					pres.setText("");
				}
			}
		});
		
		prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(res.moveToPrevious())
				{
					dr_id.setText("Doctor id: "+res.getString(0));
					dr_name.setText("Doctor Name: "+res.getString(2));
					special.setText("Doctor Speciality: "+res.getString(3));
					app_date.setText("Appointment Date: "+res.getString(4));
					dis_type.setText("Disease: "+res.getString(5));
					bp.setText("B.P.: "+res.getString(6));
					sugar.setText("Sugar: "+res.getString(7));
					pres.setText("Prescription: "+res.getString(8));
					prev.setVisibility(1);		
				}
				else
				{
					dr_id.setText("No More Records");
					dr_name.setText("");
					special.setText("");
					app_date.setText("");
					dis_type.setText("");
					bp.setText("");
					sugar.setText("");
					pres.setText("");
				}
			}
		});
	}

}
