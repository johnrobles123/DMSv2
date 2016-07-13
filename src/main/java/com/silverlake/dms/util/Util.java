package com.silverlake.dms.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {
	public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
                 
        return cal.getTime();
    }

	@SuppressWarnings("deprecation")
	public static Date getNextDate(String repeat, Date startDate)
	{	
	 	Date date = startDate;
	     
	 	System.out.println(startDate.getDay()+"day");
		if (repeat.equals("Daily"))
		{
			
			if (startDate.getDay() == 5)
			{	 date = addDays(startDate, 3);
			}else if (startDate.getDay() == 6)
			{
				 date= addDays(startDate, 2);
			}else
			{
				date= addDays(startDate, 1);
			}
		}
		else if (repeat.equals("Weekly"))
		{	
			date = addDays(startDate,7);
		} 
		else
		{
			 date = addDays(date, 1);
		}
		System.out.println(date+"date");
		return date;
	}
	 
	public static Time getTimeFormat(String date){
		DateFormat df = new SimpleDateFormat("hh:mm a");
		@SuppressWarnings("deprecation")
		Date retDate = new Date(12, 30, 2000);
		Time ppstime = new Time(retDate.getTime());
		try {
			retDate = (Date) df.parse(date);
			
			ppstime = new java.sql.Time(retDate.getTime());
		}catch (ParseException e) {
	    }
		
		return ppstime;
	}
 
	public static Date getDateFormat(String date){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		@SuppressWarnings("deprecation")
		Date retDate = new Date(12, 30, 2000);
		try {
			retDate = (Date) df.parse(date);
			
		}catch (ParseException e) {
	    }
		
		return retDate;
	}
}
