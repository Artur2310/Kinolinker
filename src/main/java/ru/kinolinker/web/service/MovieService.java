package ru.kinolinker.web.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Person;

public interface MovieService {

	void addMovie(Movie movie);
	
	public void updateMovie(Movie movie);

	public void removeMovie(int id);
	
	public void removeAllMovies();
	
	public List<Movie> getMoviesByTitle(String title);
	
	public List<Movie> getMoviesByTitleLike(String title);
	
	public List<Movie> getMoviesByTitle(String title,List<Movie> ex);
	
	public List<Movie> getMoviesByTitle(String title, Integer beginList, Integer size);
	
	public List<Movie> getMoviesByTitleLike(String title, Integer beginList, Integer size);


	public Movie getMovieById(int id);

	public List<Movie> listMovie();
	
	public List<Movie> listMovies( String sort,Boolean sortMod, Integer countryId, Integer genreId, Integer beginList, Integer size);
	
    public long listMoviesSize(String sort, Integer countryId, Integer genreId);
    
    public long listMoviesSize(String title);
    
    public long listMoviesSizeLike(String title);
	
	public Long getCount();
	
	public String updateImage(MultipartFile file, Integer id);
	
	public String updateImage(byte[] file, Integer id);
	
	public boolean addActor(Integer idMovie, Person person);

	public boolean addDirector(Integer idMovie, Person person);
	
	public boolean deleteActor(Integer idMovie, Person person);

	public boolean deleteDirector(Integer idMovie, Person person);

}
