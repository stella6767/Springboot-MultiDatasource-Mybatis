package net.lunalabs.central.domain.mysql;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionData {
	
	private String sid;  
	private Integer pid;
	private String deviceId;
	private Timestamp startTime;
	private Timestamp endTime;

}
