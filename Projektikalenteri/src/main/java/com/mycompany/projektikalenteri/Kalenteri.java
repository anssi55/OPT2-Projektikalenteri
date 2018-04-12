package com.mycompany.projektikalenteri;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class Kalenteri {
	Date date;
	int year, month, day, dayOfWeek, dayOfMonth, weekOfYear, weekOfMonth;
	SimpleDateFormat sdf;
	Calendar calendar;
	
	



	public Kalenteri() {
		sdf = new SimpleDateFormat("yyyy M dd HH:mm:ss");	
		calendar = new GregorianCalendar();
		year       = calendar.get(Calendar.YEAR);
		month      = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); 
		dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
		weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		weekOfMonth= calendar.get(Calendar.WEEK_OF_MONTH);
	}
	public int getDayOfWeek() {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	public int getDayOfMonth() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	public int getFirstDayOfMonth() {
		calendar.set(calendar.DAY_OF_MONTH, 1);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	public int getMaxDaysInMonth() {
		return calendar.getActualMaximum(calendar.DAY_OF_MONTH);
	}

	public Date getDate() {
		System.out.println(sdf.format(calendar.getTime()));
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getMonth() {
		System.out.println(calendar.MONTH);
		return calendar.MONTH;
	}
	public String getMonthName() {
		switch(calendar.get(calendar.MONTH)) {
			case 0 :
				return "January";
			case 1 :
				return "February";
			case 2 :
				return "March";
			case 3 :
				return "April";
			case 4 :
				return "May";
			case 5 :
				return "June";
			case 6 :
				return "July";
			case 7 :
				return "August";
			case 8 :
				return "September";
			case 9 :
				return "October";
			case 10 :
				return "November";
			case 11 :
				return "December";
				
		} 
		return "asdf";
	}

	public void setMonthToNext() {
		calendar.add(calendar.MONTH, 1);
	}
	public void setMonthToPrevious() {
		calendar.add(calendar.MONTH, -1);
	}
}

