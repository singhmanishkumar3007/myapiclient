package com.tech.boot.myclient.config;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	// @Bean
	// public RestTemplate restTemplate() {
	// return new RestTemplate();
	// }

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		PoolingHttpClientConnectionManager connectionManger = new PoolingHttpClientConnectionManager();
		connectionManger.closeExpiredConnections();
		connectionManger.closeIdleConnections(1, TimeUnit.MILLISECONDS);
		connectionManger.setDefaultMaxPerRoute(10);
		connectionManger.setMaxTotal(10);
		connectionManger.setValidateAfterInactivity(100);

		CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionTimeToLive(10, TimeUnit.SECONDS)
				.setConnectionManager(connectionManger).build();
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = restTemplateBuilder.build();
		restTemplate.setRequestFactory(requestFactory);
		return restTemplate;

	}

//	@Bean
//	public DataSource dataSource() {
//		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//		dataSourceBuilder.url(dbUrl);
//		dataSourceBuilder.username(username);
//		dataSourceBuilder.password(password);
//		return dataSourceBuilder.build();
//
//	}

	@Bean
	public HttpHeaders httpHeaders() {
		return new HttpHeaders();
	}

}
