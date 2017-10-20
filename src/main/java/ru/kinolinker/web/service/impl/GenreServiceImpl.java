package ru.kinolinker.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kinolinker.web.dao.entity.Genre;
import ru.kinolinker.web.dao.impl.GenreDaoImpl;
import ru.kinolinker.web.service.GenreService;

@Service("genreService")
public class GenreServiceImpl implements GenreService{

	@Autowired
	GenreDaoImpl genreDao;
	
	@Transactional
	public void addGenre(Genre genre) {
		
		genreDao.add(genre);
		
	}

	@Transactional 
	public List<Genre> listGenre() {
		
		return genreDao.list();
		
	}
	
	@Transactional 
	public List<Genre> listGenre(String sort) {
		
		return genreDao.list(sort);
		
	}
	
	@Transactional
	public Genre getGenre(Long id){
		
		return genreDao.get(id);
	}

	@Override
	@Transactional
	public void removeAllGenres() {

		genreDao.removeAllGenres();
	}

}
