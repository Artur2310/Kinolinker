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

import ru.kinolinker.web.jsonview.ViewGenre;
import ru.kinolinker.web.jsonview.ViewModel;

@Entity
@Table(name = "genre")
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "ID")
	private long id;

	@Column(name = "GENRE_TITLE")
	@JsonView({ViewModel.Total.class,ViewModel.Medium.class})
	private String title;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "genre_movie", joinColumns = @JoinColumn(name = "genre_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private Set<Movie> movies = new HashSet<Movie>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Genre genre = (Genre) o;

		if (id != genre.id)
			return false;
		if (title != null ? !title.equals(genre.title) : genre.title != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (title != null ? title.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return title;
	}
}
