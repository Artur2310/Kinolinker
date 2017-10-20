package ru.kinolinker.web.service;

import java.util.List;

import ru.kinolinker.web.dao.entity.Genre;

public interface GenreService {

	void addGenre(Genre genre);
	
	public List<Genre> listGenre();
	
	public List<Genre> listGenre(String sort);
	
	public Genre getGenre(Long id);
	
	public void removeAllGenres();
	
}
