package ru.kinolinker.web.dao;

import java.io.Serializable;
import java.util.List;

public interface AbstractDao<T, PK extends Serializable> {

    public void add(T obj);

    public T delete(PK id);

    public void update(T obj);
    
    public T get(PK id);
    
    public List<T> list();
    
    public List<T> list(String sort);
    
    public Long count();
}
