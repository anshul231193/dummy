package com.example.mms;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends Activity{
	
	EditText f_name,b_date,add,con,email,uname,pswd;
	TextView label;
	Button btn;
	private int i=0;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.d("HI","HI");
		setContentView(R.layout.register);
		Log.d("SUP","SUP");
		btn = (Button)findViewById(R.id.button1);
		f_name = (EditText)findViewById(R.id.editText1);
		b_date = (EditText)findViewById(R.id.editText2);
		add = (EditText)findViewById(R.id.editText3);
		con = (EditText)findViewById(R.id.editText4);
		Log.d("B","B");
		email = (EditText)findViewById(R.id.editText5);
		uname = (EditText)findViewById(R.id.editText6);
		pswd = (EditText)findViewById(R.id.editText7);
		Log.d("A","A");
		label = (TextView)findViewById(R.id.textView8);
		btn.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SQLiteDatabase db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);;
				db.execSQL("CREATE TABLE IF NOT EXISTS Login(Patient_id INT AUTO_INCREMENT,Username VARCHAR,Password VARCHAR,F_Name VARCHAR,B_Date VARCHAR,Address VARCHAR,Contact_no VARCHAR,Email VARCHAR,Reg_Date VARCHAR,Login_Type VARCHAR);");
					if(validate(b_date.getText().toString(),con.getText().toString(),email.getText().toString(),f_name.getText().toString(),add.getText().toString(),uname.getText().toString(),pswd.getText().toString()) == true)
					{
						Log.d(""+i, "count");
						Date d = new Date();
						Log.d("A","A");
						SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
						String date = ft.format(d)+"";
						Log.d("B","B");
					    db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);
						Log.d("C","C");
						Cursor res = db.rawQuery("SELECT * FROM Login", null);
						while(res.moveToNext())
							i++;
						i++;
						String sql = "INSERT INTO Login VALUES('"+i+"','"+uname.getText().toString()+"','"+pswd.getText().toString()+"','"+f_name.getText().toString()+"','"+b_date.getText().toString()+"','"+add.getText().toString()+"','"+con.getText().toString()+"','"+email.getText().toString()+"','"+date+"','Patient');";
						Log.d("E","E");
						Log.d(sql, "QUERY");
						db.execSQL(sql);
						label.setText("Successfully Registered");
					}
					else
					{
						label.setText("Invalid Field/Username already taken");
					}
			}
		});
	}
	
	@SuppressLint("SimpleDateFormat")
	public boolean validate(String b,String c,String e,String f,String g,String h,String i)
	{
		int f1 = 0,f2 = 0,f3 = 0,f4 = 0;
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
		SQLiteDatabase db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);
		Cursor res = db.rawQuery("SELECT * FROM Login", null);
		if(res.moveToFirst())
		{
			do
			{
				String username = res.getString(1);
				if(username.equals(h))
				{
					f4 = 1;
					break;
				}
			}while(res.moveToNext());
		}
		if(f4 == 1 || f1 == 1 || f2 == 1 || f3 == 1 || f.equals("") || g.equals("") || h.equals("") || i.equals(""))
			return false;
		return true;
	}

}
