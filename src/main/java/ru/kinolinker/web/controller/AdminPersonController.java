package ru.kinolinker.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonView;

import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Person;
import ru.kinolinker.web.jsonview.ViewModel;
import ru.kinolinker.web.service.MovieService;
import ru.kinolinker.web.service.PersonService;

@Controller
@RequestMapping(value = "/admin/persons")
public class AdminPersonController {

	private static final Logger logger = LoggerFactory.getLogger(AdminPersonController.class);

	@Autowired
	PersonService personService;

	@Autowired
	MovieService movieService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {

		String search = request.getParameter("search");
		String page = request.getParameter("page");

		Integer pageCount = 0;
		Integer pageInt = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);

		List<Person> personsList;

		if (search != null && !search.isEmpty()) {
			int sizeList = (int) personService.listPersonsSizeByNameLike(search);

			pageCount = (int) Math.ceil((double) sizeList / 40);

			if (pageInt > pageCount || pageInt < 1)
				pageInt = 1;

			int begin = (pageInt - 1) * 40;
			int size = 40;
			personsList = personService.getPersonsByNameLike(search, begin, size);

			model.addObject("search", search);

		} else {

			String sort = request.getParameter("sort");

			int sizeList = (int) personService.listPersonsSize();

			pageCount = sizeList / 40;

			pageCount = (int) Math.ceil((double) sizeList / 40);

			if (pageInt > pageCount || pageInt < 1)
				pageInt = 1;

			int begin = (pageInt - 1) * 40;
			int size = 40;

			personsList = personService.listPersonsBySort(sort, false, begin, size);

			model.addObject("sort", sort);
		}

		model.addObject("personsList", personsList);
		model.setViewName("admin/admin_persons");

		model.addObject("page", pageInt);
		model.addObject("pageCount", pageCount);
		
		return model;
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
	public ModelAndView getPersonPage(@PathVariable("id") Integer id, ModelAndView model) {

		Person person = personService.getPersonById(id);

		model.addObject("person", person);

		model.setViewName("admin/person_page");

		return model;
	}

	@RequestMapping(value = "/add_person", method = RequestMethod.GET)
	public ModelAndView addPersonPage(ModelAndView model, HttpServletRequest request) {

		Person person = new Person();

		model.addObject("person", person);

		model.setViewName("admin/add_person");

		return model;
	}

	@RequestMapping(value = "/add_person", method = RequestMethod.POST)
	public ModelAndView addPerson(@Valid Person person, BindingResult result, ModelAndView model) {

		if (result.hasErrors()) {

			model.setViewName("admin/add_person");

			return model;

		}
		personService.addPerson(person);

		model.setViewName("redirect:/admin/persons/person/" + person.getId());
		return model;

	}

	@RequestMapping(value = "/upload_image/{id}", method = RequestMethod.GET)
	public ModelAndView uploadImagePage(ModelAndView model, @PathVariable("id") Integer id) {

		model.addObject("personId", id);
		model.setViewName("admin/upload_image_person");

		return model;
	}

	@RequestMapping(value = "/upload_image/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id) {

		String path = personService.updateImage(file, id);
		if (path != null) {

			return new ResponseEntity<>(path, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Select another file", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/update_person", method = RequestMethod.GET)
	public ModelAndView updatePersonPage(@RequestParam("id") Integer id, ModelAndView model) {

		Person person = personService.getPersonById(id);

		model.addObject("person", person);

		model.setViewName("admin/update_person");
		return model;
	}

	@RequestMapping(value = "/update_person", method = RequestMethod.POST)
	public ModelAndView updatePerson(@Valid Person person, BindingResult result, ModelAndView model) {

		if (result.hasErrors()) {

			model.setViewName("admin/update_person");

			return model;

		}

		person.setActorOfMovies(personService.getPersonById(person.getId()).getActorOfMovies());
		person.setDirectorOfMovies(personService.getPersonById(person.getId()).getDirectorOfMovies());
		personService.updatePerson(person);

		model.setViewName("redirect:/admin/persons/person/" + person.getId());
		return model;
	}

	@RequestMapping(value = "/delete_person", method = RequestMethod.POST)
	public ModelAndView deletePerson(@RequestParam("id") Integer id, ModelAndView model) {

		personService.removePerson(id);

		model.setViewName("redirect:/admin/persons");
		return model;
	}

	@JsonView(ViewModel.Low.class)
	@RequestMapping(value = "/add_amovie", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Movie addAMovie(HttpServletRequest request, ModelAndView model) {

		Integer idPerson = Integer.parseInt(request.getParameter("idPerson"));
		Movie movie = movieService.getMovieById(Integer.parseInt(request.getParameter("idMovie")));
		personService.addAMovie(idPerson, movie);
		return movie;

	}

	@JsonView(ViewModel.Low.class)
	@RequestMapping(value = "/add_dmovie", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Movie addDMovie(HttpServletRequest request, ModelAndView model) {

		Integer idPerson = Integer.parseInt(request.getParameter("idPerson"));
		Movie movie = movieService.getMovieById(Integer.parseInt(request.getParameter("idMovie")));
		personService.addDMovie(idPerson, movie);
		return movie;

	}

	@RequestMapping(value = "/delete_dmovie", method = RequestMethod.POST)
	public ResponseEntity<String> deleteDMovie(HttpServletRequest request, HttpServletResponse response) {

		Integer idPerson = Integer.parseInt(request.getParameter("idPerson"));
		Movie movie = movieService.getMovieById(Integer.parseInt(request.getParameter("idMovie")));
		personService.deleteDMovie(idPerson, movie);

		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/delete_amovie", method = RequestMethod.POST)
	public ResponseEntity<String> deleteActor(HttpServletRequest request, ModelAndView model) {

		Integer idPerson = Integer.parseInt(request.getParameter("idPerson"));
		Movie movie = movieService.getMovieById(Integer.parseInt(request.getParameter("idMovie")));
		personService.deleteAMovie(idPerson, movie);

		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

	}

}
