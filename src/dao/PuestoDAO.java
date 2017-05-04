package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Puesto;

public class PuestoDAO extends GenericDAO<Puesto, Integer> {
	
	public List<Puesto> getAllPuestosByMercadoId(Integer id, Integer cat_id) {
		Session session = sessionFactory.openSession();
		List<Puesto> list = null;
		try {
			Query q = session.createQuery("SELECT p FROM Puesto p where p.mercado.id=:id and p.categoria.id=:cat_id");
			q.setParameter("id", id);
			q.setParameter("cat_id", cat_id);
			list = q.getResultList();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;

	}

	public List<Puesto> getByCategoriaId(Integer id) {
		Session session = sessionFactory.openSession();
		List<Puesto> list = null;
		try {
			Query q = session.createQuery("SELECT p FROM Puesto p where p.categoria.id=:id");
			q.setParameter("id", id);
			list = q.getResultList();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
}
