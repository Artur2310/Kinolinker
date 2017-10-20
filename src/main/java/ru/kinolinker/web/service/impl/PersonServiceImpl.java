package ru.kinolinker.web.service.impl;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Person;
import ru.kinolinker.web.dao.impl.PersonDaoImpl;
import ru.kinolinker.web.service.PersonService;
import ru.kinolinker.web.util.LinkerOfPersonsUtil;
import ru.kinolinker.web.util.PersonImageUtil;

@Service("personService")
public class PersonServiceImpl implements PersonService {

	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	PersonDaoImpl personDao;

	@Autowired
	LinkerOfPersonsUtil linker;

	public PersonDaoImpl getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDaoImpl actorDao) {
		this.personDao = personDao;
	}

	@Transactional
	public void addPerson(Person person) {
		this.personDao.add(person);
	}

	@Transactional
	public void updatePerson(Person person) {
		this.personDao.update(person);

	}

	@Transactional
	public void removePerson(int id) {
		this.personDao.delete(id);

	}

	@Transactional
	public void removeAllPersons() {
		this.personDao.removeAllPersons();
		new PersonImageUtil().deleteAllImages();
	}

	@Transactional
	public Person getPersonById(int id) {

		return this.personDao.get(id);
	}

	@Transactional
	public List<Person> listPerson() {

		return this.personDao.list();
	}

	@Transactional
	public Long getCount() {

		return this.personDao.count();
	}

	@Transactional
	public List<Movie> getMoviesByName(String nameOne, String nameTwo) {

		Person personOne = null;
		Person personTwo = null;

		
		if ((nameOne == null || nameOne.isEmpty()) && (nameTwo == null || nameTwo.isEmpty())) {
			return null;

		}

		if (nameOne != null && !nameOne.isEmpty()) {
			List<Person> persons = this.getPersonsByName(nameOne);
			if (persons != null && persons.size() > 0) {
				personOne = persons.get(0);
			}
		}

		if (nameTwo != null && !nameTwo.isEmpty()) {
			List<Person> persons = this.getPersonsByName(nameTwo);
			if (persons != null && persons.size() > 0) {
				personTwo = persons.get(0);
			}
		}

		if (personOne != null && personTwo != null) {
			List<Movie> moviesOne = personOne.getMoviesSortByDate();
			List<Movie> moviesTwo = personTwo.getMoviesSortByDate();

			if ((moviesOne != null && !moviesOne.isEmpty()) || (moviesTwo != null && !moviesTwo.isEmpty())) {
				List<Movie> movies = new ArrayList<>();

				for (Movie movie : moviesOne) {
					if (moviesTwo.contains(movie)) {
						movies.add(movie);
					}
				}
				return movies;

			} else {
				return null;
			}

		} else if (personOne == null && personTwo != null) {
			return personTwo.getMoviesSortByDate();
		} else if (personTwo == null && personOne != null) {
			return personOne.getMoviesSortByDate();
		} else if (personOne == null && personTwo == null) {
			return null;
		}

		return null;
	}

	@Transactional
	public List<Person> getPersonsByName(String name) {
		if (name != null && !name.isEmpty()) {
			return this.personDao.getPersonsByName(name.trim());
		}
		return null;
	}

	@Transactional
	public String updateImage(MultipartFile file, Integer id) {
		if (!file.isEmpty()) {
			try {
				String path = new PersonImageUtil().saveImage(id, file.getBytes());

				Person person = personDao.get(id);
				person.setPicturePath(path);
				personDao.update(person);

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
		if (file != null) {
			try {
				String path = new PersonImageUtil().saveImage(id, file);

				Person person = personDao.get(id);
				person.setPicturePath(path);
				personDao.update(person);

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

	// Список персон с вычетом имеющихся на странице
	@Transactional
	@Override
	public List<Person> getPersonsByName(String name, List<Person> ex) {

		List<Person> persons = this.personDao.getPersonsByName(name);

		if (persons != null && !persons.isEmpty()) {

			for (Person temp : ex) {
				for (int i = 0; i < persons.size(); i++) {
					if (persons.contains(temp)) {
						persons.remove(temp);
					}
				}
			}

		}

		return persons;
	}

	@Transactional
	@Override
	public boolean addAMovie(Integer idPerson, Movie movie) {
		Person person = personDao.get(idPerson);

		logger.info(person.getName());
		Set<Movie> moviesSet = person.getActorOfMovies();
		moviesSet.add(movie);
		person.setActorOfMovies(moviesSet);
		personDao.update(person);
		return true;
	}

	@Transactional
	@Override
	public boolean addDMovie(Integer idPerson, Movie movie) {
		Person person = personDao.get(idPerson);

		logger.info(person.getName());
		Set<Movie> moviesSet = person.getDirectorOfMovies();
		moviesSet.add(movie);
		person.setDirectorOfMovies(moviesSet);
		personDao.update(person);
		return true;
	}

	@Transactional
	@Override
	public boolean deleteAMovie(Integer idPerson, Movie movie) {
		Person person = personDao.get(idPerson);

		logger.info(person.getName());
		Set<Movie> moviesSet = person.getActorOfMovies();
		moviesSet.remove(movie);
		person.setActorOfMovies(moviesSet);
		personDao.update(person);
		return true;
	}

	@Transactional
	@Override
	public boolean deleteDMovie(Integer idPerson, Movie movie) {
		Person person = personDao.get(idPerson);

		logger.info(person.getName());
		Set<Movie> moviesSet = person.getDirectorOfMovies();
		moviesSet.remove(movie);
		person.setDirectorOfMovies(moviesSet);
		personDao.update(person);
		return true;
	}

	@Override
	@Transactional
	public List<Person> getPersonsByName(String name, Integer beginList, Integer size) {

		return personDao.getPersonsByName(name, beginList, size);
	}

	@Override
	@Transactional
	public List<Person> getPersonsByNameLike(String name, Integer beginList, Integer size) {

		return personDao.getPersonsByNameLike(name, beginList, size);
	}
	
	@Override
	@Transactional
	public List<Person> getPersonsByNameLike(String name, Integer beginList, Integer size, List<Person> ex) {

		List<Person> persons = this.personDao.getPersonsByNameLike(name, beginList, size);

		if (persons != null && !persons.isEmpty()) {

			for (Person temp : ex) {
				for (int i = 0; i < persons.size(); i++) {
					if (persons.contains(temp)) {
						persons.remove(temp);
					}
				}
			}

		}

		return persons;
	}

	@Override
	@Transactional
	public List<Person> listPersonsBySort(String sort, Boolean sortMod, Integer beginList, Integer size) {

		if (sort == null || sort.isEmpty())
			sort = "id";
		return this.personDao.listPersonsBySort(sort, sortMod, beginList, size);
	}

	@Override
	@Transactional
	public long listPersonsSize() {
		return this.personDao.listPersonsSize();
	}

	@Override
	@Transactional
	public long listPersonsSizeByName(String name) {
		return this.personDao.listPersonsSizeByName(name);
	}
	
	@Override
	@Transactional
	public long listPersonsSizeByNameLike(String name) {
		return this.personDao.listPersonsSizeByNameLike(name);
	}

	@Override
	@Transactional
	public List<Person> linkPersons(String name) {

		List<Person> persons = personDao.getPersonsByName(name);
		if(persons==null || persons.isEmpty()){
			return null;
		}
		
		Person person = persons.get(0);
		
		persons = linker.getLinks(person);

		if (persons == null || persons.isEmpty()) {
			return null;
		}
		
		int size = (persons.size()>39)?39:persons.size();
		return persons.subList(0, size);
	}

}
