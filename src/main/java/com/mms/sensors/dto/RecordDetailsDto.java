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
public class RecordDetailsDto {

	
	private String timestamp;
	private String date;
	private String time;
	private List<EntryDto> entry;
}
