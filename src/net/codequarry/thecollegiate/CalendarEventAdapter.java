package net.codequarry.thecollegiate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class CalendarEventAdapter extends BaseAdapter {
	
	private Context context;
	private int hour = 8;
	private int minute = 0;
	
	public CalendarEventAdapter(Context context)
	{
		this.context = context;
	}
	
	public int getCount()
	{
		return 120;
	}
	
	public Object getItem(int position)
	{
		return null;
	}
	
	public long getItemId(int position)
	{
		return 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TextView text;
		if(convertView == null)
		{
			text = new TextView(context);
			text.setLayoutParams(new GridView.LayoutParams(85, 85));
		}
		else
		{
			text = (TextView) convertView;
		}
		
		if(position % 8 == 0)
		{
			Time currentTime = new Time(hour, minute);
			text.setText(currentTime.toString());
			minute += 30;
			if(minute == 60)
			{
				hour++;
				minute = 0;
			}
		}
		else text.setText("test");
		
		return text;
	}
}