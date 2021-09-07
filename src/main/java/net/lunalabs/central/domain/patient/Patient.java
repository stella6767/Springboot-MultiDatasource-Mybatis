package net.lunalabs.central.domain.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patient { // oracle, mysql 칼럼이 같다고 가정하고, 공용으로 쓰겠다. 
	
	private Integer pid; //protoType이니 일단 integer로 받기로
	private String patientUserId;
	private String firstname;
	private String lastname;
	//private GenderType gender;
	private Integer gender;
	private Integer age;
	private Double height;
	private Double weight;
	private String lastSession; //sessionId
	private String comment;

}
