package com.example.mms;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Update extends Activity{
	
	EditText f_name,b_date,add,con,email;
	TextView label;
	Button update;
	String id;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		Intent i = getIntent();
		id = i.getStringExtra("id");
		SQLiteDatabase db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);
		Cursor res = db.rawQuery("SELECT * FROM Login WHERE Patient_id = "+id, null);
		Log.d("A", "A");
		f_name = (EditText)findViewById(R.id.editText1);
		b_date = (EditText)findViewById(R.id.editText2);
		add = (EditText)findViewById(R.id.editText3);
		con = (EditText)findViewById(R.id.editText4);
		email = (EditText)findViewById(R.id.editText5);
		label = (TextView)findViewById(R.id.textView6);
		Log.d("B", "B");
		if(res.moveToFirst())
		{
			f_name.setText(res.getString(3));
			b_date.setText(res.getString(4));
			add.setText(res.getString(5));
			con.setText(res.getString(6));
			email.setText(res.getString(7));
		}
		update = (Button)findViewById(R.id.button1);
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(validate(b_date.getText().toString(),con.getText().toString(),email.getText().toString(),f_name.getText().toString(),add.getText().toString()) == true)
				{
					Date d = new Date();
					Log.d("A","A");
					SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
					String date = ft.format(d)+"";
					Log.d("B","B");
					SQLiteDatabase db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);
					Log.d("C","C");
					String sql = "UPDATE Login SET Contact_no='"+con.getText().toString()+"', F_Name='"+f_name.getText().toString()+"', B_Date ='"+b_date.getText().toString()+"',Address='"+add.getText().toString()+"'  WHERE Patient_id ="+id+";";
					Log.d(con.getText().toString(),"Contact_no");
					Log.d(sql, "QUERY");
					db.execSQL(sql);
					label.setText("Successfully Updated");
				}
				else
				{
					label.setText("Invalid Field");
				}
			}
		});
	}
	
	public boolean validate(String b,String c,String e,String f,String g)
	{
		int f1 = 0,f2 = 0,f3 = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			 
			//if not valid, it will throw ParseException
			Date date = sdf.parse(b);
			System.out.println(date);
 
		} catch (ParseException e1) {
 
			e1.printStackTrace();
			f1 = 1;
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			f1 = 1;
		}
		
		if(c.length() != 10)
			f2 = 1;
		
		for(int i1=0;i1<c.length();i1++)
		{
			if(c.charAt(i1)<'0' && c.charAt(i1)>'9')
			{
				f2 = 1;
				break;
			}
		}
		
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Boolean b1 = e.matches(EMAIL_REGEX);
		if(b1 == false)
			f3 = 1;
		if(f1 == 1 || f2 == 1 || f3 == 1 || f.equals("") || g.equals(""))
			return false;
		return true;
	}
}
