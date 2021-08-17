package net.lunalabs.central.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.Product;
import net.lunalabs.central.domain.oracle.Member;
import net.lunalabs.central.service.MemberService;

@RequiredArgsConstructor
@RestController
public class MemberController {
	
	 private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	
	 private final MemberService memberService;
	 
	 
		@GetMapping("/member")
		public List<Member> findAll() {

			log.info("리스트 뿌리기");

			return memberService.findAll();
		}
	 
	 
	 
//		@PostMapping("/panama")
//		public String save2(@RequestBody Member member) {
//			
//			log.info(member.toString());		
//			//panamaMapper.save(panama);
//			
//			memberService.save(member);
//			
//			return "ok";
//		}

	 

}
