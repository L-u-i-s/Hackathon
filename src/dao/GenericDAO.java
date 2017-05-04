package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class GenericDAO<T, ID extends Serializable> implements IGenericDAO<T, ID> {

	protected static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	@Override
	public int add(T entity) { 
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int success=0;
		try {
			tx = session.beginTransaction();
			session.save(entity);
			tx.commit();
			success=1;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}

	@Override
	public int update(T entity) {
		int success=0;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(entity);
			tx.commit();
			success=1;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			success=0;
		} finally {
			session.close();
		}
		return success;
	}

	@Override
	public T getById(ID id) {
		Session session = sessionFactory.openSession();
		T entity = null;
		try {
			entity = (T) session.get(getEntityClass(), id);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return entity;
	}

	@Override
	public void deleteById(ID id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			T entity = (T) session.load(getEntityClass(), id);
			session.delete(entity);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public List<T> getAll() {
		Session session = sessionFactory.openSession();
		List<T> entities = null;
		try {
			entities = session.createQuery("FROM " + getEntityClass().getName()).getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return entities;
	}

	private Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
