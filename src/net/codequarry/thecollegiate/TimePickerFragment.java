package net.codequarry.thecollegiate;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	Button button;
	
	public TimePickerFragment(View view)
	{
		this.button = (Button) view;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int hour, minute;
		
		CharSequence currentSelection = button.getText();
		Pattern datePattern = Pattern.compile("([0-9]+):([0-9]+)(a|p)m");
		Matcher dateMatcher = datePattern.matcher(currentSelection);
		if(dateMatcher.matches())
		{
			// A date has already been selected so lets parse it
			hour = Integer.parseInt(dateMatcher.group(1));
			minute = Integer.parseInt(dateMatcher.group(2));
			if(dateMatcher.group(3).toString().equals("p"))	hour += 12;
		}
		else
		{
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			hour = c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);
		}
		
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
	}
	
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		String meridian;
		if(hourOfDay > 12)
		{
			meridian = "pm";
			hourOfDay -= 12;
		} else if(hourOfDay == 12)
		{
			meridian = "pm";
		}
		else
		{
			meridian = "am";
		}
		String buttonContents = hourOfDay + ":";
		if(minute < 10) buttonContents += "0";
		buttonContents += minute + meridian;
		
		button.setText(buttonContents);
	}
}