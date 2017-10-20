package ru.kinolinker.web.dao.entity;


import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import ru.kinolinker.web.dao.entity.Country;
import ru.kinolinker.web.dao.entity.Movie;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Country.class)
public class Country_ { 

	public static volatile SingularAttribute<Country, Long> id;
	public static volatile SingularAttribute<Country, String> name;

	public static volatile SetAttribute<Country, Movie> movies;



}
