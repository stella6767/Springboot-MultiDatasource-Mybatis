package net.lunalabs.central.domain.mysql.product;

import java.util.List;

import net.lunalabs.central.config.MysqlConnMapper;

@MysqlConnMapper //일종의 JPA Repository 역할로 레이어 구분하겠음
public interface ProductMapper {	
	public void save(Product product); 
	public void deleteById(int id);
	public void update(Product product);
//	public List<Product> findAll();
//	public Product findById(int id);	
}
