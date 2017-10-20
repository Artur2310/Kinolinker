package ru.kinolinker.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonView;

import ru.kinolinker.web.dao.entity.Country;
import ru.kinolinker.web.dao.entity.Genre;
import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Person;
import ru.kinolinker.web.editor.CountryEditor;
import ru.kinolinker.web.editor.GenreEditor;
import ru.kinolinker.web.jsonview.ViewPerson;
import ru.kinolinker.web.service.CountryService;
import ru.kinolinker.web.service.GenreService;
import ru.kinolinker.web.service.MovieService;
import ru.kinolinker.web.service.PersonService;

@Controller
@RequestMapping(value = "/admin/movies")
public class AdminMovieController {

	private static final Logger logger = LoggerFactory.getLogger(AdminMovieController.class);

	@Autowired
	MovieService movieService;

	@Autowired
	GenreService genreService;

	@Autowired
	CountryService countryService;

	@Autowired
	PersonService personService;

	
	
	// Admin main page (default movies page)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {

		String search = request.getParameter("search");
		String page = request.getParameter("page");

		Integer pageCount = 0;
		Integer pageInt = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);

		List<Movie> movieList;

		if (search != null && !search.isEmpty()) {

			int sizeList = (int) movieService.listMoviesSizeLike(search);
			pageCount = sizeList / 40;

			pageCount = (int) Math.ceil((double) sizeList / 40);

			if (pageInt > pageCount || pageInt < 1)
				pageInt = 1;

			int begin = (pageInt - 1) * 40;
			int size = 40;
			movieList = movieService.getMoviesByTitleLike(search, begin, size);

			model.addObject("search", search);

		} else {
			String sort = request.getParameter("sort");
			String country = request.getParameter("country");
			String genre = request.getParameter("genre");

			Integer countryId = (country == null || country.isEmpty()) ? 0 : Integer.parseInt(country);
			Integer genreId = (genre == null || genre.isEmpty()) ? 0 : Integer.parseInt(genre);

			int sizeList = (int) movieService.listMoviesSize(sort, countryId, genreId);

			pageCount = (int) Math.ceil((double) sizeList / 40);

			if (pageInt > pageCount || pageInt < 1)
				pageInt = 1;

			int begin = (pageInt - 1) * 40;
			int size = 40;

			movieList = movieService.listMovies(sort,false, countryId, genreId, begin, size);

			model.addObject("sort", sort);
			model.addObject("countryId", countryId);
			model.addObject("genreId", genreId);
		}

		model.addObject("page", pageInt);
		
		model.addObject("pageCount", pageCount);

		List<Genre> genreList = genreService.listGenre("title");

		List<Country> countryList = countryService.listCountry("name");

		model.addObject("genreList", genreList);
		model.addObject("countryList", countryList);
		model.addObject("moviesList", movieList);
		model.setViewName("admin/admin_movies");

		return model;
	}

	// Get page with information about the film (by id)
	@RequestMapping(value = "/movie/{id}", method = RequestMethod.GET)
	public ModelAndView getMoviePage(@PathVariable("id") Integer id, ModelAndView model) {

		Movie movie = movieService.getMovieById(id);

		model.addObject("movie", movie);

		model.setViewName("admin/movie_page");

		return model;
	}

	// Get page for adding a new movie
	@RequestMapping(value = "/add_movie", method = RequestMethod.GET)
	public ModelAndView addMoviePage(ModelAndView model, HttpServletRequest request) {

		Movie movie = new Movie();

		List<Genre> genreList = genreService.listGenre();
		List<Country> countryList = countryService.listCountry();

		model.addObject("movie", movie);
		model.addObject("listCountry", countryList);
		model.addObject("listGenre", genreList);
		model.setViewName("admin/add_movie_page");

		return model;
	}

	// Adding a new movie and send to the movie page
	@RequestMapping(value = "/add_movie", method = RequestMethod.POST)
	public ModelAndView addMovie(@Valid Movie movie, BindingResult result, ModelAndView model) {

		if (result.hasErrors()) {

			List<Genre> genreList = genreService.listGenre();
			List<Country> countryList = countryService.listCountry();

			model.addObject("listGenre", genreList);
			model.addObject("listCountry", countryList);

			model.setViewName("admin/add_movie_page");

			return model;

		}
		movieService.addMovie(movie);

		model.setViewName("redirect:/admin/movies/movie/" + movie.getId());
		return model;

	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {

		binder.registerCustomEditor(Country.class, new CountryEditor(countryService));
		binder.registerCustomEditor(Genre.class, new GenreEditor(genreService));

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, orderDateEditor);
	}

	// Update the film poster
	@RequestMapping(value = "/upload_image/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id,
			HttpServletRequest request) {

		String path = movieService.updateImage(file, id);
		if (path != null) {
			return new ResponseEntity<>(path, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Select another file", HttpStatus.BAD_REQUEST);

	}

	// Get a page to update information about the movie (Replace later with a
	// modal window on page)
	@RequestMapping(value = "/update_movie", method = RequestMethod.GET)
	public ModelAndView updateMoviePage(@RequestParam("id") Integer id, ModelAndView model) {

		Movie movie = movieService.getMovieById(id);

		List<Genre> genreList = genreService.listGenre();
		List<Country> countryList = countryService.listCountry();

		model.addObject("movie", movie);
		model.addObject("listCountry", countryList);
		model.addObject("listGenre", genreList);
		model.setViewName("admin/update_movie");
		return model;
	}

	// Update information about the movie
	@RequestMapping(value = "/update_movie", method = RequestMethod.POST)
	public ModelAndView updateMoviePage(@Valid Movie movie, BindingResult result, ModelAndView model) {

		if (result.hasErrors()) {
			List<Genre> genreList = genreService.listGenre();
			List<Country> countryList = countryService.listCountry();

			model.addObject("listGenre", genreList);
			model.addObject("listCountry", countryList);

			model.setViewName("admin/update_movie");

			return model;

		}

		movie.setActors(movieService.getMovieById(movie.getId()).getActors());
		movie.setDirectors(movieService.getMovieById(movie.getId()).getDirectors());
		movieService.updateMovie(movie);

		model.setViewName("redirect:/admin/movies/movie/" + movie.getId());
		return model;
	}

	// Delete the movie
	@RequestMapping(value = "/delete_movie", method = RequestMethod.POST)
	public ModelAndView deleteMovie(@RequestParam("id") Integer id, ModelAndView model) {

		movieService.removeMovie(id);

		model.setViewName("redirect:/admin/movies");
		return model;
	}
 
	// Adding an actor to a movie
	@JsonView(ViewPerson.ListView.class)
	@RequestMapping(value = "/add_actor", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Person addActor(HttpServletRequest request, ModelAndView model) {

		Integer idMovie = Integer.parseInt(request.getParameter("idMovie"));
		Person person = personService.getPersonById(Integer.parseInt(request.getParameter("idPerson")));
		movieService.addActor(idMovie, person);
		return person;

	}

	// Adding a director to a movie
	@JsonView(ViewPerson.ListView.class)
	@RequestMapping(value = "/add_director", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Person addDirector(HttpServletRequest request, ModelAndView model) {

		Integer idMovie = Integer.parseInt(request.getParameter("idMovie"));
		Person person = personService.getPersonById(Integer.parseInt(request.getParameter("idPerson")));
		movieService.addDirector(idMovie, person);
		return person;

	}

	@RequestMapping(value = "/delete_director", method = RequestMethod.POST)
	public ResponseEntity<String> deleteDirector(HttpServletRequest request, HttpServletResponse response) {

		Integer idMovie = Integer.parseInt(request.getParameter("idMovie"));
		Person person = personService.getPersonById(Integer.parseInt(request.getParameter("idPerson")));
		movieService.deleteDirector(idMovie, person);

		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/delete_actor", method = RequestMethod.POST)
	public ResponseEntity<String> deleteActor(HttpServletRequest request, ModelAndView model) {

		Integer idMovie = Integer.parseInt(request.getParameter("idMovie"));
		Person person = personService.getPersonById(Integer.parseInt(request.getParameter("idPerson")));
		movieService.deleteActor(idMovie, person);

		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

	}

}
