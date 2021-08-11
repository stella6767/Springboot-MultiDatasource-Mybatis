package net.lunalabs.central.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.product.Product;
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
	
}
