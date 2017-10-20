package ru.kinolinker.web.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ru.kinolinker.web.dao.entity.Movie;
import ru.kinolinker.web.dao.entity.Person;

@Component
public class LinkerOfPersonsUtil {

	private static final Logger logger = Logger.getLogger(LinkerOfPersonsUtil.class);
	
	class Comp implements Comparator<Person> {
	      
	       @Override
	       public int compare(Person personOne, Person personTwo) {
	             int k = 0;
	             if(personOne.getRating()<personTwo.getRating()){
	            	 k = 1;
	             }
	             if(personOne.getRating()>personTwo.getRating()){
	            	 k = -1;
	             }
	             
	             return k;
	       }

	}
	
	public List<Person> getLinks(Person person){
		Set<Person> persons = new HashSet<>();
		
		for(Movie movie : person.getMovies()){
			persons.addAll(movie.getAllPerson());
		}
		
		List<Person> personsList = new ArrayList<>(persons);
		personsList.remove(person);
		Collections.sort(personsList,new Comp());
		
		return personsList;
	}
}
