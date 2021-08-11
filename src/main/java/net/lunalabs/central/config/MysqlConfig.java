package net.lunalabs.central.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MysqlConfig{
	
     @Primary    
     @Bean(name = "MysqlDataSource")
     @ConfigurationProperties(prefix = "spring.mysql.datasource")
     public DataSource firstDataSource() {
         return DataSourceBuilder.create().build();
     }

     @Primary
     @Bean(name = "MySqlSessionFactory")
     public SqlSessionFactory mySqlSessionFactory(@Qualifier("MysqlDataSource") DataSource mysqlDataSource) throws Exception {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(mysqlDataSource);
            //sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/mysql/*.xml"));
            return sqlSessionFactoryBean.getObject();
     }

     @Primary
     @Bean(name = "MysqlSessionTemplate")
     public SqlSessionTemplate mySqlSessionTemplate(@Qualifier("MySqlSessionFactory") SqlSessionFactory firstSqlSessionFactory) {
         return new SqlSessionTemplate(firstSqlSessionFactory);
     }
}