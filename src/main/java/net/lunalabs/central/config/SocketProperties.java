package net.lunalabs.central.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Setter //이걸로 값을 집어넣는 가보다.
@Getter
@Configuration
//@PropertySource(value = "classpath:ftpconfig.yml",
//factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
@PropertySource(value = "classpath:commonproperties.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix =  "socket")
public class SocketProperties {//일단은 하드코딩
	
	public Integer port;

}
