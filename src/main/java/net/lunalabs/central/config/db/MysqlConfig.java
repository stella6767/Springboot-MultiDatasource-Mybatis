package net.lunalabs.central.config.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
//@EnableTransactionManagement
@MapperScan(value = "net.lunalabs.central", annotationClass=MysqlConnMapper.class, sqlSessionFactoryRef="MySqlSessionFactory")//멀티DB사용시 mapper클래스파일 스켄용 basePackages를 DB별로 따로설정, 지금은 따로 어노테이션 만드므로 상관없음
public class MysqlConfig{
	
	 private static final Logger log = LoggerFactory.getLogger(MysqlConfig.class);
	
	
   @Primary//동일한 유형의 Bean이 여러 개 있을 때 해당 Bean에 더 높은 우선권을 부여
   @Bean(name = "MysqlDataSource")
   @ConfigurationProperties(prefix = "spring.datasource.mysql") //application.yaml에서 어떤 properties를 읽을 지 지정
   public DataSource mysqlDataSource() {
  	 
  	 log.info("yml 설정으로 Mysql Datasource set" );    	 
       return DataSourceBuilder
      		 .create()  
      		 .build(); //type(HikariDataSource.class).
   }

   @Primary
   @Bean(name = "MySqlSessionFactory")
   public SqlSessionFactory mySqlSessionFactory(@Qualifier("MysqlDataSource") DataSource mysqlDataSource, ApplicationContext applicationContex) throws Exception {
	   
          SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
          sqlSessionFactoryBean.setDataSource(mysqlDataSource);
          sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("mybatis-config/mysql-config.xml")); //mybatis 설정 xml 파일매핑 
          sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/mysql/*.xml"));
          //sqlSessionFactoryBean.setMapperLocations(applicationContex.getResources("classpath:mapper/mysql/*.xml"));
          sqlSessionFactoryBean.setTypeAliasesPackage("net.lunalabs.central.domain.mysql"); //benas pakage에 dao나 vo 모아둘 때 구분하기 위해 쓰는 것도 좋음
          //log.info("여기" + new PathMatchingResourcePatternResolver().getResources("mapper/mysql/*.xml").toString());
          
          //sqlSessionFactoryBean.setTypeAliasesPackage(null); //Mapper 에서 사용하고자하는 VO 및 Entity 에 대해서
          return sqlSessionFactoryBean.getObject();
   }

   @Primary
   @Bean(name = "MysqlSessionTemplate")
   public SqlSessionTemplate mySqlSessionTemplate(@Qualifier("MySqlSessionFactory") SqlSessionFactory mySqlSessionFactory) {
       return new SqlSessionTemplate(mySqlSessionFactory);
   }
   
   
   @Bean(name = "MysqlTransactionManager")
   @Primary
   public DataSourceTransactionManager PrimaryTransactionManager(@Qualifier("MysqlDataSource") DataSource mysqlDataSource) {
       return new DataSourceTransactionManager(mysqlDataSource);
   }
   
   
   
   
}