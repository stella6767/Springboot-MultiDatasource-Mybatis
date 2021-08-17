package net.lunalabs.central.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.Product;
import net.lunalabs.central.domain.mysql.ProductBean;
import net.lunalabs.central.service.mysql.ProductService;

@RequiredArgsConstructor
@RestController
public class ProductController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	// private final ProductMapper productRepository;
	// private final PanamaMapper panamaMapper;

	private final ProductService productService;

	@GetMapping("/product")
	public List<Product> findAll() {

		log.info("리스트 뿌리기");

		return productService.findAll();
	}

	@PostMapping("/product")
	public String save(@RequestBody Product product) {

		log.info(product.toString());

		// productRepository.save(product);

		productService.save(product);

		return "ok";
	}
	
	
	
	@GetMapping("/product/{id}")
	public ProductBean findByIdAndJoin(@PathVariable int id){
		return productService.findByIdAndJoin(id); 
	}

}
