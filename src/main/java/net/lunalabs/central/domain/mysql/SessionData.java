package net.lunalabs.central.domain.mysql;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionData {
	
	private int sid;  //일단 int로
	private int pid;
	private String deviceId;
	private Date startTime;
	private Date endTime;

}
