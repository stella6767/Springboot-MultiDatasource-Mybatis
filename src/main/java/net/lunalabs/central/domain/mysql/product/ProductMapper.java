package net.lunalabs.central.domain.mysql.product;

import java.util.List;

import net.lunalabs.central.config.MysqlConnMapper;

@MysqlConnMapper
public interface ProductMapper {	
	public void save(Product product); 
	public void deleteById(int id);
	public void update(Product product);
//	public List<Product> findAll();
//	public Product findById(int id);	
}
