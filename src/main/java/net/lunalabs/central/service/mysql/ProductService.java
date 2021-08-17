package net.lunalabs.central.service.mysql;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.Product;
import net.lunalabs.central.domain.mysql.ProductBean;
import net.lunalabs.central.mapper.mysql.ProductMapper;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductMapper productMapper;
	
	
	@Transactional
	public void save(Product product) {
		productMapper.save(product);
	
	}
	
	@Transactional(readOnly = true)
	public List<Product> findAll(){	
		List<Product> books = productMapper.findAll();	
		return books;
	}
	
	
	@Transactional(readOnly = true)
	public Product findById(int id) {
		
		Product productEntity = productMapper.findById(id);		
		if(productEntity != null) {
			
			System.out.println("있음"+ productEntity);
			
			return productEntity;
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	public ProductBean findByIdAndJoin(int id) {
		
		ProductBean productBean = productMapper.findByIdAndJoin(id);	
		
		if(productBean != null) {
			
			System.out.println("있음"+ productBean);
			
			return productBean;
		}
		return null;
	}
	
	
	
}
