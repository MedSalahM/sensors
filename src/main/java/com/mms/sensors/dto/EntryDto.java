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
public class EntryDto {

	private String sensorid;
	private String name;
	private double value;
	private int trend;

}
