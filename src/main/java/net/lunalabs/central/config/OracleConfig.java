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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages="net.lunalabs.central",annotationClass = OracleConnMapper.class,sqlSessionFactoryRef="OracleSqlSessionFactory")//멀티DB사용시 mapper클래스파일 스켄용 basePackages를 DB별로 따로설정, 지금은 따로 어노테이션 만드므로 상관없음
public class OracleConfig {
	
	
	private static final Logger log = LoggerFactory.getLogger(OracleConfig.class);

	
    @Bean(name = "OracleDataSource")
    @ConfigurationProperties(prefix="spring.oracle.datasource")
    public DataSource SecondDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "OracleSqlSessionFactory")
    public SqlSessionFactory oracleSqlSessionFactory(@Qualifier("OracleDataSource") DataSource oracleDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(oracleDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/oracle/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "OracleSessionTemplate")
    public SqlSessionTemplate oracleSqlSessionTemplate(@Qualifier("OracleSqlSessionFactory") SqlSessionFactory oracleSessionTemplate) {
        return new SqlSessionTemplate(oracleSessionTemplate);
    }
}