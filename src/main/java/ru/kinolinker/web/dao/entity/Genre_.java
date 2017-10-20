package ru.kinolinker.web.dao.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import ru.kinolinker.web.dao.entity.Genre;
import ru.kinolinker.web.dao.entity.Movie;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Genre.class)
public class Genre_ {

	public static volatile SingularAttribute<Genre, Long> id;
	public static volatile SingularAttribute<Genre, String> title;
	public static volatile SetAttribute<Genre, Movie> movies;

}
