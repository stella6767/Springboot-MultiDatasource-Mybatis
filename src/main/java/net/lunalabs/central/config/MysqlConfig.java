package net.lunalabs.central.config;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
//@EnableTransactionManagement
@MapperScan(value = "net.lunalabs.central",annotationClass = MysqlConnMapper.class,sqlSessionFactoryRef="MySqlSessionFactory")//멀티DB사용시 mapper클래스파일 스켄용 basePackages를 DB별로 따로설정, 지금은 따로 어노테이션 만드므로 상관없음
public class MysqlConfig{
	
	 private static final Logger log = LoggerFactory.getLogger(MysqlConfig.class);
	
	
     @Primary//동일한 유형의 Bean이 여러 개 있을 때 해당 Bean에 더 높은 우선권을 부여
     @Bean(name = "MysqlDataSource")
     @ConfigurationProperties(prefix = "spring.mysql.datasource") //application.yaml에서 어떤 properties를 읽을 지 지정
     public DataSource firstDataSource() {
         return DataSourceBuilder.create().build(); //type(HikariDataSource.class).
     }

     @Primary
     @Bean(name = "MySqlSessionFactory")
     public SqlSessionFactory mySqlSessionFactory(@Qualifier("MysqlDataSource") DataSource mysqlDataSource) throws Exception {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(mysqlDataSource);
            //sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config-primary.xml"));
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/mysql/*.xml"));
            sqlSessionFactoryBean.setTypeAliasesPackage("net.lunalabs.central.domain.mysql.product");
            //log.info("여기" + new PathMatchingResourcePatternResolver().getResources("mapper/mysql/*.xml").toString());
            
            //sqlSessionFactoryBean.setTypeAliasesPackage(null); //Mapper 에서 사용하고자하는 VO 및 Entity 에 대해서
            return sqlSessionFactoryBean.getObject();
     }

     @Primary
     @Bean(name = "MysqlSessionTemplate")
     public SqlSessionTemplate mySqlSessionTemplate(@Qualifier("MySqlSessionFactory") SqlSessionFactory firstSqlSessionFactory) {
         return new SqlSessionTemplate(firstSqlSessionFactory);
     }
     
     
     @Bean(name = "MysqlTransactionManager")
     @Primary
     public DataSourceTransactionManager PrimaryTransactionManager(@Qualifier("MysqlDataSource") DataSource mysqlDataSource) {
         return new DataSourceTransactionManager(mysqlDataSource);
     }
     
     
     
     
}