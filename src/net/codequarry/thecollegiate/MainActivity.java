package net.codequarry.thecollegiate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        GridView mainGrid = (GridView) findViewById(R.id.main_grid);
        mainGrid.setNumColumns(8); // One for times, seven for the days of the week
        
        mainGrid.setAdapter(new CalendarEventAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
