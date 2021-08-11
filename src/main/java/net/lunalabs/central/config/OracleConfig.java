package net.lunalabs.central.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class OracleConfig {
    @Bean(name = "OracleDataSource")
    @ConfigurationProperties(prefix="spring.oracle.datasource")
    public DataSource SecondDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "OracleSqlSessionFactory")
    public SqlSessionFactory oracleSqlSessionFactory(@Qualifier("OracleDataSource") DataSource oracleDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(oracleDataSource);
        //sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/oracle/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "OracleSessionTemplate")
    public SqlSessionTemplate oracleSqlSessionTemplate(@Qualifier("OracleSqlSessionFactory") SqlSessionFactory oracleSessionTemplate) {
        return new SqlSessionTemplate(oracleSessionTemplate);
    }
}