package net.lunalabs.central.domain.oracle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patient2 {
	
	private int pid;
	private String name;
	private String gender;
	private int height;
	private int weight;

}
