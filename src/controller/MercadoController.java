package controller;

import java.util.HashMap;
import java.util.List;

import dao.CategoriaDAO;
import dao.MercadoDAO;
import dao.PuestoDAO;
import model.Categoria;
import model.Mercado;
import model.Puesto;

public class MercadoController extends Controller {
	private MercadoDAO mercadoDAO;
	private PuestoDAO puestoDAO;
	private CategoriaDAO categoriaDAO;

	public MercadoController(MercadoDAO mercadoDAO, PuestoDAO puestoDAO, CategoriaDAO categoriaDAO) {
		this.mercadoDAO = mercadoDAO;
		this.puestoDAO = puestoDAO;
		this.categoriaDAO = categoriaDAO;
	}

	public HashMap<String, Object> locales() {
		Integer id = getIntegerParam("mercado_id");
		Mercado merc = mercadoDAO.getById(id);
		Categoria cat = categoriaDAO.getFirst();
		List<Puesto> puestos = puestoDAO.getAllPuestosByMercadoId(id,cat.getId());
		List<Categoria> categorias = categoriaDAO.getAll();
		map.put("puestos", puestos);
		map.put("categorias", categorias);
		map.put("mercado", merc);
		return map;
	}
	
	public HashMap<String, Object> inicio() {
		List<Mercado> mercados = mercadoDAO.getAll();
		map.put("mercados", mercados);
		return map;
	}
	
	public HashMap<String, Object> ruta(){
		String nombre = getStringOptParam("nombre");
		map.put("nombre", nombre);
		return map;
	}
	
	public HashMap<String, Object> rutas(){
		return map;
	}

	
	public HashMap<String, Object> create() {
		//List<Puesto> puestos = puestoDAO.getAllActivos();
		//map.put("puestos", puestos);
		return map;
	}




	public int save() {
		int success = 0;
		Integer id = getIntegerParam("id");
		String nombre = getStringOptParam("nombre");
		String appaterno = getStringOptParam("appaterno");
		String apmaterno = getStringOptParam("apmaterno");
		String domicilio = getStringOptParam("domicilio");
		String telefono = getStringOptParam("telefono");
		Integer puesto_id = getIntegerParam("puesto");
		
//		if (id != null) {
//			persona.setId(id);
//			success = mercadoDAO.update(persona);
//
//		} else {
//			success = mercadoDAO.add(persona);
//		}
		return success;
	}


	public HashMap<String, Object> edit() {
		Integer id = (Integer) request.getAttribute("id");
//		Persona persona = null;
//		if (id != null) {
//			persona = mercadoDAO.getById(id);
//		}
//		map = create();
//		map.put("persona", persona);
		return map;
	}

//	public HashMap<String, Object> editAsistente() {
//		Integer id = (Integer) request.getAttribute("id");
//		Usuario user = null;
//		if (id != null) {
//			user = personaDAO.getByIdWithUser(id);
//		}
//		map = createAsistente();
//		map.put("user", user);
//		map.put("asistente", user.getPersona());
//		return map;
//	}

//	public HashMap<String, Object> delete() {
//		Integer id = (Integer) request.getAttribute("id");
//		if (id != null) {
//			mercadoDAO.deleteById(id);
//		}
//		return index();
//	}


//	public Roles home() {
//		String rol = getStringOptParam("rol");
//		System.out.println(rol);
//		Roles roles = new Roles();
//		if (rol == null) {
//			rol = usuario.getRol_actual().getNombre();
//		}
//		boolean a = false;
//		for (Rol r : usuario.getRoles()) {
//			if (r.getNombre().equals(rol)) {
//				a = true;
//				break;
//			}
//		}
//		if (!a) {
//			try {
//				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			if (rol.equalsIgnoreCase("Administrador")) {
//				roles.setClassName("area");
//				roles.setMethodName("index");
//				List<Area> areas = areaDAO.getAll();
//				map.put("areas", areas);
//			} else {
//				roles.setClassName("persona");
//				roles.setMethodName("asuntosRecibidos");
//				map = asuntosRecibidos();
//			}
//			usuario.setRol_actual(rolDAO.getById(getUserRoles().get(rol)));
//			usuarioDAO.update(usuario);
//			map.put("usuario", usuario);
//			roles.setObjeto(map);
//		}
//		return roles;
//
//	}
//
//

}
