package net.lunalabs.central.domain.mysql.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patient {
	
	private Integer pid; //protoType이니 일단 integer로 받기로
	private String firstname;
	private String lastname;
	//private GenderType gender;
	private int gender;
	private int age;
	private double height;
	private double weight;
	private String comment;

}
