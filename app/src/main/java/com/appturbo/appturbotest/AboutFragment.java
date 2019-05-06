package com.appturbo.appturbotest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;


public class AboutFragment extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_about, parent, false);

		//Initialise the textview with the result of the mydescription function
		TextView description = (TextView)v.findViewById(R.id.my_description);
		description.setText(getMyDescription("Takou Syntiche N'DONGO", 24, setDate(1, Calendar.SEPTEMBER, 2016), setDate(28, Calendar.FEBRUARY, 2017)));

		return v;
	}

	//Method that takes in parameters and formats and returns the description string
	private String getMyDescription(final String name, final int age, final Calendar startTime, final Calendar endTime) {
		/*
		 * TODO: Get the string and format it with the good parameter.
		 */

		String aboutMe = getString(R.string.my_description, name, age, startTime, endTime);
        return aboutMe;
	}

	//Method that set the calendard object for the dates
	public Calendar setDate(int day, int month, int year){
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}
}
