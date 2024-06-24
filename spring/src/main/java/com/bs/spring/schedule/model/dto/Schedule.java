package com.bs.spring.schedule.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

	private int scheduleNo;
	private String scheduleTitle;
	private String scheduleContent;
	private int scheduleLabel;
	private String schedulePlace;
	private String scheduleType;
	private Date scheduleStartDate;
	private Date scheduleEndDate;
}
