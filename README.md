# Springboot-MultiDatasource-Mybatis-JDBCLOG4j 연동
oracle과 mysql mybatis 연동




## DB example


### oracle 

```oracle
create USER test IDENTIFIED by 1234; -- 사용자이름 superuser DB이름 supreruser
grant create session to test;
grant create table to test;
grant create sequence to test;
grant unlimited tablespace to test;
  ----접속 후-------

create table panama
(
  id number not null,
  productId  number ,
  username VARCHAR2(100));
  
create SEQUENCE panama_seq  -- 시퀀스이름은 무조건 이런식으로 정한다고 생각하자
increment by 1  --1씩 증가
start with 1; -- 1부터


drop SEQUENCE panama_seq; 
 
drop table panama;
  


```

### mysql

```mysql


create user 'test' @'%' IDENTIFIED by '1234';
GRANT all PRIVILEGES on *.* to 'test'@'%';
create DATABASE test;


-------접속 후-----------


use test;


create table product(
	id int auto_increment primary key,
	name varchar(50),
    code varchar(50)
);

```


## apllication.yml

```



server:
  servlet:
    encoding:
      charset: utf-8
      enabled: true

      
spring:
  profiles:
    active:
      - dev
      
      
---
      
server:
  port: 8088           
      
spring:
  config:
    activate:
      on-profile:
        - dev


  datasource:
    mysql:
#      driver-class-name: com.mysql.cj.jdbc.Driver
#      jdbc-url: jdbc:log4jdbc:mysql://db.rigel.kr:3306/bilab?&characterEncoding=UTF-8&serverTimezone=Asia/Seoul

      driver-class-name: net.sf.log4jdbc.sql.jdbcapi.DriverSpy
      jdbc-url: jdbc:log4jdbc:mysql://db.rigel.kr:3306/bilab?&characterEncoding=UTF-8&serverTimezone=Asia/Seoul
      username: bilab
      password: bilab1234
    oracle:
#      driver-class-name: oracle.jdbc.driver.OracleDriver
#      jdbc-url: jdbc:oracle:thin:@localhost:1521/xe

      driver-class-name: net.sf.log4jdbc.sql.jdbcapi.DriverSpy
      jdbc-url: jdbc:log4jdbc:oracle:thin:@localhost:1521/xe
      username: test
      password: 1234
#    hikari:
#      jdbc-url: 


  output:
    ansi:
      enabled: always



---

server:
  port: 8081
spring:
  config:
    activate:
      on-profile: prod     
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://hk-mysql:3306/cos?serverTimezone=Asia/Seoul
    username: root
    password: bitc5600

  jpa:
    hibernate:
      ddl-auto: update # create, update, none
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: true


```



## logback-spring.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<!-- 60초마다 설정 파일의 변경을 확인 하여 변경시 갱신 -->
<configuration scan="true" scanPeriod="60 seconds">

	<!--로그 파일 저장 위치 -->
	<springProfile name="dev">
		<property name="LOGS_PATH" value="./logs" />
	</springProfile>



	<!-- 이 속성 안 쓸거임 -->
	<property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){green} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}" />
	<property name="CONSOLE_LOG_CHARSET" value="${CONSOLE_LOG_CHARSET:-default}" />



	<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		<withJansi>true</withJansi>

		<encoder>

			<pattern>[%d{yyyy-MM-dd HH:mm:ss}:%-3relative][%thread] %green(%-5level) %logger{35} %cyan(%logger{15}) - %msg %n</pattern>

		</encoder>
	</appender>

	<appender name="DAILY_ROLLING_FILE_APPENDER" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<file>${LOGS_PATH}/logback.log</file>
		<encoder>
			<pattern>[%d{yyyy-MM-dd HH:mm:ss}:%-3relative][%thread] %-5level%logger{35} - %msg%n</pattern>
		</encoder>
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<fileNamePattern>${LOGS_PATH}/logback.%d{yyyy-MM-dd}.%i.log.gz
			</fileNamePattern>
			<timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
				<!-- or whenever the file size reaches 100MB -->
				<maxFileSize>5MB</maxFileSize>
				<!-- kb, mb, gb -->
			</timeBasedFileNamingAndTriggeringPolicy>
			<maxHistory>30</maxHistory>
		</rollingPolicy>
	</appender>

	<logger name="net.lunalabs.central" level="INFO">
		<appender-ref ref="DAILY_ROLLING_FILE_APPENDER" />
	</logger>

	<!-- log4jdbc 옵션 설정 -->
	<logger name="jdbc" level="OFF" /> <!-- 커넥션 open close 이벤트를 로그로 남긴다. -->
	<logger name="jdbc.connection" level="OFF" /> <!-- SQL문만을 로그로 남기며, PreparedStatement일 경우 관련된 argument 값으로 대체된 SQL문이 보여진다. -->
	<logger name="jdbc.sqlonly" level="OFF" /> <!-- SQL문과 해당 SQL을 실행시키는데 수행된 시간 정보(milliseconds)를 포함한다. -->
	<logger name="jdbc.sqltiming" level="DEBUG" /> <!-- ResultSet을 제외한 모든 JDBC 호출 정보를 로그로 남긴다. 많은 양의 로그가 생성되므로 특별히 JDBC 문제를 추적해야 할 필요가 있는 경우를 제외하고는 사용을 권장하지 않는다. -->
	<logger name="jdbc.audit" level="OFF" /> <!-- ResultSet을 포함한 모든 JDBC 호출 정보를 로그로 남기므로 매우 방대한 양의 로그가 생성된다. -->
	<logger name="jdbc.resultset" level="OFF" /> <!-- SQL 결과 조회된 데이터의 table을 로그로 남긴다. -->
	<logger name="jdbc.resultsettable" level="OFF" />



	<root level="INFO">
		<appender-ref ref="STDOUT" />
	</root>
</configuration>


```



## log4jdbc.log4j2.properties


```

log4jdbc.spylogdelegator.name=net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator
log4jdbc.dump.sql.maxlinelength=0


```


## DB config


```


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
            //sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config-primary.xml")); //mybatis 설정 xml 파일매핑
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/mysql/*.xml"));
            //sqlSessionFactoryBean.setMapperLocations(applicationContex.getResources("classpath:mapper/mysql/*.xml"));
            //sqlSessionFactoryBean.setTypeAliasesPackage("net.lunalabs.central.domain.mysql.product"); //benas pakage에 dao나 vo 모아둘 때 구분하기 위해 쓰는 것도 좋음
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


```




```

@Configuration
@MapperScan(value="net.lunalabs.central",annotationClass = OracleConnMapper.class,sqlSessionFactoryRef="OracleSqlSessionFactory")//멀티DB사용시 mapper클래스파일 스켄용 basePackages를 DB별로 따로설정, 지금은 따로 어노테이션 만드므로 상관없음
public class OracleConfig {
	
	
	private static final Logger log = LoggerFactory.getLogger(OracleConfig.class);

	
    @Bean(name = "OracleDataSource")
    @ConfigurationProperties(prefix="spring.datasource.oracle")
    public DataSource SecondDataSource() {
    	
    	log.info("oracle datasource yml 설정");
    	
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
    
    
    @Bean(name = "OracleTransactionManager")
    public DataSourceTransactionManager PrimaryTransactionManager(@Qualifier("OracleDataSource") DataSource oracleDataSource) {
        return new DataSourceTransactionManager(oracleDataSource);
    }
    
    
}




```



## porm.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.5.3</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>
	<groupId>net.lunalabs</groupId>
	<artifactId>central</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>central</name>
	<description>Demo project for Spring Boot</description>
	<properties>
		<java.version>11</java.version>
	</properties>
	<dependencies>

		<!-- log4jdbc -->
		<dependency>
			<groupId>org.bgee.log4jdbc-log4j2</groupId>
			<artifactId>log4jdbc-log4j2-jdbc4.1</artifactId>
			<version>1.16</version>
		</dependency>



		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>


		<!-- MariaDB는 다른 식으로 연결.. -->

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>



		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
				<exclusions> 
				<!-- 스프링 부트에서 Log4j2를 사용하기위해선 내부로깅에서 쓰이는 의존성을 제외해주어야 합니다. 기본적으로 Spring은 Slf4j라는 로깅 프레임워크를 사용합니다.
				 구현체를 손쉽게 교체할 수 있도록 도와주는 프레임 워크입니다. Slf4j는 인터페이스고 내부 구현체로 logback을 가지고 있는데, Log4j2를 사용하기 위해 exclude 해야 합니다. -->
					<exclusion>
						<groupId>org.springframework.boot</groupId>
						<artifactId>spring-boot-starter-logging</artifactId>
					</exclusion>
			    </exclusions>
		</dependency>
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>2.2.0</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.oracle.database.jdbc</groupId>
			<artifactId>ojdbc8</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.mariadb.jdbc</groupId>
			<artifactId>mariadb-java-client</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.restdocs</groupId>
			<artifactId>spring-restdocs-mockmvc</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
			<optional>true</optional>
		</dependency>
	</dependencies>

	<build>
		<finalName>bilabCs</finalName>
		<plugins>
			<plugin>
				<groupId>org.asciidoctor</groupId>
				<artifactId>asciidoctor-maven-plugin</artifactId>
				<version>1.5.8</version>
				<executions>
					<execution>
						<id>generate-docs</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>process-asciidoc</goal>
						</goals>
						<configuration>
							<backend>html</backend>
							<doctype>book</doctype>
						</configuration>
					</execution>
				</executions>
				<dependencies>
					<dependency>
						<groupId>org.springframework.restdocs</groupId>
						<artifactId>spring-restdocs-asciidoctor</artifactId>
						<version>${spring-restdocs.version}</version>
					</dependency>
				</dependencies>
			</plugin>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>


```


## 결과
![image](https://user-images.githubusercontent.com/65489223/129328027-3383fe3a-74ea-4d7f-9a1e-a7f45e254815.png)
![image](https://user-images.githubusercontent.com/65489223/129328054-90ac46a7-3064-421a-84a7-92b60ed20ec3.png)


