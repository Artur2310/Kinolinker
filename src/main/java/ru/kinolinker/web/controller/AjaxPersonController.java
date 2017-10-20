package ru.kinolinker.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import ru.kinolinker.web.jsonview.ViewPerson;
import ru.kinolinker.web.service.MovieService;
import ru.kinolinker.web.service.PersonService;

@Controller
public class AjaxPersonController {

	@Autowired
	PersonService personService;

	@Autowired
	MovieService movieService;

	private static final Logger logger = LoggerFactory.getLogger(AjaxPersonController.class);

	// Получить список имён для быстрого поиска
	@JsonView(ViewPerson.ListView.class)
	@RequestMapping(value = "/get_persons", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Person> getNames(@RequestParam("term") String name) {

		List<Person> persons = personService.getPersonsByNameLike(name, 0, 20);
		return persons;

	}

	// Получить список имён для быстрого поиска на странице фильма
	@JsonView(ViewModel.Low.class)
	@RequestMapping(value = "/get_persons/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Person> getNamesForMovie(@PathVariable("id") Integer id,
			@RequestParam("term") String name, @RequestParam("role") String role) {

		logger.info("Name "+name);
		Movie movie = movieService.getMovieById(id);
		List<Person> persons = new ArrayList<>();

		if (role.equals("actor")) {

			persons  = personService.getPersonsByNameLike(name,0,25, movie.getActorsList());

		}
		if (role.equals("director")) {
			persons  = personService.getPersonsByNameLike(name,0,25, movie.getDirectorsList());

		}

		return persons;

	}

	// Получение списка фильмов для одного ил двух актёров на главной странице
	@JsonView(ViewModel.Medium.class)
	@RequestMapping(value = "/list_movies_for_pair", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Movie> searchMovies(HttpServletRequest request, HttpServletResponse response) {


		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String nameOne = request.getParameter("nameOne");
		String nameTwo = request.getParameter("nameTwo");

		List<Movie> movieList = personService.getMoviesByName(nameOne, nameTwo);

		

		return movieList;

	}

	// получить Person по имени
	@JsonView(ViewPerson.ListView.class)
	@RequestMapping(value = "/get_person", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Person getPersonByName(HttpServletRequest request) {
		String name = request.getParameter("name");
		List<Person> persons = personService.getPersonsByName(name);
		Person person = null;
		if (persons != null && !persons.isEmpty()) {
			person = persons.get(0);
		}

		return person;
	}

	@JsonView(ViewModel.Low.class)
	@RequestMapping(value = "/get_directors/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Person> getDirectorsByMovie(@PathVariable("id") Integer id) {

		Movie movie = movieService.getMovieById(id);

		List<Person> directors = movie.getDirectorsList();

		return directors;

	}

	@JsonView(ViewModel.Low.class)
	@RequestMapping(value = "/get_actors/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Person> getActorsByMovie(@PathVariable("id") Integer id) {

		Movie movie = movieService.getMovieById(id);

		List<Person> actors = movie.getActorsList();
		return actors;

	}

	@JsonView(ViewModel.Low.class)
	@RequestMapping(value = "/get_links", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Person> getLinksOfPerson(HttpServletRequest request) {

		String name = request.getParameter("name");
		List<Person> persons = personService.linkPersons(name);
		
		if(persons!=null){

	      for(int i = 0; i<persons.size(); i++){
	    	  if(persons.get(i).getPicturePath()==null || persons.get(i).getPicturePath().isEmpty())
	    		  persons.remove(i);
	      }
		}
		return persons;

	}
}
