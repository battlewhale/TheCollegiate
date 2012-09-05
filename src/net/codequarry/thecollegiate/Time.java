package net.codequarry.thecollegiate;

public class Time {
	public int hour;
	public int minute;
	
	public Time()
	{
		this.hour = 0;
		this.minute = 0;
	}
	
	public Time(int hour, int minute)
	{
		this.hour = hour;
		this.minute = minute;
	}
	
	public Time(String time)
	{
		String parts[] = time.split(":");
		this.hour = Integer.parseInt(parts[0]);
		this.minute = Integer.parseInt(parts[1]);
	}
	
	@Override
	public String toString() // This can be expanded later to use settings or convert to 12hr time
	{
		String result = Integer.toString(this.hour) + ":";
		if(this.minute < 10) result += 0;
		result += Integer.toString(this.minute);
		
		return result;
	}
}
