package com.example.fetusvoicemeter.entity;

public class RecorderEntity {
	
	

	public RecorderEntity() {
		super();
	}

	public RecorderEntity(String name, Integer[] beatValues, Float[] beatTimes,
			long durationTime, long startTime) {
		super();
		this.name = name;
		this.beatValues = beatValues;
		this.beatTimes = beatTimes;
		this.durationTime = durationTime;
		this.startTime = startTime;
	}

	private String name;
	
	private Integer[] beatValues;
	
	private Float[] beatTimes;
	
	private long durationTime;
	
	private long startTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer[] getBeatValues() {
		return beatValues;
	}

	public void setBeatValues(Integer[] beatValues) {
		this.beatValues = beatValues;
	}

	public Float[] getBeatTimes() {
		return beatTimes;
	}

	public void setBeatTimes(Float[] beatTimes) {
		this.beatTimes = beatTimes;
	}

	public long getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(long durationTime) {
		this.durationTime = durationTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	
}
