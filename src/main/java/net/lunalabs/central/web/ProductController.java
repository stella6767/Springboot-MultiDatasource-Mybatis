package net.lunalabs.central.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.config.MysqlConfig;
import net.lunalabs.central.domain.mysql.product.Product;
import net.lunalabs.central.domain.mysql.product.ProductMapper;
import net.lunalabs.central.domain.oracle.panama.Panama;
import net.lunalabs.central.domain.oracle.panama.PanamaMapper;


@RequiredArgsConstructor
@RestController
public class ProductController {
	

	 private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	private final ProductMapper productRepository;
	private final PanamaMapper panamaMapper;
	
	
	@PostMapping("/product")
	public String save(@RequestBody Product product) {
		
		log.info(product.toString());
		
		productRepository.save(product);
		
		return "ok";
	}
	
	
	@PostMapping("/panama")
	public String save2(@RequestBody Panama panama) {
		panamaMapper.save(panama);
		return "ok";
	}

}
