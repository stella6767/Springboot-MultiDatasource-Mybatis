package net.lunalabs.central.domain.mysql.sessiondata;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionData {
	
	private String sid;  
	private Integer pid;
	private String deviceId;
	private String startTime;
	private String endTime;

}
