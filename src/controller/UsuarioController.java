package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.util.encoders.Hex;

import dao.UsuarioDAO;
import model.Usuario;

@PublicClass
public class UsuarioController extends Controller {
	private UsuarioDAO usuarioDAO;
	//private PersonaDAO personaDAO;

	public UsuarioController(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
		//this.personaDAO = personaDAO;
	}

	public HashMap<String, Object> index() {
		List<Usuario> usuarios = usuarioDAO.getAll();
		map.put("usuarios", usuarios);
		return map;
	}

	public HashMap<String, Object> create() {
//		List<Persona> personas = personaDAO.getInternosSinUsuario();
//		List<Rol> roles = rolDAO.getAll();
//		map.put("personas", personas);
//		map.put("roles", roles);
		return map;
	}

	public int save() {
		int success = 0;
		Integer id = getIntegerParam("id");
		String correo = getStringOptParam("correo").toLowerCase();		
		Integer[] rol_ids = getIntegerParams("roles");		
		Usuario u;
		if (id != null) {
			u = usuarioDAO.getById(id);
		}
		else{
			u = new Usuario();
		}
		u.setCorreo(correo);		
				
//		Rol rol = null;
//		u.setRoles(new ArrayList<Rol>());
//		for(Integer rol_id:rol_ids){
//			rol = new Rol();
//			rol.setId(rol_id);
//			
//			u.getRoles().add(rol);
//		}
//		u.setRol_actual(rol);
//		
//		
//		if (id != null) {
//			success = usuarioDAO.update(u);
//		} else {
//			Persona persona = new Persona();
//			String password = getStringOptParam("password");
//			Integer persona_id = getIntegerParam("persona_id");
//			persona.setId(persona_id);
//			u.setPersona(persona);
//			u.setPassword(sha256(password));
//			success = usuarioDAO.add(u);
//		}
		return success;
	}

	public HashMap<String, Object> edit() {
		Integer id = (Integer) request.getAttribute("id");
		Usuario usuario = null;
		if (id != null) {
			usuario = usuarioDAO.getById(id);
		}
		map = create();
		map.put("user", usuario);
		return map;
	}

	public HashMap<String, Object> delete() {
		Integer id = (Integer) request.getAttribute("id");
		if (id != null) {
			usuarioDAO.deleteById(id);
		}
		return index();
	}

	public int acceso() {
		String correo = getStringParam("username");
		String password = getStringParam("password");

		if (correo != null && password != null) {
			Usuario usuario = null;
			password = sha256(password);
			usuario = usuarioDAO.validarCredenciales(correo, password);
			if (usuario != null) { // acceso
				HttpSession session = request.getSession(true);
				session.setAttribute("usuario", usuario);
				session.setMaxInactiveInterval(300 * 60);

				session.setAttribute("userFiles", new ArrayList<String>());

				/*
				 * Cookie userName = new Cookie("user", username);
				 * userName.setMaxAge(30*60); response.addCookie(userName);
				 */
				return 1;

			} else {
				// Mensaje de error
			}

		}
		return 0;
	}

	private String sha256(String base) {
		try {
			byte[] data = base.getBytes();
			SHA256Digest digest = new SHA256Digest();
			digest.update(data, 0, data.length);
			byte[] res = new byte[32];
			digest.doFinal(res, 0);
			return new String(Hex.encode(res));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void login() {
		// if(session!=null) return petController.index();
	}

	public void isValidSession() {
		HttpSession session = request.getSession(false);
		if (session == null) {
			try {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void signup() {

	}

	public void logout() {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}
}
