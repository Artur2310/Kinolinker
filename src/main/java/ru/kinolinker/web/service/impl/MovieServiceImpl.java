package ru.kinolinker.web.service.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ru.kinolinker.web.dao.MovieDao;
import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Person;
import ru.kinolinker.web.dao.impl.MovieDaoImpl;
import ru.kinolinker.web.dao.impl.PersonDaoImpl;
import ru.kinolinker.web.service.MovieService;
import ru.kinolinker.web.util.ImageUtil;
import ru.kinolinker.web.util.MovieImageUtil;
import ru.kinolinker.web.util.ParserIMDBUtil;
import ru.kinolinker.web.util.PersonImageUtil;

@Service("movieService")
public class MovieServiceImpl implements MovieService {

	private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

	@Autowired
	MovieDaoImpl movieDao;

	@Autowired
	PersonDaoImpl personDao;

	public MovieDao getMovieDao() {
		return movieDao;
	}

	public void setMovieDao(MovieDaoImpl movieDao) {
		this.movieDao = movieDao;
	}

	@Transactional
	public void addMovie(Movie movie) {

		logger.info(movie.getImdbLink());
		Float rating = null;
		if (movie.getImdbLink() != null && !movie.getImdbLink().isEmpty()) {
			rating = ParserIMDBUtil.parse(movie.getImdbLink());
		}
		if (rating != null)
			movie.setImdb(rating);
		else {
			movie.setImdbLink("");
		}

		this.movieDao.add(movie);
	}

	@Transactional
	public void updateMovie(Movie movie) {

		Float rating = null;
		if (movie.getImdbLink() != null && !movie.getImdbLink().isEmpty()) {
			if (!movieDao.get(movie.getId()).getImdbLink().equals(movie.getImdbLink())) {
				rating = ParserIMDBUtil.parse(movie.getImdbLink());
			}

			if (rating != null)
				movie.setImdb(rating);
			else {
				movie.setImdbLink(movieDao.get(movie.getId()).getImdbLink());
				movie.setImdb(movieDao.get(movie.getId()).getImdb());
			}
		}

		this.movieDao.update(movie);

	}

	@Transactional
	public void removeMovie(int id) {
		String path = movieDao.get(id).getPicturePath();
		if (path!=null && !path.isEmpty())
			ImageUtil.deleteImage(path);

		this.movieDao.delete(id);

	}

	@Transactional
	public void removeAllMovies() {
		this.movieDao.removeAllMovies();
		new MovieImageUtil().deleteAllImages();
	}

	@Transactional
	public List<Movie> getMoviesByTitle(String title) {
		return this.movieDao.getMovieByTitle(title);

	}

	@Transactional
	public List<Movie> getMoviesByTitleLike(String title) {
		return this.movieDao.getMovieByTitleLike(title);

	}
	@Transactional
	public List<Movie> getMoviesByTitle(String title, List<Movie> ex) {
		List<Movie> movies = this.movieDao.getMovieByTitle(title);

		if (movies != null && !movies.isEmpty()) {

			for (Movie temp : ex) {
				for (int i = 0; i < movies.size(); i++) {
					if (movies.contains(temp)) {
						movies.remove(temp);
					}
				}
			}

		}

		return movies;
	}

	@Transactional
	public Movie getMovieById(int id) {

		return this.movieDao.get(id);
	}

	@Transactional
	public List<Movie> getMovieByTitle(String title) {
		return this.movieDao.getMovieByTitle(title);
	}

	@Transactional
	public List<Movie> getMoviesByTitle(String title, Integer beginList, Integer size) {
		if(title!=null && !title.isEmpty()){
			title = title.trim();
			return this.movieDao.getMoviesByTitle(title, beginList, size);	
		}
		return null;
	}
	
	@Transactional
	public List<Movie> getMovieByTitleLike(String title) {
		return this.movieDao.getMovieByTitleLike(title);
	}

	@Transactional
	public List<Movie> getMoviesByTitleLike(String title, Integer beginList, Integer size) {
		if(title!=null && !title.isEmpty()){
			title = title.trim();
			return this.movieDao.getMoviesByTitleLike(title, beginList, size);	
		}
		return null;
	}

	@Transactional
	public List<Movie> listMovie() {

		return this.movieDao.list();
	}

	@Transactional
	public List<Movie> listMovies(String sort,Boolean sortMod, Integer countryId, Integer genreId, Integer beginList, Integer size) {

		if (sort == null || sort.isEmpty())
			sort = "id";
		return this.movieDao.listMovies(sort,sortMod, countryId, genreId, beginList, size);
	}

	@Transactional
	public long listMoviesSize(String sort, Integer countryId, Integer genreId) {

		if (sort == null || sort.isEmpty())
			sort = "id";

		return this.movieDao.listMoviesSize(sort, countryId, genreId);

	}

	@Transactional
	public long listMoviesSize(String title) {

		return this.movieDao.listMoviesSize(title);

	}
	
	@Transactional
	public long listMoviesSizeLike(String title) {

		return this.movieDao.listMoviesSizeLike(title);

	}

	@Transactional
	public Long getCount() {

		return this.movieDao.count();
	}

	@Transactional
	public String updateImage(MultipartFile file, Integer id) {

		if (!file.isEmpty()) {
			try {
				String path = new MovieImageUtil().saveImage(id, file.getBytes());

				Movie movie = movieDao.get(id);
				movie.setPicturePath(path);
				movieDao.update(movie);

				logger.info("You successfully uploaded file=" + path);
				return path;

			} catch (Exception e) {
				logger.info("You failed to upload " + file.getName() + " => " + e.getMessage());
				return null;
			}
		} else {
			logger.info("You failed to upload " + file.getName() + " because the file was empty.");
			return null;
		}
	}
	
	
	@Transactional
	public String updateImage(byte[] file, Integer id) {
		if (file!=null) {
			try {
				String path = new MovieImageUtil().saveImage(id, file);

				Movie movie = movieDao.get(id);
				movie.setPicturePath(path);
				movieDao.update(movie);

				logger.info("You successfully uploaded file=" + path);
				return path;

			} catch (Exception e) {
				logger.info("You failed to upload  => " + e.getMessage());
				return null;
			}
		} else {
			logger.info("You failed to upload  because the file was empty.");
			return null;
		}
	}

	@Override
	@Transactional
	public boolean addActor(Integer idMovie, Person person) {
		Movie movie = movieDao.get(idMovie);

		logger.info(movie.getTitle());
		Set<Person> actorsSet = movie.getActors();
		actorsSet.add(person);
		movie.setActors(actorsSet);
		movieDao.update(movie);
		return true;
	}

	@Override
	@Transactional
	public boolean addDirector(Integer idMovie, Person person) {
		Movie movie = movieDao.get(idMovie);
		logger.info(person.getName());
		Set<Person> directorsSet = movie.getDirectors();
		directorsSet.add(person);
		movie.setDirectors(directorsSet);
		movieDao.update(movie);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteActor(Integer idMovie, Person person) {
		Movie movie = movieDao.get(idMovie);

		logger.info(movie.getTitle());
		Set<Person> actorsSet = movie.getActors();
		actorsSet.remove(person);
		movie.setActors(actorsSet);
		movieDao.update(movie);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteDirector(Integer idMovie, Person person) {
		Movie movie = movieDao.get(idMovie);
		Set<Person> directorsSet = movie.getDirectors();
		directorsSet.remove(person);
		movie.setDirectors(directorsSet);
		movieDao.update(movie);
		return true;
	}

	public PersonDaoImpl getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDaoImpl personDao) {
		this.personDao = personDao;
	}

}
