package ru.kinolinker.web.dao.entity;

import java.util.HashSet;
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

import com.fasterxml.jackson.annotation.JsonView;

import ru.kinolinker.web.jsonview.ViewModel;
import ru.kinolinker.web.jsonview.ViewPerson;



@Entity
@Table(name = "country")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "COUNTRY_ID")
	private long id;

	@Column(name = "COUNTRY_NAME")
	@JsonView({ViewModel.Total.class,ViewModel.Medium.class})
	private String name;
	

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "country_movie", joinColumns = @JoinColumn(name = "country_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))	@JsonView(ViewPerson.TotalView.class)
	private Set<Movie> movies = new HashSet<Movie>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Country country = (Country) o;

		if (id != country.id)
			return false;
		if (name != null ? !name.equals(country.name) : country.name != null)
			return false;

		return true;
	}
	
	@Override 
	public String toString() {
		return name;
	}
	
	@Override 
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
