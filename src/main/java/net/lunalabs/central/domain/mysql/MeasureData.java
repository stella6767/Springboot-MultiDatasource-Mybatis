package net.lunalabs.central.domain.mysql;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Accessors(chain=true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeasureData {
	
	private Integer mid;
	private String sid; //세션데이터 id
	private String parame;
	private String value;
	private String startTime; //이렇게 String으로 받아도 되나??
	
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String endTime;
	
}
