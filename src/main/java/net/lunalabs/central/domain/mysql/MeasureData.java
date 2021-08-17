package net.lunalabs.central.domain.mysql;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeasureData {
	
	private int mid;
	private int sid; //세션데이터 id
	private String parame;
	private String value;
	private Date startTime;
	private Date endTime;
	
}
