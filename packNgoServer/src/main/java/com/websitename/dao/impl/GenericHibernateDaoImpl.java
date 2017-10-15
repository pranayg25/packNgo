package com.websitename.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class GenericHibernateDaoImpl<T, ID extends Serializable> {

	@Autowired
	private SessionFactory sessionFactory;
	private Class<T> persistentClass;

	public GenericHibernateDaoImpl() {
		/*
		 * this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
		 * .getGenericSuperclass()).getActualTypeArguments()[0];
		 */
	}

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            class type
	 */
	public GenericHibernateDaoImpl(Class<T> type) {
		this.persistentClass = type;
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @return Retrieves session from session factory
	 */

	protected Session getSession() {
		Session session = getSessionFactory().getCurrentSession();

		return session;

	}

	/**
	 * @param session
	 *            Releases given session
	 */

	protected void closeSession() {

		((SessionFactory) getSessionFactory()).getCurrentSession().close();
	}

	/**
	 * @param id
	 *            - primary key value of entity
	 * @return T - returns entity
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public T getById(ID id) throws Exception {
		Session session = getSession();
		return (T) session.get(getPersistentClass(), id);
	}

	/**
	 * @param entity
	 *            - entity object
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Serializable save(T entity) throws HibernateException {
		return getSession().save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<T> getAll() throws Exception {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		return crit.list();
	}

	/**
	 * @param entity
	 *            - entity object
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(T entity) throws Exception {
		getSession().update(entity);
	}

	/**
	 * @param entity
	 *            - entity object
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveOrUpdate(T entity) throws Exception {

		try {
			getSession().saveOrUpdate(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * @param entity
	 *            - entity object
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(T entity) throws Exception {
		getSession().delete(entity);

	}

	/**
	 * @param id
	 *            - primary key of the entity object
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteById(ID id) throws Exception {

		try {
			getSession().delete(getById(id));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// @Transactional(propagation = Propagation.REQUIRED)
	protected List<T> findByCriteria(Criterion... criterion) throws Exception {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		if (crit.list() == null)
			return new ArrayList<T>();

		return crit.list();
	}

	protected T findByCriteriaWithUniqueResult(Criterion... criterion)
			throws Exception {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}

		if (crit.uniqueResult() == null)
			return getPersistentClass().newInstance();

		return (T) crit.uniqueResult();
	}

	protected List<T> findByCriteria(List<Order> orderList,
			Criterion... criterion) throws Exception {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		for (Order order : orderList) {
			crit.addOrder(order);
		}
		if (crit.list() == null)
			return new ArrayList<T>();

		return crit.list();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	protected List<T> findByCriteria(Map<String, String> aliases,
			Criterion... criterion) throws Exception {
		Criteria crit = getSession().createCriteria(getPersistentClass());

		// Add aliases for associations
		if (aliases != null && aliases.size() > 0) {
			for (String propertyName : aliases.keySet()) {
				String aliasName = aliases.get(propertyName);
				crit.createAlias(propertyName, aliasName);
			}
		}
		// Add criterion/restrictions
		for (Criterion c : criterion) {
			crit.add(c);
		}
		if (crit.list() == null)
			return new ArrayList<T>();

		return crit.list();
	}

	/**
	 * @param exampleInstance
	 *            - entity object with populated properties
	 * @param excludeProperty
	 *            - properties to be excluded
	 * @return
	 */
	// @Transactional(propagation = Propagation.REQUIRED)
	protected List<T> findByExample(T exampleInstance, String[] excludeProperty)
			throws Exception {

		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance).excludeZeroes()
				.enableLike().ignoreCase();
		if (excludeProperty != null) {
			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
		}
		crit.add(example);
		if (crit.list() == null)
			return new ArrayList<T>();
		return crit.list();
	}

	public void merge(T entity) throws Exception {
		getSession().merge(entity);
	}

	protected List<Object[]> executeSQLQueryWithMultipleSelectColumns(
			String sqlQuery) throws Exception {
		return getSession().createSQLQuery(sqlQuery).list();
	}

	protected List<Object> executeSQLQueryWithSingleSelectColumn(String sqlQuery)
			throws Exception {
		return getSession().createSQLQuery(sqlQuery).list();
	}

	protected List<T> queryList(String hqlQuery) throws Exception {
		List<T> list = getSession().createQuery(hqlQuery).list();
		if (list == null)
			list = new ArrayList<T>();

		return list;
	}

	protected int executeUpdate(String hql) throws Exception {

		Query query = getSession().createQuery(hql);
		int rowCount = query.executeUpdate();
		return rowCount;
	}

}
