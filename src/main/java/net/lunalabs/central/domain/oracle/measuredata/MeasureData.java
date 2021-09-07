package net.lunalabs.central.domain.oracle.measuredata;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_ABSENT) //json 직렬화 시 null 제외
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeasureData {
	
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
