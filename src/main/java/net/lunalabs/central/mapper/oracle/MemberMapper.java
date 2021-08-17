package net.lunalabs.central.mapper.oracle;

import java.util.List;

import net.lunalabs.central.config.db.OracleConnMapper;
import net.lunalabs.central.domain.oracle.Member;

@OracleConnMapper
public interface MemberMapper {

	//public void save(Member member); 
	public void deleteById(int id);
	//public void update(Member member);
	public List<Member> findAll();
	public Member findById(int id);	
	
	
}
