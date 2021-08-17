package net.lunalabs.central.mapper.oracle;

import java.util.List;

import net.lunalabs.central.config.db.OracleConnMapper;
import net.lunalabs.central.domain.oracle.Test;

@OracleConnMapper
public interface TestMapper {

	public List<Test> findAll();

	
}
