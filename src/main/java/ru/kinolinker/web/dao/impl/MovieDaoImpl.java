package ru.kinolinker.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import ru.kinolinker.web.dao.MovieDao;
import ru.kinolinker.web.dao.entity.Country;
import ru.kinolinker.web.dao.entity.Country_;
import ru.kinolinker.web.dao.entity.Genre;
import ru.kinolinker.web.dao.entity.Genre_;
import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Movie_;

@Repository
public class MovieDaoImpl extends AbstractDaoImpl<Movie, Integer> implements MovieDao {

	public MovieDaoImpl() {
		Class type = new Movie().getClass();
		this.setType(type);
	}

	public void removeAllMovies() {

		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.movie").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.genre_movie").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.country_movie").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.director_movie").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE movie.actor_movie").executeUpdate();
		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

	}

	// Return list movies with search criteria for admin page
	public List<Movie> listMovies(String sort, Boolean sortMod, Integer countryId, Integer genreId, Integer beginList,
			Integer size) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Movie> movieCriteria = entityManager.getCriteriaBuilder().createQuery(Movie.class);

		Root<Movie> movieRoot = movieCriteria.from(Movie.class);

		movieCriteria.select(movieRoot);
		movieCriteria.distinct(true);

		movieCriteria.where(listMoviesQuery(movieRoot, countryId, genreId));

		if (sortMod) {
			movieCriteria.orderBy(cb.asc(movieRoot.get(sort)));

		} else {
			movieCriteria.orderBy(cb.desc(movieRoot.get(sort)));
		}

		return entityManager.createQuery(movieCriteria).setFirstResult(beginList).setMaxResults(size)
				.getResultList();
	}

	public List<Movie> getMovieByTitle(String title) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Movie> movieLikeCriteria = entityManager.getCriteriaBuilder().createQuery(Movie.class);
		Root<Movie> likeMovieRoot = movieLikeCriteria.from(Movie.class);
		movieLikeCriteria.select(likeMovieRoot);
		movieLikeCriteria.where(cb.like(likeMovieRoot.get(Movie_.title), title + "%"));

		movieLikeCriteria.orderBy(cb.asc(likeMovieRoot.get("id")));

		return entityManager.createQuery(movieLikeCriteria).getResultList();

	}

	public List<Movie> getMoviesByTitle(String title, Integer beginList, Integer size) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Movie> movieLikeCriteria = entityManager.getCriteriaBuilder().createQuery(Movie.class);
		Root<Movie> likeMovieRoot = movieLikeCriteria.from(Movie.class);
		movieLikeCriteria.select(likeMovieRoot);
		movieLikeCriteria.where(cb.like(likeMovieRoot.get(Movie_.title), title + "%"));

		movieLikeCriteria.orderBy(cb.asc(likeMovieRoot.get("id")));

		return entityManager.createQuery(movieLikeCriteria).setFirstResult(beginList).setMaxResults(size)
				.getResultList();

	}
	
	public List<Movie> getMovieByTitleLike(String title) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Movie> movieLikeCriteria = entityManager.getCriteriaBuilder().createQuery(Movie.class);
		Root<Movie> likeMovieRoot = movieLikeCriteria.from(Movie.class);
		movieLikeCriteria.select(likeMovieRoot);
		movieLikeCriteria.where(cb.like(likeMovieRoot.get(Movie_.title), "%" + title + "%"));

		movieLikeCriteria.orderBy(cb.asc(likeMovieRoot.get("id")));

		return entityManager.createQuery(movieLikeCriteria).getResultList();

	}

	public List<Movie> getMoviesByTitleLike(String title, Integer beginList, Integer size) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Movie> movieLikeCriteria = entityManager.getCriteriaBuilder().createQuery(Movie.class);
		Root<Movie> likeMovieRoot = movieLikeCriteria.from(Movie.class);
		movieLikeCriteria.select(likeMovieRoot);
		movieLikeCriteria.where(cb.like(likeMovieRoot.get(Movie_.title), "%" + title + "%"));

		movieLikeCriteria.orderBy(cb.asc(likeMovieRoot.get("id")));

		return entityManager.createQuery(movieLikeCriteria).setFirstResult(beginList).setMaxResults(size)
				.getResultList();

	}

	public long listMoviesSize(String sort, Integer countryId, Integer genreId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> movieCriteria = entityManager.getCriteriaBuilder().createQuery(Long.class);

		Root<Movie> movieRoot = movieCriteria.from(Movie.class);

		movieCriteria.distinct(true);

		movieCriteria.where(listMoviesQuery(movieRoot, countryId, genreId));
		movieCriteria.select(cb.count(movieRoot));

		return entityManager.createQuery(movieCriteria).getSingleResult();
	}

	public long listMoviesSize(String title) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> movieLikeCriteria = entityManager.getCriteriaBuilder().createQuery(Long.class);
		Root<Movie> likeMovieRoot = movieLikeCriteria.from(Movie.class);

		movieLikeCriteria.where(cb.like(likeMovieRoot.get(Movie_.title), title + "%"));

		movieLikeCriteria.select(cb.count(likeMovieRoot));

		return entityManager.createQuery(movieLikeCriteria).getSingleResult();
	}
	
	public long listMoviesSizeLike(String title) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> movieLikeCriteria = entityManager.getCriteriaBuilder().createQuery(Long.class);
		Root<Movie> likeMovieRoot = movieLikeCriteria.from(Movie.class);

		movieLikeCriteria.where(cb.like(likeMovieRoot.get(Movie_.title), "%" + title + "%"));

		movieLikeCriteria.select(cb.count(likeMovieRoot));

		return entityManager.createQuery(movieLikeCriteria).getSingleResult();
	}

	private Predicate[] listMoviesQuery(Root<Movie> root, Integer countryId, Integer genreId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		List<Predicate> predicates = new ArrayList<>();

		if (countryId != 0) {
			Join<Movie, Country> countriesJoin = root.join(Movie_.country);

			predicates.add(cb.equal(countriesJoin.get(Country_.id), countryId));
		}

		if (genreId != 0) {
			Join<Movie, Genre> genresJoin = root.join(Movie_.genre);

			predicates.add(cb.equal(genresJoin.get(Genre_.id), genreId));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
