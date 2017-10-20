package ru.kinolinker.web.service;

import java.util.List;

import ru.kinolinker.web.dao.entity.Country;

public interface CountryService {

	void addCountry(Country country);
	
	public List<Country> listCountry();
	
	public List<Country> listCountry(String sort);
	
	public Country getCountry(Long id);
	
	public void removeAllCountries();
	
	
}
