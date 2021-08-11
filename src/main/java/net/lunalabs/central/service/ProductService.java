package net.lunalabs.central.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.product.Product;
import net.lunalabs.central.domain.mysql.product.ProductMapper;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductMapper productMapper;
	
	
	@Transactional
	public void save(Product product) {
		productMapper.save(product);
	
	}
	
}
