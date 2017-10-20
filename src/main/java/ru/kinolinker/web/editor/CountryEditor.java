package ru.kinolinker.web.editor;

import java.beans.PropertyEditorSupport;

import ru.kinolinker.web.dao.entity.Country;
import ru.kinolinker.web.service.CountryService;

public class CountryEditor extends PropertyEditorSupport{

	private CountryService countryService;

    public CountryEditor(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override 
    public void setAsText(String text) throws IllegalArgumentException {
    
        	Country country = countryService.getCountry(Long.parseLong(text));
        	
            this.setValue(country);
        
    }

    @Override
    public String getAsText() {
    	Country parent = new Country();
        if (this.getValue() != null) {
            parent = (Country) this.getValue();
        }
        return "";
    } 
}
