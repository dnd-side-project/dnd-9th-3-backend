package com.dnd.gooding;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoodingApplication {

	@Value("${spring.environment}")
	private String environment;

	@Value("${spring.file-dir}")
	private String fileDir;

	@PostConstruct
	private void init() {
		if (environment.equals("local")) {
			String staticFolder = System.getProperty("user.dir") + "/src/main/resources/static";
			mkdirResource(staticFolder);

			String files = System.getProperty("user.dir") + fileDir;
			mkdirResource(files);
		} else if (environment.equals("development")) {
			String filesFolder = "/home/ec2-user/files";
			mkdirResource(filesFolder);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(GoodingApplication.class, args);
	}

	private static void mkdirResource(String fileDir) {
		File folder = new File(fileDir);
		if(!folder.exists()) {
			try {
				folder.mkdir();
			} catch(Exception e) {
				e.getStackTrace();
			}
		}
	}
}
