package com.example.travelfly;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import driver.Admin;
import driver.User;
import driver.UserManager;
import driver.FlightManager;

public class LogIn extends Activity {
	
	/*
	 * IMPORTANT NOTE:
	 * 
	 * 	   UserManager and FlightManager will not be passed between activities with an intent!
	 *     Instead, use:   
	 *     
	 *     		--> LogIn.userManager  &  LogIn.flightManager <--
	 *     
	 *     to access them from other activities.
	 */
	public static UserManager userManager;
	public static FlightManager flightManager;
	
	
	public static String ABSOLUTE_PATH;// = this.getApplicationContext().getFilesDir().getAbsolutePath();
	private ArrayList<String> passwords;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		
		// Paths to the data stored on the device.
		ABSOLUTE_PATH = this.getApplicationContext().getFilesDir().getAbsolutePath();
		String serializedUsers = ABSOLUTE_PATH + "/users.ser";
		String serializedFlights = ABSOLUTE_PATH + "/flights.ser";
		String csvPasswords = ABSOLUTE_PATH + "/passwords.txt";
		
		// Create User/Flight Manager.
		userManager = new UserManager(serializedUsers);
		flightManager = new FlightManager(serializedFlights);
		
		// Stores usernames and passwords.
		passwords = new ArrayList<String>();
		Scanner passwordsFile;
		String nextLine;
		try {
			passwordsFile = new Scanner(new File(csvPasswords));
			while(passwordsFile.hasNext()) {
				nextLine = passwordsFile.nextLine();
				passwords.add(nextLine); }
			// Close the scanner.
			passwordsFile.close();
		} catch (FileNotFoundException e) {
	        System.out.println("FILE: \"passwords.txt\" does not exist in application directory");
			e.printStackTrace(); }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}

	/**
	 * Handles logging in a client or admin.
	 * @param view
	 * @throws FileNotFoundException 
	 */
	public void login(View view) throws FileNotFoundException {
		
		// Gets the data from the EditText field.
		EditText userName = (EditText) findViewById(R.id.username);
		EditText passWord = (EditText) findViewById(R.id.password);
		String username = userName.getText().toString();
		String UserPassPair = username + "," + passWord.getText().toString();

		if(passwords.contains(UserPassPair + ",admin") || passwords.contains(UserPassPair + ",client")) {
		
			Intent intent;
			
			// Check if the Admin already exists in userManager.
			if(userManager.contains(username)) {
				User user = userManager.getUser(username);
				
				if (user instanceof Admin) {
					intent = new Intent(this, AdminView.class);
					intent.putExtra(Keys.ADMIN_KEY, username);
				} else {
					intent = new Intent(this, UserView.class);
					intent.putExtra(Keys.CLIENT_KEY, username); }
			
				
			// If not, create it. (Only Admins can be created this way)
			} else {
				
				if(passwords.contains(UserPassPair + ",admin")) {
					intent = new Intent(this, AdminView.class);
					Admin admin = new Admin(username);
					userManager.addUser(username, admin);
			
					intent.putExtra(Keys.ADMIN_KEY, username); }
				else {
					intent = null;
					TextView error = (TextView) findViewById(R.id.error_message);
			    	error.setText("Client has not been created by an Admin.");
			    	return;
				}
			}
	
			// Reset the error message.
			TextView error = (TextView) findViewById(R.id.error_message);
			error.setText("");
			
			// Start the next Activity.
			startActivity(intent);
			
			return;

		// User doesn't exist, print error.
	    } else {
		    	
	    	TextView error = (TextView) findViewById(R.id.error_message);
	    	error.setText("Username or Password is incorrect.");
	    }
	}
}
