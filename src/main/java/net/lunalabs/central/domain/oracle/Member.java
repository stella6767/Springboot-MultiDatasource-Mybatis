package net.lunalabs.central.domain.oracle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private int no;
	private String name;
	private int jumin;
	private String passwd;
	private String id;
	private String an_key;
	private String an_key_dap;
}