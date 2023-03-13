package com.mms.sensors.domaine;

import com.mms.sensors.dto.RecordDetailsDto;
import com.mms.sensors.dto.RecordDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorValue {

	

	private Long id;
	private String timestamp;
	private String date;
	private String time;
	private String sensorid;
	private String value;
	private String trend;
	
	
	
}
