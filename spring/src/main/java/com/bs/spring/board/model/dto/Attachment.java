package com.bs.spring.board.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {
	
	private int attachNo;
	private String originalFileName;
	private String renamedFileName;
	private int boardNo;
	private Date uploadDate;
	private int downloadCount;
	private String status;

}
