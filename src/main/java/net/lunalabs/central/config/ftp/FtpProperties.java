package net.lunalabs.central.config.ftp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import net.lunalabs.central.config.YamlPropertySourceFactory;

@Getter
@Configuration
@PropertySource(value = "classpath:ftpconfig.yml",
factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
@ConfigurationProperties("ftp")
public class FtpProperties {//일단은 하드코딩
	
	public Integer port;
	public String userlist;
	public String uploadDir;
	
	
//	FUKUDAGW.FTP.PORT=21
//	FUKUDAGW.FTP.USERLIST=FX7102DATA/FUKUDADENSHI,mediana/1234,kyu/1234
//			FUKUDAGW.EMR.FTP.URL=116.2.12.12
//			FUKUDAGW.EMR.FTP.ID=emr
//			FUKUDAGW.EMR.FTP.PW=hpinvent1!
//			FUKUDAGW.EMR.FTP.PORT=21

}
