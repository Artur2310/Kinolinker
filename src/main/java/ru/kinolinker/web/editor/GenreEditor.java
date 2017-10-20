package ru.kinolinker.web.editor;

import java.beans.PropertyEditorSupport;

import ru.kinolinker.web.dao.entity.Genre;
import ru.kinolinker.web.service.GenreService;

public class GenreEditor extends PropertyEditorSupport{

	private GenreService genreService;

    public GenreEditor(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
    
    	Genre genre = genreService.getGenre(Long.parseLong(text));
        	
            this.setValue(genre);
        
    }

    @Override
    public String getAsText() {
    	Genre parent = new Genre();
        if (this.getValue() != null) {
            parent = (Genre) this.getValue();
        }
        return "";
    } 
    
}
