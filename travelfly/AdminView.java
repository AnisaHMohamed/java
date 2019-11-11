package com.example.travelfly;

import java.io.IOException;
import driver.Admin;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class AdminView extends Activity {

	/** The Admin who owns this view. */
	Admin admin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_view);
		
		Intent intent = getIntent();
		
		admin =  (Admin) LogIn.userManager.getUser((String)intent.getSerializableExtra(Keys.ADMIN_KEY));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_view, menu);
		return true;
	}

	public void uploadData(View view) {
		Intent intent = new Intent(this, UploadData.class);
		startActivity(intent);
	}
	
	public void displayFlightInfo(View view) {
		Intent intent = new Intent(this, DisplayFlights.class);
		startActivity(intent);
	}
	
	public void finish(View view) throws IOException {
		// Save data to files before loggin out.
		LogIn.flightManager.saveToFile(this.getApplicationContext().getFilesDir().getAbsolutePath() + "/flights.ser");
		LogIn.userManager.saveToFile(this.getApplicationContext().getFilesDir().getAbsolutePath() + "/users.ser");
		super.finish();
	}
}
