package ru.kinolinker.web.dao;

import java.util.List;

import ru.kinolinker.web.dao.entity.Person;

public interface PersonDao {
	
	public void removeAllPersons();
	
	public List<Person> getPersonsByName(String name);	
	
	public List<Person> getPersonsByName(String name, Integer beginList, Integer endList);
	
	public List<Person> getPersonsByNameLike(String name, Integer beginList, Integer endList);

    public List<Person> listPersonsBySort(String sort,Boolean sortMod, Integer beginList, Integer endList);
    
    public long listPersonsSize();
    
    public long listPersonsSizeByNameLike(String name);
    
    public long listPersonsSizeByName(String name);

}
