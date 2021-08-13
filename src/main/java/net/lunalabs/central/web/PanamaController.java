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
import net.lunalabs.central.domain.oracle.Panama;
import net.lunalabs.central.service.PanamaService;

@RequiredArgsConstructor
@RestController
public class PanamaController {
	
	 private static final Logger log = LoggerFactory.getLogger(PanamaController.class);

	
	 private final PanamaService panamaService;
	 
	 
		@GetMapping("/panama")
		public List<Panama> findAll() {

			log.info("리스트 뿌리기");

			return panamaService.findAll();
		}
	 
	 
	 
		@PostMapping("/panama")
		public String save2(@RequestBody Panama panama) {
			
			log.info(panama.toString());		
			//panamaMapper.save(panama);
			
			panamaService.save(panama);
			
			return "ok";
		}

	 

}
