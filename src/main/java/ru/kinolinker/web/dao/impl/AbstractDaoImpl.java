package ru.kinolinker.web.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import ru.kinolinker.web.dao.AbstractDao;

public abstract class AbstractDaoImpl<T, PK extends Serializable> implements AbstractDao<T,PK> {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractDaoImpl.class);

	private Class<T> type;
	
	public void setType(Class<T> type){
		this.type = type;
	}

	@PersistenceContext
	protected EntityManager entityManager;

	public void add(T obj) {
		entityManager.persist(obj);
		entityManager.flush();
		
		logger.info(obj.getClass().getName() + " succesfully saved." + obj.getClass().getName() + " details: " + obj);
	}

	public T delete(PK id) {

		T obj = entityManager.find(type, id);

		if (obj != null) {

			entityManager.remove(obj);
			logger.info(obj.getClass().getName() + " succesfully removed. " + obj.getClass().getName()
					+ " details :" + obj);
			return obj;

		} else {
			logger.info(obj.getClass().getName() + " not found!");
			return null;
		}
	}
	 
	
	public void update(T obj){
		entityManager.merge(obj);
		
		entityManager.flush();
		logger.info(obj.getClass().getName() + " succesfully update. "+obj.getClass().getName() + " details :" + obj);
	}
	
	public T get(PK id){
		T obj = entityManager.find(type,id);

		if (obj!=null) {
	

			logger.info(obj.getClass().getName() +" succesfully loaded. "+obj.getClass().getName() + " details " + obj);

			return obj;
		} else {
			logger.info(obj.getClass().getName() +" not found");
			return null;
		}
	}
	
	public List<T> list(){
		 CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<T> cq = builder.createQuery(type);
		    Root<T> root = cq.from(type);
		    cq.select(root);
		    return entityManager.createQuery(cq).getResultList();
	}
	
	public List<T> list(String sort){
		    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<T> cq = builder.createQuery(type);
		    Root<T> root = cq.from(type);
		    cq.select(root);
			cq.orderBy(builder.asc(root.get(sort)));

		    return entityManager.createQuery(cq).getResultList();
	}
	
	public Long count(){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(type)));

		return entityManager.createQuery(cq).getSingleResult();
	}

}
