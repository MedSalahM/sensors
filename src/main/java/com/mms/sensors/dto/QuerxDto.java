package com.mms.sensors.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuerxDto {
	
	
	private double version;
	private String hostname;
	private String ip;
	private int port;
	private String date_gmt;
	private String date_local;
	private String contact;
	private String location;

	private List<SensorDto> sensors;
	private List<RecordDto> data;
	
	
	
	
}
