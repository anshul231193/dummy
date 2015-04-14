package com.example.mms;

import android.support.v7.app.ActionBarActivity;
import android.database.Cursor;
import android.database.sqlite.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	EditText uname,pswd;
	TextView error;
	Button btn,reg;
	
	public void onBackPressed() {
		   Intent intent = new Intent(Intent.ACTION_MAIN);
		   intent.addCategory(Intent.CATEGORY_HOME);
		   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   startActivity(intent);
		 }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		uname = (EditText)findViewById(R.id.editText1);
		pswd = (EditText)findViewById(R.id.editText2);
		error = (TextView)findViewById(R.id.textView3);
		btn = (Button)findViewById(R.id.button1);
		reg = (Button)findViewById(R.id.button2);
		
		btn.setOnClickListener(new OnClickListener() {
			
			private int flag = 0;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				SQLiteDatabase db = openOrCreateDatabase("MMS", MODE_PRIVATE, null);
				Cursor res = db.rawQuery("SELECT * FROM Login", null);
				Log.d(res+"","event");
				if(res.moveToFirst())
				{
					do
					{
						String id = res.getString(0);
						String username = res.getString(1);
						String pass = res.getString(2);
						String type = res.getString(9);
						String fname = res.getString(3);
						if(uname.getText().toString().equals(username) && pswd.getText().toString().equals(pass) && type.equals("Patient")) 
						{
							Intent i = new Intent(getApplicationContext(), Patient.class);
							i.putExtra("name", fname);
							i.putExtra("id", id);
							startActivity(i);
							flag = 1;
							break;
						}
						else if(uname.getText().toString().equals(username) && pswd.getText().toString().equals(pass) && type.equals("Doctor")) 
						{
							break;
						}
						else if(uname.getText().toString().equals("admin") && pswd.getText().toString().equals("admin")) 
						{
							break;
						}
					}while(res.moveToNext());
				}
				if(flag == 0)
				{
					error.setText("Invalid Username or password");
				}
			}
		});
		
		reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i_reg = new Intent(getApplicationContext(), Register.class);
				
				startActivity(i_reg);
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
