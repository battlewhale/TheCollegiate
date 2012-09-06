package net.codequarry.thecollegiate;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class AddCourseActivity extends Activity
{
	LinearLayout layout;
	LayoutInflater layoutInflater;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        
        // Add initial blank class
        this.layout = (LinearLayout) findViewById(R.id.add_course_classes);
        this.layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout.addView(layoutInflater.inflate(R.layout.partial_class_time, null));
        
        // Add button listeners
        TimeOnClickListener listener = new TimeOnClickListener();
        
        Button startButton = (Button) findViewById(R.id.add_course_start);
        startButton.setOnFocusChangeListener(listener);
        startButton.setOnClickListener(listener);
        
        Button endButton = (Button) findViewById(R.id.add_course_end);
        endButton.setOnFocusChangeListener(listener);
        endButton.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_add_course, menu);
        return true;
    }
    
    public void addClass(View view)
    {
    	this.layout.addView(layoutInflater.inflate(R.layout.partial_class_time, null));
    }
    
    class TimeOnClickListener implements OnClickListener, OnFocusChangeListener
    {
    	public void launchDialog(View view)
    	{
    		DialogFragment newFragment = new TimePickerFragment(view);
            newFragment.show(getFragmentManager(), "timePicker");
    	}
    	
    	public void onClick(View view)
    	{
    		launchDialog(view);
    	}
    	
    	public void onFocusChange(View view, boolean hasFocus)
    	{
    		if(hasFocus) launchDialog(view);
    	}
    }
}
