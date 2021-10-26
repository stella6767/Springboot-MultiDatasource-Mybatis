//package net.lunalabs.central.mapper.mysql;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import lombok.extern.slf4j.Slf4j;
//import net.lunalabs.central.config.db.MysqlConfig;
//
//
////replace = Replace.ANY = 가짜 데이터베이스 띄워서 테스트, Replace.None은 실제 DB로 테스트
//
//
//@Slf4j
////@AutoConfigureTestDatabase(replace = Replace.ANY)
//@ExtendWith(SpringExtension.class)
////@SpringBootTest
//@Transactional
//@MybatisTest(properties = "spring.datasource.mysql")
////@PropertySource("persistence-generic-entity.properties")
////@ContextConfiguration(classes = MysqlConfig.class)
//class MapperTest {
//	
//	
//	@Test
//	public void findByContainIdTest() {
//		//fail("Not yet implemented");
//		//multiple datasource mybatis test code not working!!!
//		
//	}
//
//}
