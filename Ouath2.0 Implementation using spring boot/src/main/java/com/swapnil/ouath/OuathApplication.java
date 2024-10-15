package com.swapnil.ouath;

import com.swapnil.ouath.config.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class OuathApplication {

	public static void main(String[] args) {
		SpringApplication.run(OuathApplication.class, args);
	}

}
