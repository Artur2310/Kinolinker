package ru.kinolinker.web.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ru.kinolinker.web.dao.PersonDao;
import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Movie_;
import ru.kinolinker.web.dao.entity.Person;
import ru.kinolinker.web.dao.entity.Person_;

@Repository
public class PersonDaoImpl extends AbstractDaoImpl<Person, Integer> implements PersonDao {

	public PersonDaoImpl() {
		Class type = new Person().getClass();
		this.setType(type);
	}

	public void removeAllPersons() {

		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.person").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.director_movie").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.actor_movie").executeUpdate();
		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

	}

	public List<Person> getPersonsByName(String name) {

		logger.info("person list :" + name);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> personLikeCriteria = entityManager.getCriteriaBuilder().createQuery(Person.class);
		Root<Person> likePersonRoot = personLikeCriteria.from(Person.class);
		personLikeCriteria.select(likePersonRoot);
		personLikeCriteria.where(cb.like(likePersonRoot.get(Person_.name), name));

		return entityManager.createQuery(personLikeCriteria).getResultList();
	}

	@Override
	public List<Person> getPersonsByName(String name, Integer beginList, Integer endList) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> personLikeCriteria = entityManager.getCriteriaBuilder().createQuery(Person.class);
		Root<Person> likePersonRoot = personLikeCriteria.from(Person.class);
		personLikeCriteria.select(likePersonRoot);
		personLikeCriteria.where(cb.like(likePersonRoot.get(Person_.name), name));

		personLikeCriteria.orderBy(cb.asc(likePersonRoot.get("id")));

		return entityManager.createQuery(personLikeCriteria).setFirstResult(beginList).setMaxResults(endList)
				.getResultList();

	}
	
	@Override
	public List<Person> getPersonsByNameLike(String name, Integer beginList, Integer endList) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> personLikeCriteria = entityManager.getCriteriaBuilder().createQuery(Person.class);
		Root<Person> likePersonRoot = personLikeCriteria.from(Person.class);
		personLikeCriteria.select(likePersonRoot);
		personLikeCriteria.orderBy(cb.desc(likePersonRoot.get("rating")));
		personLikeCriteria.where(cb.like(likePersonRoot.get(Person_.name), "%" + name + "%"));

		return entityManager.createQuery(personLikeCriteria).setFirstResult(beginList).setMaxResults(endList)
				.getResultList();
	}

	@Override
	public List<Person> listPersonsBySort(String sort,Boolean sortMod, Integer beginList, Integer size) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> personCriteria = entityManager.getCriteriaBuilder().createQuery(Person.class);
		Root<Person> personRoot = personCriteria.from(Person.class);
		personCriteria.select(personRoot);
		personCriteria.distinct(true); 


		if(sortMod){
			personCriteria.orderBy(cb.asc(personRoot.get(sort)));

		}else{
			personCriteria.orderBy(cb.desc(personRoot.get(sort)));

		}
		return entityManager.createQuery(personCriteria).setFirstResult(beginList).setMaxResults(size).getResultList();
	}

	@Override
	public long listPersonsSize() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> personCriteria = entityManager.getCriteriaBuilder().createQuery(Long.class);

		Root<Person> personRoot = personCriteria.from(Person.class);

		personCriteria.distinct(true);

		personCriteria.select(cb.count(personRoot));

		return entityManager.createQuery(personCriteria).getSingleResult();
	}

	@Override
	public long listPersonsSizeByNameLike(String name) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> personCriteria = entityManager.getCriteriaBuilder().createQuery(Long.class);

		Root<Person> personRoot = personCriteria.from(Person.class);

		personCriteria.where(cb.like(personRoot.get(Person_.name), "%" + name + "%"));

		personCriteria.distinct(true);

		personCriteria.select(cb.count(personRoot));

		return entityManager.createQuery(personCriteria).getSingleResult();
	}
	
	@Override
	public long listPersonsSizeByName(String name) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> personCriteria = entityManager.getCriteriaBuilder().createQuery(Long.class);

		Root<Person> personRoot = personCriteria.from(Person.class);

		personCriteria.where(cb.like(personRoot.get(Person_.name), name + "%"));

		personCriteria.distinct(true);

		personCriteria.select(cb.count(personRoot));

		return entityManager.createQuery(personCriteria).getSingleResult();
	}

	

}
