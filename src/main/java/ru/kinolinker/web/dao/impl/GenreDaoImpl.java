package ru.kinolinker.web.dao.impl;

import org.springframework.stereotype.Repository;

import ru.kinolinker.web.dao.GenreDao;
import ru.kinolinker.web.dao.entity.Genre;

@Repository 
public class GenreDaoImpl extends AbstractDaoImpl<Genre, Long> implements GenreDao{

	public GenreDaoImpl(){
		Class type = new Genre().getClass();
		this.setType(type);
	}
	
	public void removeAllGenres() {

		
		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.genre").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.genre_movie").executeUpdate();
		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

	}
}
