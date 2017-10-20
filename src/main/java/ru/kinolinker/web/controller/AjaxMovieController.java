package ru.kinolinker.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Person;
import ru.kinolinker.web.jsonview.ViewModel;
import ru.kinolinker.web.service.MovieService;
import ru.kinolinker.web.service.PersonService;

@Controller
public class AjaxMovieController {

	@Autowired
	MovieService movieService;

	@Autowired
	PersonService personService;

	private static final Logger logger = LoggerFactory.getLogger(AjaxMovieController.class);

	
	@JsonView(ViewModel.Total.class)
	@RequestMapping(value = "/get_movie/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Movie getMoviesForPerson(@PathVariable("id") Integer id) {

	    Movie movie = movieService.getMovieById(id);

		return movie;

	}
	
	@JsonView(ViewModel.Low.class)
	@RequestMapping(value = "/get_movies/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Movie> getMoviesForPerson(@PathVariable("id") Integer id,
			@RequestParam("term") String title, @RequestParam("role") String role) {

		Person person = personService.getPersonById(id);
		List<Movie> movies = new ArrayList<>();

		if (role.equals("actor")) {

			movies = movieService.getMoviesByTitle(title, person.getAMoviesList());

		}
		if (role.equals("director")) {
			movies = movieService.getMoviesByTitle(title, person.getDMoviesList());

		}

		return movies;

	}

	// Получить список названий фильмов для быстрого поиска на странице персоны
	@JsonView(ViewModel.Low.class)
	@RequestMapping(value = "/get_movies/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Movie> getTitlesForPerson(@PathVariable("id") Integer id,
			@RequestParam("term") String name, @RequestParam("role") String role) {

		Person person = personService.getPersonById(id);
		List<Movie> movies = new ArrayList<>();

		if (role.equals("actor")) {

			movies = movieService.getMoviesByTitle(name, person.getAMoviesList());

		}
		if (role.equals("director")) {
			movies = movieService.getMoviesByTitle(name, person.getDMoviesList());

		}

		return movies;

	}
	
	// Получить список названий фильмов для быстрого поиска на странице персоны
		@JsonView(ViewModel.Medium.class)
		@RequestMapping(value = "/get_movies", method = RequestMethod.POST, produces = "application/json")
		public @ResponseBody List<Movie> getMovies(HttpServletRequest request) {

			String title = request.getParameter("title");
			
			List<Movie> movies = null;
			
			movies = movieService.getMoviesByTitle(title, 0, 90);
			
			return movies;

		}

}
