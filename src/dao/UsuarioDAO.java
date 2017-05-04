package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario, Integer>{

	public Usuario validarCredenciales(String correo, String password) {
		Session session = sessionFactory.openSession();
		Usuario usuario = null;
		List<?> list = null;
		try {
			Query q = session.createQuery("SELECT u FROM Usuario u where correo=lower(:correo) and password=:password");
			q.setParameter("correo", correo);
			q.setParameter("password", password);
			list = q.getResultList();
			usuario = list.isEmpty() ? null : (Usuario)list.get(0);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return usuario;

	}

	public Usuario getByPersonaId(Integer id) {
		Session session = sessionFactory.openSession();
		Usuario usuario = null;
		List<?> list = null;
		try {
			Query q = session.createQuery("SELECT u FROM Usuario u where u.persona.id=:id");
			q.setParameter("id", id);
			list = q.getResultList();
			usuario = list.isEmpty() ? null : (Usuario)list.get(0);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return usuario;

	}
}
