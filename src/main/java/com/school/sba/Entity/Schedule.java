package com.school.sba.Entity;

import java.time.Duration;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scheduleId;
	private LocalTime opensAt;
	private LocalTime closesAt;
	private int classHoursPerDay;
	private Duration classHourLengthInMinutes;
	private LocalTime breakTime;
	private Duration breakLength;
	private LocalTime lunchTime;
	private Duration lunchLength;
	
	
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
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
	public Duration getClassHourLengthInMinutes() {
		return classHourLengthInMinutes;
	}
	public void setClassHourLengthInMinutes(Duration classHourLengthInMinutes) {
		this.classHourLengthInMinutes = classHourLengthInMinutes;
	}
	public LocalTime getBreakTime() {
		return breakTime;
	}
	public void setBreakTime(LocalTime breakTime) {
		this.breakTime = breakTime;
	}
	public Duration getBreakLength() {
		return breakLength;
	}
	public void setBreakLength(Duration breakLength) {
		this.breakLength = breakLength;
	}
	public LocalTime getLunchTime() {
		return lunchTime;
	}
	public void setLunchTime(LocalTime lunchTime) {
		this.lunchTime = lunchTime;
	}
	public Duration getLunchLength() {
		return lunchLength;
	}
	public void setLunchLength(Duration lunchLength) {
		this.lunchLength = lunchLength;
	}
	
	

	
	
	
	
}
