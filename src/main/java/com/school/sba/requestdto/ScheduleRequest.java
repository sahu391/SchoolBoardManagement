package com.school.sba.requestdto;

import java.time.Duration;
import java.time.LocalTime;

public class ScheduleRequest {
	
	
	private LocalTime opensAt;
	private LocalTime closesAt;
	private int classHoursPerDay;
	private int classHourLengthInMinutes;
	private LocalTime breakTime;
	private int breakLength;
	private LocalTime lunchTime;
	private int lunchLength;
	
	public LocalTime getOpensAt() {
		return opensAt;
	}
	public void setOpensAt(LocalTime opensAt) {
		this.opensAt = opensAt;
	}
	public LocalTime getClosesAt() {
		return closesAt;
	}
	public void setClosesAt(LocalTime closesAt) {
		this.closesAt = closesAt;
	}
	public int getClassHoursPerDay() {
		return classHoursPerDay;
	}
	public void setClassHoursPerDay(int classHoursPerDay) {
		this.classHoursPerDay = classHoursPerDay;
	}
	public int getClassHourLengthInMinutes() {
		return classHourLengthInMinutes;
	}
	public void setClassHourLengthInMinutes(int classHourLengthInMinutes) {
		this.classHourLengthInMinutes = classHourLengthInMinutes;
	}
	public LocalTime getBreakTime() {
		return breakTime;
	}
	public void setBreakTime(LocalTime breakTime) {
		this.breakTime = breakTime;
	}
	public int getBreakLength() {
		return breakLength;
	}
	public void setBreakLength(int breakLength) {
		this.breakLength = breakLength;
	}
	public LocalTime getLunchTime() {
		return lunchTime;
	}
	public void setLunchTime(LocalTime lunchTime) {
		this.lunchTime = lunchTime;
	}
	public int getLunchLength() {
		return lunchLength;
	}
	public void setLunchLength(int lunchLength) {
		this.lunchLength = lunchLength;
	}
	public ScheduleRequest(LocalTime opensAt, LocalTime closesAt, int classHoursPerDay,
			int classHourLengthInMinutes, LocalTime breakTime, int breakLength, LocalTime lunchTime,
			int lunchLength) {
		super();
		
		this.opensAt = opensAt;
		this.closesAt = closesAt;
		this.classHoursPerDay = classHoursPerDay;
		this.classHourLengthInMinutes = classHourLengthInMinutes;
		this.breakTime = breakTime;
		this.breakLength = breakLength;
		this.lunchTime = lunchTime;
		this.lunchLength = lunchLength;
	}
	public ScheduleRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
