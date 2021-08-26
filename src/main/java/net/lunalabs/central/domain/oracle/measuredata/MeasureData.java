package net.lunalabs.central.domain.oracle.measuredata;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeasureData {
	
	private int mid;
	private int pid; //환자 id
	private String parame;
	private String value;
	private Date startTime;
	private Date endTime;
	
}
