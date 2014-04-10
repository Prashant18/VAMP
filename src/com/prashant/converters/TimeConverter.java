package com.prashant.converters;

public class TimeConverter {
	
	public TimeConverter(){}
	
	public String milliTofull(String duration)
	{
		Long time_duration=Long.parseLong(duration);
		String Seconds= String.valueOf((time_duration%60000)/1000);
    	String Minutes=String.valueOf(time_duration/60000);
		String FIN;
		if(Seconds.length()==1)
    	{
    		FIN="0"+Minutes+":0"+Seconds;
    	}else
    	{
    		FIN="0"+Minutes+":"+Seconds;
    	}
		return FIN;	
	}
}
