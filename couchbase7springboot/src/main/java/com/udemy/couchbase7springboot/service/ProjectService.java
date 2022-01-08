package com.udemy.couchbase7springboot.service;

import java.util.List;

import com.couchbase.client.java.json.JsonArray;
import com.udemy.couchbase7springboot.model.Project;
import com.udemy.couchbase7springboot.model.ProjectDetailDto;
import com.udemy.couchbase7springboot.model.Task;

public interface ProjectService {
	Project createProject(Project p);

	Task createTask(Task t);

	Project findById(String id);

	List<Project> findAll();

	void deleteProject(String id);

	List<Project> findByName(String name);

	List<Project> findByNameLike(String name);

	List<Project> findByNameIsNot(String name);

	List<Project> findByNameMatches(String regex);

	List<Project> findByNameNickel(String name);

	List<Project> findByNameLikeNickel(String name);

	Long countProjectsNickel();

	List<Project> findAllByIdsNickel(JsonArray ids);

	List<Project> findAllbyCountryNickel(String country);

	List<Project> findProjectsTasksOwnedByNickel(String taskowner);

	List<Project> findProjectTasksCostGreaterThanNickel(Long taskCost);

	void deleteProjectNickel(String id);

	List<ProjectDetailDto> findByNameProjection(String name);

	void saveTx(Project p, Task t);

	List<Project> ftsSimpleSearchQuery(String text);

	List<Project> ftsPhraseSearchQuery(String text);

	List<Project> ftsRegexSearchQuery(String text);

	List<Project> ftsWildCardSearchQuery(String text);

	List<Project> ftsPrefixSearchQuery(String text);

	List<Project> ftsSimpleSearchQueryAll(String text);

	List<Project> ftsConjunctSearchQuery(String name, String desc);

	List<Project> ftsDisjunctSearchQuery(String name, String desc);

}
