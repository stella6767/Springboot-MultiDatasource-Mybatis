package net.lunalabs.central.domain.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product { //DB와 직접 일치하는 vo
	private int id;
	private String name; // 상품명
	private String code; //  상품코드
}
