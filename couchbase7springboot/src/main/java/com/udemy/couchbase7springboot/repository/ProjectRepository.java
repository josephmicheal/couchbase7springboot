package com.udemy.couchbase7springboot.repository;

import java.util.List;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.repository.query.Param;

import com.couchbase.client.java.json.JsonArray;
import com.udemy.couchbase7springboot.model.Project;
import com.udemy.couchbase7springboot.model.ProjectDetailDto;

public interface ProjectRepository extends CouchbaseRepository<Project, String> {

	// SELECT * FROM `default`:`udemy`.`dev`.`project` where name=$1 and
	// _class='com.udemy.couchbase7springboot.model.Project'

	public List<Project> findByName(String name);

	// SELECT * FROM `default`:`udemy`.`dev`.`project` where name LIKE $1 and
	// _class='com.udemy.couchbase7springboot.model.Project'

	public List<Project> findByNameLike(String name);

	// SELECT * FROM `default`:`udemy`.`dev`.`project` where name!=$1 and
	// _class='com.udemy.couchbase7springboot.model.Project'
	public List<Project> findByNameIsNot(String name);
	// SELECT * FROM `default`:`udemy`.`dev`.`project` where REGEXP_LIKE(name,
	// "<regex>") and _class='com.udemy.couchbase7springboot.model.Project'

	public List<Project> findByNameMatches(String regex);

	// SELECT * FROM `default`:`udemy`.`dev`.`project` p WHERE
	// p._class="com.udemy.couchbase7springboot.model.Project" AND name = $1 ORDER
	// BY name DESC
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND  name = $1 ORDER BY name DESC")
	List<Project> findByNameNickel(String name);

	// SELECT * FROM `default`:`udemy`.`dev`.`project` p WHERE
	// p._class="com.udemy.couchbase7springboot.model.Project" AND name LIKE $1

	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND name LIKE '%#{[0]}%'")
	List<Project> findByNameLikeNickel(String name);

	// SELECT COUNT(*) FROM `default`:`udemy`.`dev`.`project` p WHERE
	// p._class="com.udemy.couchbase7springboot.model.Project"
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter}")
	Long countProjectsNickel();

	// SELECT * FROM `default`:`udemy`.`dev`.`project` p WHERE
	// p._class="com.example.couchbase7springboot.model.Project AND p.id in $1"
	@Query("#{#n1ql.selectEntity} WHERE  #{#n1ql.filter} AND  meta().id IN $ids")
	List<Project> findAllByIdsNickel(@Param("ids") JsonArray ids);

	// SELECT * FROM `default`:`udemy`.`dev`.`project` p WHERE
	// p._class="com.udemy.couchbase7springboot.model.Project AND SOME p.country IN
	// countryList SATISFIES country = $1 END
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND (SOME country IN countryList SATISFIES country = $1  END)")
	List<Project> findAllbyCountryNickel(String country);

	// SELECT * FROM `default`:`udemy`.`dev`.`project` p WHERE
	// p._class="com.udemy.couchbase7springboot.model.Project AND META().id IN
	// (SELECT RAW t.pid FROM `default`:`udemy`.`dev`.`task` t where
	// t._class='com.udemy.couchbase7springboot.model.Task' AND t.ownername = $1)
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id IN (SELECT  RAW t.pid FROM `default`:`udemy`.`dev`.`task` t where t._class='com.udemy.couchbase7springboot.model.Task' AND t.ownername = $1) ")
	List<Project> findProjectsTasksOwnedByNickel(String taskowner);

	@Query("SELECT  p.*, META(p).id AS __id, META(p).cas AS __cas  FROM   `default`:`udemy`.`dev`.`task`  t JOIN  `default`:`udemy`.`dev`.`project`  p ON t.pid=META(p).id WHERE t.cost>$1 AND p._class='com.udemy.couchbase7springboot.model.Project' AND t._class='com.udemy.couchbase7springboot.model.Task'")
	List<Project> findProjectTasksCostGreaterThanNickel(Long taskCost);

	@Query("#{#n1ql.delete} WHERE meta().id = $1")
	void deleteProjectNickel(String id);

	@Query("SELECT META(p).id AS __id, META(p).cas AS __cas, p.name, p.code FROM  `default`:`udemy`.`dev`.`project` p   WHERE #{#n1ql.filter} AND  p.name = $1 ORDER BY p.name DESC")
	List<ProjectDetailDto> findByNameProjection(String name);

}
