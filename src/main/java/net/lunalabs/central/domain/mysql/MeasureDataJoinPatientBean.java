package net.lunalabs.central.domain.mysql;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_ABSENT) //null 제외
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeasureDataJoinPatientBean {  //sql에서 select 안 하고, 바로 socket 파싱 결과로 빌더.
	
	//deviceId로 구분
	//private String deviceId;
	
	
	//measureData
	private Integer mid;
	private String sid; //세션데이터 id
	private Integer pid;
	private String parame;
	private String valueUnit;
	private String value;
	private String startTime; 
	private String endTime;
	
	//Patient
	private int age;
	
}
