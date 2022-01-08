package com.udemy.couchbase7springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.queries.ConjunctionQuery;
import com.couchbase.client.java.search.queries.DisjunctionQuery;
import com.couchbase.client.java.search.queries.MatchPhraseQuery;
import com.couchbase.client.java.search.queries.MatchQuery;
import com.couchbase.client.java.search.queries.PrefixQuery;
import com.couchbase.client.java.search.queries.RegexpQuery;
import com.couchbase.client.java.search.queries.WildcardQuery;
import com.couchbase.client.java.search.result.SearchResult;
import com.couchbase.client.java.search.result.SearchRow;
import com.couchbase.transactions.Transactions;
import com.udemy.couchbase7springboot.model.Project;
import com.udemy.couchbase7springboot.model.ProjectDetailDto;
import com.udemy.couchbase7springboot.model.Task;
import com.udemy.couchbase7springboot.repository.ProjectRepository;
import com.udemy.couchbase7springboot.repository.TaskRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	Transactions transactions;
	@Autowired
	Cluster cluster;

	@Autowired
	CouchbaseClientFactory couchbaseClientFactory;

	@Override
	public Project createProject(Project p) {
		// TODO Auto-generated method stub
		return projectRepository.save(p);
	}

	@Override
	public Task createTask(Task t) {
		// TODO Auto-generated method stub
		return taskRepository.save(t);
	}

	@Override
	public Project findById(String id) {
		// TODO Auto-generated method stub
		return projectRepository.findById(id).orElse(null);
	}

	@Override
	public List<Project> findAll() {
		// TODO Auto-generated method stub
		return projectRepository.findAll();
	}

	@Override
	public void deleteProject(String id) {
		// TODO Auto-generated method stub
		projectRepository.deleteById(id);
	}

	@Override
	public List<Project> findByName(String name) {
		// TODO Auto-generated method stub
		return projectRepository.findByName(name);
	}

	@Override
	public List<Project> findByNameLike(String name) {
		// TODO Auto-generated method stub
		name = "%" + name + "%";
		return projectRepository.findByNameLike(name);
	}

	@Override
	public List<Project> findByNameIsNot(String name) {
		// TODO Auto-generated method stub
		return projectRepository.findByNameIsNot(name);
	}

	@Override
	public List<Project> findByNameMatches(String regex) {
		// TODO Auto-generated method stub
		return projectRepository.findByNameMatches(regex);
	}

	@Override
	public List<Project> findByNameNickel(String name) {
		// TODO Auto-generated method stub
		return projectRepository.findByNameNickel(name);
	}

	@Override
	public List<Project> findByNameLikeNickel(String name) {
		// TODO Auto-generated method stub
		return projectRepository.findByNameLikeNickel(name);
	}

	@Override
	public Long countProjectsNickel() {
		// TODO Auto-generated method stub
		return projectRepository.countProjectsNickel();
	}

	@Override
	public List<Project> findAllByIdsNickel(JsonArray ids) {
		// TODO Auto-generated method stub
		return projectRepository.findAllByIdsNickel(ids);
	}

	@Override
	public List<Project> findAllbyCountryNickel(String country) {
		// TODO Auto-generated method stub
		return projectRepository.findAllbyCountryNickel(country);
	}

	@Override
	public List<Project> findProjectsTasksOwnedByNickel(String taskowner) {
		// TODO Auto-generated method stub
		return projectRepository.findProjectsTasksOwnedByNickel(taskowner);
	}

	@Override
	public List<Project> findProjectTasksCostGreaterThanNickel(Long taskCost) {
		// TODO Auto-generated method stub
		return projectRepository.findProjectTasksCostGreaterThanNickel(taskCost);
	}

	@Override
	public void deleteProjectNickel(String id) {
		// TODO Auto-generated method stub
		projectRepository.deleteProjectNickel(id);
	}

	@Override
	public List<ProjectDetailDto> findByNameProjection(String name) {
		// TODO Auto-generated method stub
		return projectRepository.findByNameProjection(name);
	}

	@Override
	public void saveTx(Project p, Task t) {

		transactions.run(ctx -> {
			ctx.insert(couchbaseClientFactory.getBucket().scope("dev").collection("project"), "100", p);
			ctx.insert(couchbaseClientFactory.getBucket().scope("dev").collection("task"), "100", t);
			ctx.commit();
		});

	}

	@Override
	public List<Project> ftsSimpleSearchQuery(String text) {
		String indexName = "project_fts_index";
		MatchQuery query = SearchQuery.match(text).field("name");

		Collection c = cluster.bucket("udemy").scope("dev").collection("project");

		SearchResult result = cluster.searchQuery(indexName, query);
		List<Project> list = new ArrayList<>();
		for (SearchRow r : result.rows()) {

			JsonObject json = c.get(r.id()).contentAsObject();

			Project p = new Project();
			p.set_id(r.id());
			p.setDescription(json.get("desc").toString());
			p.setName(json.get("name").toString());
			list.add(p);
		}

		return list;
	}

	@Override
	public List<Project> ftsPhraseSearchQuery(String text) {
		String indexName = "project_fts_index";

		MatchPhraseQuery mpq = SearchQuery.matchPhrase(text).field("name");
		Collection c = cluster.bucket("udemy").scope("dev").collection("project");

		SearchResult result = cluster.searchQuery(indexName, mpq);
		List<Project> list = new ArrayList<>();
		for (SearchRow r : result.rows()) {

			JsonObject json = c.get(r.id()).contentAsObject();

			Project p = new Project();
			p.set_id(r.id());
			p.setDescription(json.get("desc").toString());
			p.setName(json.get("name").toString());
			list.add(p);
		}

		return list;
	}

	public List<Project> ftsRegexSearchQuery(String text) {
		// ^.*?
		String regex = ".*" + text + ".*";
		String indexName = "project_fts_index";
		RegexpQuery query = SearchQuery.regexp(regex).field("name");
		Collection c = cluster.bucket("udemy").scope("dev").collection("project");
		SearchResult result = cluster.searchQuery(indexName, query);

		List<Project> list = new ArrayList<>();
		for (SearchRow r : result.rows()) {

			JsonObject json = c.get(r.id()).contentAsObject();

			Project p = new Project();
			p.set_id(r.id());
			p.setDescription(json.get("desc").toString());
			p.setName(json.get("name").toString());
			list.add(p);
		}

		return list;
	}

	// ? *
	@Override
	public List<Project> ftsWildCardSearchQuery(String text) {
		String wc = "*" + text + "*";
		String indexName = "project_fts_index";
		WildcardQuery query = SearchQuery.wildcard(wc).field("name");
		Collection c = cluster.bucket("udemy").scope("dev").collection("project");

		SearchResult result = cluster.searchQuery(indexName, query);
		List<Project> list = new ArrayList<>();
		for (SearchRow r : result.rows()) {

			JsonObject json = c.get(r.id()).contentAsObject();

			Project p = new Project();
			p.set_id(r.id());
			p.setDescription(json.get("desc").toString());
			p.setName(json.get("name").toString());
			list.add(p);
		}

		return list;
	}

	@Override
	public List<Project> ftsPrefixSearchQuery(String text) {
		String indexName = "project_fts_index";
		PrefixQuery query = SearchQuery.prefix(text).field("name");
		Collection c = cluster.bucket("udemy").scope("dev").collection("project");

		SearchResult result = cluster.searchQuery(indexName, query);
		List<Project> list = new ArrayList<>();
		for (SearchRow r : result.rows()) {

			JsonObject json = c.get(r.id()).contentAsObject();

			Project p = new Project();
			p.set_id(r.id());
			p.setDescription(json.get("desc").toString());
			p.setName(json.get("name").toString());
			list.add(p);
		}

		return list;
	}

	@Override
	public List<Project> ftsSimpleSearchQueryAll(String text) {
		String indexName = "project_fts_index";
		MatchQuery query = SearchQuery.match(text).field("_all");
		Collection c = cluster.bucket("udemy").scope("dev").collection("project");

		SearchResult result = cluster.searchQuery(indexName, query);
		List<Project> list = new ArrayList<>();
		for (SearchRow r : result.rows()) {

			JsonObject json = c.get(r.id()).contentAsObject();

			Project p = new Project();
			p.set_id(r.id());
			p.setDescription(json.get("desc").toString());
			p.setName(json.get("name").toString());
			list.add(p);
		}

		return list;
	}

	@Override
	public List<Project> ftsConjunctSearchQuery(String name, String desc) {

		String indexName = "project_fts_index";
		MatchQuery firstQuery = SearchQuery.match(name).field("name");
		String regex = ".*" + desc + ".*";
		RegexpQuery secondQuery = SearchQuery.regexp(regex).field("desc");
		ConjunctionQuery conjunctionQuery = SearchQuery.conjuncts(firstQuery, secondQuery);

		Collection c = cluster.bucket("udemy").scope("dev").collection("project");

		SearchResult result = cluster.searchQuery(indexName, conjunctionQuery);

		List<Project> list = new ArrayList<>();
		for (SearchRow r : result.rows()) {

			JsonObject json = c.get(r.id()).contentAsObject();

			Project p = new Project();
			p.set_id(r.id());
			p.setDescription(json.get("desc").toString());
			p.setName(json.get("name").toString());
			list.add(p);
		}

		return list;
	}

	@Override
	public List<Project> ftsDisjunctSearchQuery(String name, String desc) {
		// TODO Auto-generated method stub
		String indexName = "project_fts_index";
		MatchQuery firstQuery = SearchQuery.match(name).field("name");
		String regex = ".*" + desc + ".*";
		RegexpQuery secondQuery = SearchQuery.regexp(regex).field("desc");
		DisjunctionQuery disjunctionQuery = SearchQuery.disjuncts(firstQuery, secondQuery);
		Collection c = cluster.bucket("udemy").scope("dev").collection("project");
		SearchResult result = cluster.searchQuery(indexName, disjunctionQuery);

		List<Project> list = new ArrayList<>();
		for (SearchRow r : result.rows()) {

			JsonObject json = c.get(r.id()).contentAsObject();

			Project p = new Project();
			p.set_id(r.id());
			p.setDescription(json.get("desc").toString());
			p.setName(json.get("name").toString());
			list.add(p);
		}

		return list;
	}

}
