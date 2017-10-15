
package com.websitename.dao;

import java.io.Serializable;
import java.util.List;



public interface GenericDAO<T, ID> {

	public Serializable save(T entity) throws Exception;

	public void update(T entity) throws Exception;

	public T getById(ID id) throws Exception;
	
	public void deleteById(ID id) throws Exception;

	public void merge(T entity) throws Exception;
	
	public void saveOrUpdate(T entity) throws Exception;
	
	public void delete(T entity) throws Exception;
	
	public List<T> getAll() throws Exception;
	
}
