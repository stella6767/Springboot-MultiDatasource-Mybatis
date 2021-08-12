package net.lunalabs.central.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.product.ProductMapper;
import net.lunalabs.central.domain.oracle.panama.Panama;
import net.lunalabs.central.domain.oracle.panama.PanamaMapper;

@RequiredArgsConstructor
@Service
public class PanamaService {

	private final PanamaMapper panamaMapper;
	
	
	@Transactional
	public void save(Panama panama) {
		panamaMapper.save(panama);
	
	}
	
	@Transactional(readOnly = true)
	public List<Panama> findAll(){	
		List<Panama> books = panamaMapper.findAll();	
		return books;
	}
	
	
	@Transactional(readOnly = true)
	public Panama findById(int id) {
		
		Panama productEntity = panamaMapper.findById(id);		
		if(productEntity != null) {
			
			System.out.println("있음"+ productEntity);
			
			return productEntity;
		}
		return null;
	}
	
}
