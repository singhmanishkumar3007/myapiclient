package com.tech.boot.myclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api.geturl")
public class APIConfigs {

	private String url;
	private String description;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public APIConfigs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public APIConfigs(String url, String description) {
		super();
		this.url = url;
		this.description = description;
	}

}
