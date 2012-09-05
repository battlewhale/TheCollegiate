package net.codequarry.thecollegiate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class HourAdapter extends BaseAdapter {
	
	private Context context;
	
	public HourAdapter(Context context)
	{
		this.context = context;
	}
	
	public int getCount()
	{
		return 12;
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
			//text.setLayoutParams(new GridView.LayoutParams(85, 85));
		}
		else
		{
			text = (TextView) convertView;
		}
		
		text.setText(Integer.toString(position));
		
		return text;
	}
	
}
