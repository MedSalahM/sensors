package com.mms.sensors.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SondeDto {

	
	private String id;
	private String name;
	private String unit;
	private String status;
	private String uplim;
	private String lolim;
	
	
}
