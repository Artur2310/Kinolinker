package ru.kinolinker.web.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import ru.kinolinker.web.jsonview.ViewModel;
import ru.kinolinker.web.jsonview.ViewPerson;

@Entity
@Table(name = "person")
public class Person implements Serializable{

	@Id
	@Column(name = "id")  
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@JsonView({ViewModel.Total.class,ViewModel.Low.class,ViewPerson.ListView.class})
	private int id;
	
	@Column(name = "URL")
	@JsonView({ViewModel.Total.class,ViewModel.Low.class}) 
	private String url; 

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "PICTURE_PATH")
	@JsonView({ViewModel.Low.class,ViewPerson.ListView.class})
	private String picturePath;

	@Column(name = "NAME", nullable = false)
	@NotEmpty(message="Не может быть пустым!") 
	@JsonView({ViewModel.Low.class,ViewPerson.ListView.class})
	@Index(name="nameIndex")
	private String name;
	 
	@Column(name = "RATING")
	@JsonView({ViewModel.Total.class,ViewModel.Low.class,ViewPerson.TotalView.class})
	private int rating;

	@ManyToMany(fetch = FetchType.LAZY ) 
	@JoinTable(name = "actor_movie", joinColumns = @JoinColumn(name = "actor_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	@JsonView({ViewModel.Total.class,ViewPerson.TotalView.class})
	private Set<Movie> actorOfMovies = new HashSet<Movie>();
	 
	@ManyToMany(fetch = FetchType.LAZY)  
	@JoinTable(name = "director_movie", joinColumns = @JoinColumn(name = "director_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	@JsonView({ViewModel.Total.class,ViewPerson.TotalView.class})
	private Set<Movie> directorOfMovies = new HashSet<Movie>();

	public Person() {

	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {

		return id + " " + name + " " + picturePath;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}



	public Set<Movie> getActorOfMovies() {
		return actorOfMovies;
	}

	public void setActorOfMovies(Set<Movie> actorOfMovies) {
		this.actorOfMovies = actorOfMovies;
	}

	public Set<Movie> getDirectorOfMovies() {
		return directorOfMovies;
	}

	public void setDirectorOfMovies(Set<Movie> directorOfMovies) {
		this.directorOfMovies = directorOfMovies;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((picturePath == null) ? 0 : picturePath.hashCode());
		result = prime * result + rating;
		return result;
	}

   

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (picturePath == null) {
			if (other.picturePath != null)
				return false;
		} else if (!picturePath.equals(other.picturePath))
			return false;
		if (rating != other.rating)
			return false;
		return true;
	}


	public List<Movie> getAMoviesList(){
		return new ArrayList<>(actorOfMovies);
	}
	
	public List<Movie> getDMoviesList(){
		return new ArrayList<>(directorOfMovies);
	}
	
    public List<Movie> getMovies(){
    	
    	Set<Movie> movies = new HashSet<>();
    	movies.addAll(directorOfMovies);
    	movies.addAll(actorOfMovies);
    	 
    	return new ArrayList<Movie>(movies);
    }
    
    
    public static final Comparator<Movie> COMPARE_BY_DATE = new Comparator<Movie>() {
        @Override
        public int compare(Movie one, Movie two) {
            return two.getReleaseDate().compareTo(one.getReleaseDate());
        }
    };
        
    public List<Movie> getMoviesSortByDate(){
    	List <Movie> movies = getMovies();
    	Collections.sort(movies,COMPARE_BY_DATE);
    	return movies;
    }
}
