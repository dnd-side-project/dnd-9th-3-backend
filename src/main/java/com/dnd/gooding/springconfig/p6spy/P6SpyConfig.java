package com.dnd.gooding.springconfig.p6spy;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.spy.P6SpyOptions;

@Configuration
public class P6SpyConfig {

	@PostConstruct
	public void setLogMessageFormat() {
		P6SpyOptions.getActiveInstance().setLogMessageFormat(P6SpyFormatter.class.getName());
	}
}
