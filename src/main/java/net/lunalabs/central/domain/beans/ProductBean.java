package net.lunalabs.central.domain.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBean {
	private int id; //panmae
	private String username; //panmae   //oracle과 mysql이라 조인을 못하겠군..
	private String name; //product
	private String code; //product
}