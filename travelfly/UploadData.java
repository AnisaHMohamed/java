package com.example.travelfly;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UploadData extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upload_data, menu);
		return true;
	}
	
	/**
	 * Determines if the filename given by the Admin exists. If it does, the file will be
	 * sent to upload User data or Flight data depending on how the file is formatted.
	 * @param view
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public void uploadData(View view) throws IOException, NumberFormatException, ParseException {   // TODO Catch these exceptions in the back end perhaps.
		
		String filePath = LogIn.ABSOLUTE_PATH + "/" + ((EditText) findViewById(R.id.filename)).getText().toString();
		TextView message = (TextView) findViewById(R.id.message);

		Scanner scanner;
		String firstLine;
		try {
			scanner = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
	    	message.setText("That file does not exist.");
	    	return;
		}
		
		firstLine = scanner.nextLine();
		String[] firstLineArray = firstLine.split(",");
		
		if(firstLineArray.length == 6) {
			LogIn.userManager.uploadUserInfo(filePath);
	    	message.setText("User data has been uploaded.");
		} else if (firstLineArray.length == 7) {  // TODO <------------------------------ CHANGE THIS TO 8 WHEN WE UPDATE 'uploadFlightInfo'
			LogIn.flightManager.uploadFlightInfo(filePath);
	    	message.setText("Flight data has been uploaded.");
		} else {
	    	message.setText("That file is not in the correct format.");	
		}
		
		scanner.close();
		return;
	}
	
	public void finish(View view) {
		super.finish();
	}

}
