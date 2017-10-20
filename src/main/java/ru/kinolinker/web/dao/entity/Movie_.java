package ru.kinolinker.web.dao.entity;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import ru.kinolinker.web.dao.entity.Country;
import ru.kinolinker.web.dao.entity.Genre;
import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Person;

@StaticMetamodel(Movie.class)
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public class Movie_ {

	public static volatile SingularAttribute<Movie, Integer> id;
	public static volatile SingularAttribute<Movie, String> url;

	public static volatile SingularAttribute<Movie, String> picturePath;
	public static volatile SingularAttribute<Movie, String> title;
	public static volatile SingularAttribute<Movie, String> alternativeTitle;
	public static volatile SingularAttribute<Movie, Float> imdb;
	public static volatile SingularAttribute<Movie, String> imdbLink;
	public static volatile SingularAttribute<Movie, String> description;
	public static volatile SingularAttribute<Movie, Boolean> dublicatedMovies;
	public static volatile SingularAttribute<Movie, String> trailer;
	public static volatile SingularAttribute<Movie, Date> releaseDate;
	public static volatile SetAttribute<Movie, Person> actors;
	public static volatile SetAttribute<Movie, Person> directors;
	public static volatile SetAttribute<Movie, Country> country; 
	public static volatile SetAttribute<Movie, Genre> genre;

}
