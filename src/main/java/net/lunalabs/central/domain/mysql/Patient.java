package net.lunalabs.central.domain.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patient {
	
	private Integer pid; //protoType이니 일단 integer로 받기로
	private String name;
	private String gender;
	private int height;
	private int weight;

}
