package controller;

import java.util.HashMap;
import java.util.List;

import dao.MercadoDAO;
import dao.PuestoDAO;
import model.Mercado;
import model.Puesto;

public class PuestoController extends Controller {
	private MercadoDAO mercadoDAO;
	private PuestoDAO puestoDAO;

	public PuestoController(MercadoDAO mercadoDAO, PuestoDAO puestoDAO) {
		this.mercadoDAO = mercadoDAO;
		this.puestoDAO = puestoDAO;
	}

	public HashMap<String, Object> info() {
		Integer id = getIntegerParam("puesto_id");
		Puesto puesto = puestoDAO.getById(id);
		
		map.put("puesto", puesto);
		return map;
	}
	
	public List<Puesto> getPuestos() {
		Integer id = getIntegerParam("categoria_id");		
		Integer mercado_id = getIntegerParam("mercado_id");
		List<Puesto> puestos = puestoDAO.getAllPuestosByMercadoId(mercado_id,id);
		return puestos;
	}
	
	public HashMap<String, Object> buscarPorProducto() {
		String q = getStringParam("q");		
		List<Puesto> puestos = puestoDAO.buscarPorProducto(q);
		map.put("puestos", puestos);
		return map;
	}
	

}
