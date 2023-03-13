package com.mms.sensors.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorValueDto {
	

	private String hostname;
	private String timestamp;
	private String date;
	private String time;
	private String sensorid;
	private String ip;
	private double value;
	private int trend;
	
	
	
	
}
