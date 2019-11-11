package com.example.travelfly;

import driver.Flight;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FlightEdit extends Activity {

	private Flight flight;
	private String flightString;
	private boolean newFlight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight_edit);
		
		Intent intent = getIntent();
		flightString = (String) intent.getSerializableExtra(Keys.FLIGHT_KEY);
		newFlight = (flightString == null);
		
		// If the admin is editing an existing flight, this code sets the hints in the EditText fields to the flight data.
		if(!newFlight) {
			flight = LogIn.flightManager.getFlight(flightString);
			((EditText) findViewById(R.id.airlineText)).setHint(flight.getAirline());
			((EditText) findViewById(R.id.flightNumberText)).setHint(flight.getFlightNumber());
			((EditText) findViewById(R.id.originText)).setHint(flight.getOrigin());
			((EditText) findViewById(R.id.destinationText)).setHint(flight.getDestination());
			String[] dates = flight.getDepAndArrAsString().split(" ");
			((EditText) findViewById(R.id.departureDateText)).setHint(dates[0]);
			((EditText) findViewById(R.id.departureTimeText)).setHint(dates[1]);
			((EditText) findViewById(R.id.arrivalDateText)).setHint(dates[2]);
			((EditText) findViewById(R.id.arrivalTimeText)).setHint(dates[3]);
			((EditText) findViewById(R.id.costText)).setHint(Flight.df.format(flight.getCost()));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flight_edit, menu);
		return true;
	}

	public void editFlightInfo(View view) {
		if(newFlight) {
			
		}
	}
}
