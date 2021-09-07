package net.lunalabs.central.mapper.mysql;

import net.lunalabs.central.config.db.MysqlConnMapper;
import net.lunalabs.central.domain.Patient;
import net.lunalabs.central.domain.mysql.bilabfile.BilabFile;

@MysqlConnMapper
public interface BilabFileMapper {

	public void save(BilabFile bilabFile); 

	
}
