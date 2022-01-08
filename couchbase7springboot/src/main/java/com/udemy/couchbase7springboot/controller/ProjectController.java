package com.udemy.couchbase7springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.couchbase.client.java.json.JsonArray;
import com.udemy.couchbase7springboot.model.Project;
import com.udemy.couchbase7springboot.model.ProjectDetailDto;
import com.udemy.couchbase7springboot.model.Task;
import com.udemy.couchbase7springboot.service.ProjectService;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;

	@PostMapping(path = "/save")
	public Project createProject(@RequestBody Project project) {

		try {
			return projectService.createProject(project);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Project Related Error", ex);
		}
	}

	@PostMapping(path = "/saveTask")
	public Task createTask(@RequestBody Task task) {
		return projectService.createTask(task);
	}

	@GetMapping(path = "/find/{id}")
	public Project findById(@PathVariable("id") String id) {
		return projectService.findById(id);
	}

	@GetMapping(path = "/find/all")
	public List<Project> findAll() {

		return projectService.findAll();

	}

	@DeleteMapping(path = "/delete/{id}")
	public void deleteById(@PathVariable("id") String id) {

		projectService.deleteProject(id);

	}

	@GetMapping(path = "/find/byName")
	public List<Project> findByName(@RequestParam String name) {

		return projectService.findByName(name);
	}

	@GetMapping(path = "/find/byNameLike")
	public List<Project> findByNameLike(@RequestParam String name) {
		return projectService.findByNameLike(name);
	}

	@GetMapping(path = "/find/byNameIsNot")
	public List<Project> findByNameIsNot(@RequestParam String name) {
		return projectService.findByNameIsNot(name);

	}

	@GetMapping(path = "/find/byNameMacthes")
	public List<Project> findByNameMatches(@RequestParam String name) {

		String regex = "^" + name + ".*";
		return projectService.findByNameMatches(regex);
	}

	@GetMapping(path = "/find/byNameNickel")
	public List<Project> findByNameNickel(@RequestParam String name) {

		return projectService.findByNameNickel(name);
	}

	@GetMapping(path = "/find/byNameLikeNickel")
	public List<Project> findByNameLikeNickel(@RequestParam String name) {

		return projectService.findByNameLikeNickel(name);
	}

	@GetMapping(path = "/find/countAll")
	public Long countProjectsNickel() {

		return projectService.countProjectsNickel();

	}

	@GetMapping(path = "/find/byIdsNickel")
	public List<Project> findAllByIdsNickel(@RequestParam String ids) {
		String params[] = ids.split("\\s*,\\s*");

		JsonArray array = JsonArray.from(params);
		return projectService.findAllByIdsNickel(array);
	}

	@GetMapping(path = "/find/byCountryNickel")
	public List<Project> findAllbyCountryNickel(@RequestParam String country) {

		return projectService.findAllbyCountryNickel(country);
	}

	@GetMapping(path = "/find/byTasksOwnedByNickel")
	public List<Project> findProjectsTasksOwnedByNickel(@RequestParam String taskowner) {

		return projectService.findProjectsTasksOwnedByNickel(taskowner);
	}

	@GetMapping(path = "/find/byTasksCostGreaterThanNickel")
	public List<Project> findProjectTasksCostGreaterThanNickel(@RequestParam String taskcost) {

		return projectService.findProjectTasksCostGreaterThanNickel(Long.parseLong(taskcost));
	}

	@DeleteMapping(path = "/deletenickel/{id}")
	public void deleteProjectNickel(@PathVariable("id") String id) {

		projectService.deleteProjectNickel(id);
	}

	@GetMapping(path = "/find/byNameProjection")
	public List<ProjectDetailDto> findByNameProjection(@RequestParam String name) {

		return projectService.findByNameProjection(name);
	}

	@PostMapping(path = "/create/withtx")
	public void createProjectTaskTx() {

		Project p1 = new Project();
		p1.setName("Project100");
		p1.setCode("100");
		p1.set_id("100");

		Task p2 = new Task();
		p2.setName("Task100");
		p2.setProjectId("100");
		p2.set_id("100");

		projectService.saveTx(p1, p2);

	}

	@GetMapping(path = "/fts/simpleSearch")
	public List<Project> ftsSimpleSearchQuery(@RequestParam String name) {

		return projectService.ftsSimpleSearchQuery(name);
	}

	@GetMapping(path = "/fts/phraseSearch")
	public List<Project> ftsPhraseSearchQuery(@RequestParam String name) {

		return projectService.ftsPhraseSearchQuery(name);
	}

	@GetMapping(path = "/fts/regexSearch")
	public List<Project> ftsRegexSearchQuery(@RequestParam String name) {

		return projectService.ftsRegexSearchQuery(name);
	}

	@GetMapping(path = "/fts/wildCardSearch")
	public List<Project> ftsWildCardSearchQuery(@RequestParam String name) {

		return projectService.ftsWildCardSearchQuery(name);
	}

	@GetMapping(path = "/fts/prefixSearch")
	public List<Project> ftsPrefixSearchQuery(@RequestParam String name) {

		return projectService.ftsPrefixSearchQuery(name);
	}

	@GetMapping(path = "/fts/allSearch")
	public List<Project> ftsSimpleSearchQueryAll(@RequestParam String name) {

		return projectService.ftsSimpleSearchQueryAll(name);
	}

	@GetMapping(path = "/fts/conjunctionSearch")
	public List<Project> ftsConjunctSearchQuery(@RequestParam String name, @RequestParam String desc) {

		return projectService.ftsConjunctSearchQuery(name, desc);
	}

	@GetMapping(path = "/fts/disjunctionSearch")
	public List<Project> ftsDisjunctSearchQuery(@RequestParam String name, @RequestParam String desc) {

		return projectService.ftsDisjunctSearchQuery(name, desc);
	}
}
