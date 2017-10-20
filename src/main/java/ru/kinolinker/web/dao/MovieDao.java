package ru.kinolinker.web.dao;

import java.util.List;

import ru.kinolinker.web.dao.entity.Movie;

public interface MovieDao {

	public void removeAllMovies();

	public List<Movie> getMovieByTitle(String title);

	public List<Movie> getMoviesByTitle(String title, Integer beginList, Integer size);

	public List<Movie> getMovieByTitleLike(String title);

	public List<Movie> getMoviesByTitleLike(String title, Integer beginList, Integer size);

	public List<Movie> listMovies(String sort, Boolean sortMod, Integer countryId, Integer genreId, Integer beginList,
			Integer size);

	public long listMoviesSize(String sort, Integer countryId, Integer genreId);

	public long listMoviesSize(String title);
	
	public long listMoviesSizeLike(String title);


}
