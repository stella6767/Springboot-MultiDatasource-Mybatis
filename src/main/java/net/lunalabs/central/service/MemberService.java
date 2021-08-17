package net.lunalabs.central.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.oracle.Member;
import net.lunalabs.central.mapper.mysql.ProductMapper;
import net.lunalabs.central.mapper.oracle.MemberMapper;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberMapper memberMapper;
	
	
//	@Transactional
//	public void save(Member panama) {
//		memberMapper.save(panama);
//	
//	}
	
	@Transactional(readOnly = true)
	public List<Member> findAll(){	
		List<Member> members = memberMapper.findAll();	
		return members;
	}
	
	
	@Transactional(readOnly = true)
	public Member findById(int id) {
		
		Member memberEntity = memberMapper.findById(id);		
		if(memberEntity != null) {
			
			System.out.println("있음"+ memberEntity);
			
			return memberEntity;
		}
		return null;
	}
	
}
