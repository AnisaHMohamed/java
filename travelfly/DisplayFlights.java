package com.example.travelfly;

import java.util.ArrayList;
import java.util.Arrays;

import driver.Flight;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayFlights extends Activity {

	private ListView flightList ;  
	private ArrayAdapter<String> listAdapter ;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_flights);
		
		// Find the ListView resource.   
	    flightList = (ListView) findViewById( R.id.flightsList );  
	  
	    ArrayList<String> flightInfo = new ArrayList<String>();
	    
	    for(Flight flight : LogIn.flightManager.getFlights()) {
	    	flightInfo.add(flight.toStringListView());
	    }
	      
	    if(flightInfo.size() == 0)
	    	flightInfo.add("No flight data exists.");
	    
	    // Sorts the list by Airline, then flight number.
	    java.util.Collections.sort(flightInfo);
	    
	    // Create ArrayAdapter using the list of Flights.
	    listAdapter = new ArrayAdapter<String>(this, R.layout.flight_row, flightInfo);  
	      
	    // Set the ArrayAdapter as the ListView's adapter.  
	    flightList.setAdapter( listAdapter );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_flights, menu);
		return true;
	}

	public void finish(View view) {
		super.finish();
	}
}
