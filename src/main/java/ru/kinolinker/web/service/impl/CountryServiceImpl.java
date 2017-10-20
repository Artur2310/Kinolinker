package ru.kinolinker.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kinolinker.web.dao.CountryDao;
import ru.kinolinker.web.dao.entity.Country;
import ru.kinolinker.web.dao.impl.CountryDaoImpl;
import ru.kinolinker.web.service.CountryService;

@Service("countryService")
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryDaoImpl countryDao;
	
	
	
	@Transactional
	public void addCountry(Country country) {
		
		countryDao.add(country);
		
	}

	@Transactional 
	public List<Country> listCountry() {
		
		return countryDao.list();
		
	}
	
	@Transactional 
	public List<Country> listCountry(String sort) {
		
		return countryDao.list(sort);
		
	}
	
	@Transactional
	public Country getCountry(Long id){
		
		return countryDao.get(id);
	}
	
	@Transactional
	public void removeAllCountries(){
		countryDao.removeAllCountries();
	}
}
