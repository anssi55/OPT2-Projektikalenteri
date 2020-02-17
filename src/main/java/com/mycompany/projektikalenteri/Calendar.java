package com.mycompany.projektikalenteri;

import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.Month;
import java.time.DayOfWeek;

public class Calendar {
	private ResourceBundle messages;
	private LocalDate date;

	public Calendar(ResourceBundle messages) {
		this.messages = messages;
		this.date = LocalDate.now();
	}

	public DayOfWeek getDayOfWeek() {
		return this.date.getDayOfWeek();
	}

	public int getDayOfMonth() {
		return this.date.getDayOfMonth();
	}

	public int getFirstDayOfMonth() {
		return this.date.withDayOfMonth(1).getDayOfMonth();
	}

	public int getMaxDaysInMonth() {
		return this.date.lengthOfMonth();
	}

	public LocalDate getDate() {
		return this.date;
	}

	public Month getMonth() {
		return this.date.getMonth();
	}

	public String getMonthName() {
		switch (this.date.getMonth().getValue()) {
		case 0:
			return this.messages.getString("january");
		case 1:
			return this.messages.getString("february");
		case 2:
			return this.messages.getString("march");
		case 3:
			return this.messages.getString("april");
		case 4:
			return this.messages.getString("may");
		case 5:
			return this.messages.getString("june");
		case 6:
			return this.messages.getString("july");
		case 7:
			return this.messages.getString("august");
		case 8:
			return this.messages.getString("september");
		case 9:
			return this.messages.getString("october");
		case 10:
			return this.messages.getString("november");
		case 11:
			return this.messages.getString("december");

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
