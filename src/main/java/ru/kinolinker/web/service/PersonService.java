package ru.kinolinker.web.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Person;

public interface PersonService {

	void addPerson(Person person);

	public void updatePerson(Person person);

	public void removePerson(int id);
	
	public void removeAllPersons();

	public Person getPersonById(int id);

	public List<Person> listPerson();
	
	public Long getCount();
	
	public List<Movie> getMoviesByName(String nameOne, String nameTwo);
	
	public List<Person> getPersonsByName(String name);
	
	public List<Person> getPersonsByName(String name,List<Person> ex);
	
	public List<Person> getPersonsByName(String name, Integer beginList, Integer size);
	
	public List<Person> getPersonsByNameLike(String name, Integer beginList, Integer size);
	
	public List<Person> getPersonsByNameLike(String name, Integer beginList, Integer size, List<Person> ex);
	
	public String updateImage(MultipartFile file, Integer id);
	
	public String updateImage(byte[] file, Integer id);
	
	public boolean addAMovie(Integer idPerson, Movie movie);

	public boolean addDMovie(Integer idPerson, Movie movie);
	
	public boolean deleteAMovie(Integer idPerson, Movie movie);

	public boolean deleteDMovie(Integer idPerson, Movie movie);

    public List<Person> listPersonsBySort(String sort,Boolean sortMod, Integer beginList, Integer size);
    
    public long listPersonsSize();
    
    public long listPersonsSizeByName(String name);
    
    public long listPersonsSizeByNameLike(String name);

    
    public List<Person> linkPersons(String name);

}
