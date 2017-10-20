package ru.kinolinker.web.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ru.kinolinker.web.dao.CountryDao;
import ru.kinolinker.web.dao.entity.Country;
import ru.kinolinker.web.dao.entity.Movie;

@Repository 
public class CountryDaoImpl extends AbstractDaoImpl<Country, Long> implements CountryDao{

	public CountryDaoImpl(){
		
		Class type = new Country().getClass();
		this.setType(type);
	} 

	@Override
	public void removeAllCountries() {
		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.country").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.country_movie").executeUpdate();
		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();		
	}
}
