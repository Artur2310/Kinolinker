package ru.kinolinker.web.dao.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Person;

@StaticMetamodel(Person.class)
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public class Person_ {
	public static volatile SingularAttribute<Person, Integer> id;
	public static volatile SingularAttribute<Person, String> picturePath;
	public static volatile SingularAttribute<Person, String> name;
	public static volatile SingularAttribute<Person, String> url;
	public static volatile SingularAttribute<Person, Integer> rating;
	public static volatile SetAttribute<Person, Movie> actorOfMovies;
	public static volatile SetAttribute<Person, Movie> directorOfMovies;


}
