package net.lunalabs.central.domain.measuredata;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;




@JsonInclude(JsonInclude.Include.NON_ABSENT) //null 제외
//@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain=true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeasureData { //MeasureData VO 도 공용
			
	private Integer mid;
	private String sid; //세션데이터 id
	private Integer pid;
	private String patientUserId;
	private String parame;
	private String valueUnit;
	private String value;
	private String startTime; //이렇게 String으로 받아도 되나??
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String endTime;
	
}
