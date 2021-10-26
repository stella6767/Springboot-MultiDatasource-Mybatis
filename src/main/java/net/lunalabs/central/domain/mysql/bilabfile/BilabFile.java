package net.lunalabs.central.domain.mysql.bilabfile;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BilabFile {	
	private Integer fid;
	private String fileName;
	private String filePath;
	//private Timestamp createTime;
	//private Timestamp updateTime;
}
