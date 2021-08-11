package net.lunalabs.central.domain.mysql.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	private int id;
	private String name; // 상품명
	private String code; //  상품코드
}
