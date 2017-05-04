package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Categoria;
import model.Puesto;

public class CategoriaDAO extends GenericDAO<Categoria, Integer> {

	public Categoria getFirst() {
		Session session = sessionFactory.openSession();
		Categoria cat = null;
		try {
			Query q = session.createQuery("FROM Categoria");
			cat = (Categoria)q.getResultList().get(0);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return cat;

	}
	
	
}
