package com.udemy.couchbase7springboot.repository;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.udemy.couchbase7springboot.model.Task;

public interface TaskRepository extends CouchbaseRepository<Task, String> {

}
