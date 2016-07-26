package com.instinctools.configs;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.instinctools.repositories")
@EnableTransactionManagement
@ComponentScan(basePackages = "com.instinctools")
@EnableAsync
public class CurrencyExchangerApplication extends Neo4jConfiguration{

	public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangerApplication.class, args);
	}

	@Bean
    @Override
	public SessionFactory getSessionFactory() {
		return new SessionFactory("com.instinctools.domain");
	}

	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Override
	public Session getSession() throws Exception {
		return super.getSession();
	}
}
