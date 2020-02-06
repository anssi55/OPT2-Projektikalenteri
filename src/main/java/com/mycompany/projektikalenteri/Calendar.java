package com.mycompany.projektikalenteri;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.LocalDate;

public class Calendar {
	private ResourceBundle messages;
	private java.time.LocalDate date;

	public Calendar(ResourceBundle messages) {
		this.messages = messages;
		this.date = LocalDate.now();
	}

	public int getDayOfWeek() {
		return date.getDayOfWeek();
	}

	public int getDayOfMonth() {
		return date.getDayOfMonth();
	}

	public int getFirstDayOfMonth() {
		return date.withDayOfMonth(1);
	}

	public int getMaxDaysInMonth() {
		return date.plusDays(daysToAdd);
	}

	public Date getDate() {
		return this.date;
	}

	public int getMonth() {
		return this.date.getMonth();
	}

	public String getMonthName() {
		switch (this.date.getMonth()) {
		case 0:
			return messages.getString("january");
		case 1:
			return messages.getString("february");
		case 2:
			return messages.getString("march");
		case 3:
			return messages.getString("april");
		case 4:
			return messages.getString("may");
		case 5:
			return messages.getString("june");
		case 6:
			return messages.getString("july");
		case 7:
			return messages.getString("august");
		case 8:
			return messages.getString("september");
		case 9:
			return messages.getString("october");
		case 10:
			return messages.getString("november");
		case 11:
			return messages.getString("december");

		}
		return "";
	}

	public int getYear() {
		return this.date.getYear();
	}

	public void setDate(LocalDate newDate) {
		this.date = newDate;
	}

	public void setToCurrentDate() {
		this.date = LocalDate.now();
	}

	public void setMonthToNext() {
		this.date.plusMonths(1);
	}

	public void setMonthToPrevious() {
		this.date.minusMonths(1);
	}

}
