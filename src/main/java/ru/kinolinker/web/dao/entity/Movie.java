package ru.kinolinker.web.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonView;

import ru.kinolinker.web.jsonview.ViewModel;



@Entity
@Table(name = "movie")
public class Movie implements Serializable{

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@JsonView({ViewModel.Total.class,ViewModel.Medium.class,ViewModel.Low.class})
	private int id;
	
	@Column(name = "URL")
	@JsonView(ViewModel.Total.class)
	private String url; 

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "PICTURE_PATH")
	@JsonView({ViewModel.Total.class,ViewModel.Medium.class,ViewModel.Low.class})
	private String picturePath; 
 
	@Column(name = "TITLE", nullable = false)
	@NotEmpty(message="Не может быть пустым!") 
	@JsonView({ViewModel.Total.class,ViewModel.Medium.class,ViewModel.Low.class})
	@Index(name="titleIndex")
	private String title; 

	@Column(name = "ALTER_TITLE")
	@JsonView(ViewModel.Total.class)
	private String alternativeTitle;

	@DecimalMax("10")
	@DecimalMin("0")
	@NumberFormat(style = Style.NUMBER) 
	@Column(name = "IMDB")
	@JsonView({ViewModel.Total.class,ViewModel.Medium.class,ViewModel.Medium.class})
	private float imdb;
  
	@Column(name = "IMDB_LINK")      
	@Pattern(regexp="((?:https?:\\/\\/)?(?:[\\w\\.]+)\\.(?:[a-z]{2,6}\\.?)(?:\\/[\\w\\.]*)*\\/?|^$)")  
	@JsonView(ViewModel.Total.class)
	private String imdbLink;

	@Column(name = "DESCRIPTION", length = 2000)  
	@JsonView(ViewModel.Total.class) 
	private String description;
	 
	@Column(nullable = false,columnDefinition="TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType") 
	@JsonView(ViewModel.Total.class) 
	private boolean dublicatedMovies = false; 
	
 
	@Column(name = "TRAILER", length = 500)
	@JsonView(ViewModel.Total.class)
	private String trailer;

	@Column(name = "RELEASE_DATE") 
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	@Temporal(TemporalType.DATE)  
	@JsonView({ViewModel.Total.class,ViewModel.Medium.class}) 
	private Date releaseDate;

	@ManyToMany( fetch = FetchType.LAZY)  
	@JoinTable(name = "actor_movie", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private Set<Person> actors = new HashSet<Person>();

	@ManyToMany( fetch = FetchType.LAZY)  
	@JoinTable(name = "director_movie", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "director_id"))	
	private Set<Person> directors = new HashSet<Person>();
  
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "country_movie", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "country_id"))
	@JsonView({ViewModel.Total.class,ViewModel.Medium.class})
	private Set<Country> country = new HashSet<Country>();
	  
	@ManyToMany(fetch = FetchType.LAZY)   
	@JoinTable(name = "genre_movie", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
	@JsonView({ViewModel.Total.class,ViewModel.Medium.class}) 
	private Set<Genre> genre = new HashSet<Genre>();
	
 
	public Movie() {

	}
 
	public Movie(int id, String picturePath, String title, float imdb, Set<Genre> genre, Date releaseDate,Set<Country> country) {
		super();
		this.id = id;
		this.picturePath = picturePath;
		this.title = title;
		this.imdb = imdb;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.country = country;
	}

	public String getGenreString() {
		String listGenre = "";

		for (Genre tempGenre : genre) {
			listGenre = listGenre.concat(tempGenre.toString() + " ");
		}
		return listGenre;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isDublicatedMovies() {
		return dublicatedMovies;
	}

	public void setDublicatedMovies(boolean dublicatedMovies) {
		this.dublicatedMovies = dublicatedMovies;
	}

	public float getImdb() {
		return imdb;
	}

	public void setImdb(float imdb) {
		this.imdb = imdb;
	}

	public String getImdbLink() {
		return imdbLink;
	}

	public void setImdbLink(String imdbLink) {
		this.imdbLink = imdbLink;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Person> getActors() {
		return actors;
	}

	public void setActors(Set<Person> persons) {
		this.actors = persons;
	}

	public String toString() {
		return id + " " + title + " " + imdb;
	}

	public boolean equals(Movie movie) {
		return this.id == movie.getId();

	}

	public Set<Person> getPersons() {
		return actors;
	}

	public void setPersons(Set<Person> persons) {
		this.actors = persons;
	}

	public Set<Person> getDirectors() {
		return directors;
	}

	public void setDirectors(Set<Person> directors) {
		this.directors = directors;
	}

	public String getAlternativeTitle() {
		return alternativeTitle;
	}

	public void setAlternativeTitle(String alternativeTitle) {
		this.alternativeTitle = alternativeTitle;
	}

	public void setGenre(Set<Genre> genre) {
		this.genre = genre;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Set<Genre> getGenre() {
		return genre;
	}

	public Set<Country> getCountry() {
		return country;
	} 

	public void setCountry(Set<Country> country) {
		this.country = country;
	}

	
	
	//Вывод всех персон без повторений
	public List<Person> getAllPerson(){
	 	List<Person> persons = new ArrayList<>();
		
		persons.addAll(directors); 
		persons.addAll(actors);
		
		if (dublicatedMovies){
		
			//Удаляем дубликаты
			for(int i=0; i<directors.size(); i++){
				for(int j=directors.size(); j<persons.size();j++){
					if(persons.get(i).equals(persons.get(j))){
						persons.remove(j);
						break;
					}
				}
				
			}
			
		}
		
		return persons;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alternativeTitle == null) ? 0 : alternativeTitle.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (dublicatedMovies ? 1231 : 1237);
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + id;
		result = prime * result + Float.floatToIntBits(imdb);
		result = prime * result + ((imdbLink == null) ? 0 : imdbLink.hashCode());
		result = prime * result + ((picturePath == null) ? 0 : picturePath.hashCode());
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((trailer == null) ? 0 : trailer.hashCode());
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
		Movie other = (Movie) obj;
		if (alternativeTitle == null) {
			if (other.alternativeTitle != null)
				return false;
		} else if (!alternativeTitle.equals(other.alternativeTitle))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dublicatedMovies != other.dublicatedMovies)
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(imdb) != Float.floatToIntBits(other.imdb))
			return false;
		if (imdbLink == null) {
			if (other.imdbLink != null)
				return false;
		} else if (!imdbLink.equals(other.imdbLink))
			return false;
		if (picturePath == null) {
			if (other.picturePath != null)
				return false;
		} else if (!picturePath.equals(other.picturePath))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (trailer == null) {
			if (other.trailer != null)
				return false;
		} else if (!trailer.equals(other.trailer))
			return false;
		return true;
	}
	
	public List<Person> getActorsList(){
		return new ArrayList<>(actors);
	}
	
	public List<Person> getDirectorsList(){
		return new ArrayList<Person>(directors);
	}
}
