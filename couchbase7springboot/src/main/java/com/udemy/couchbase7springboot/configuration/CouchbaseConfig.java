package com.udemy.couchbase7springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import com.couchbase.client.java.Cluster;
import com.couchbase.transactions.Transactions;
import com.couchbase.transactions.config.TransactionConfigBuilder;

@Configuration
@EnableCouchbaseRepositories(basePackages="com.udemy.couchbase7springboot.repository")
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

	@Override
	public String getConnectionString() {
		// TODO Auto-generated method stub
		return "couchbase://127.0.0.1";
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return "Administrator";
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return "couchbase";
	}

	@Override
	public String getBucketName() {
		// TODO Auto-generated method stub
		return "udemy";
	}
	@Bean
	public Transactions transactions(final Cluster couchbaseCluster) {
		return Transactions.create(couchbaseCluster, TransactionConfigBuilder.create()
			// The configuration can be altered here, but in most cases the defaults are fine.
			.build());
	}

}
