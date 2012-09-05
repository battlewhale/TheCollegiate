package net.codequarry.thecollegiate;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class AddCourseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        
        // Add initial blank class
        LinearLayout layout = (LinearLayout) findViewById(R.id.add_course_classes);
        LayoutInflater layoutInflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout.addView(layoutInflator.inflate(R.layout.partial_class_time, null));
        
        // Populate spinners
        //Spinner startHourSpinner = (Spinner) findViewById(R.id.add_course_start_hour);
        //startHourSpinner.setAdapter(new HourAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_course, menu);
        return true;
    }
    
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
}
