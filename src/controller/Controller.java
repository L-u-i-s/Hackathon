package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Usuario;

public class Controller {

	HttpServletRequest request;
	HttpServletResponse response;
	Usuario usuario;
	HashMap<String, Object> map = new HashMap<>();

	public Controller() {

	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public double getDoubleParam(String name) {
		double d;
		try {
			d = Double.parseDouble(request.getParameter(name));
		} catch (NumberFormatException nfe) {
			throw new RuntimeException("El parametro " + name + " debe ser un double");
		}
		return d;
	}

	public String getStringOptParam(String name) {
		return request.getParameter(name);
	}

	public String getStringParam(String name) {
		String value = getStringOptParam(name);
		if (value != null) {
			if (value.isEmpty()) {
				throw new RuntimeException("El parametro " + name + " es obligatorio");
			}
			return value.trim();
		}
		return "";
	}

	public Integer getIntegerParam(String name) {
		String value = getStringOptParam(name);
		if (!value.isEmpty()) {
			return Integer.parseInt(value);
		}
		return null;
	}

	public Integer[] getIntegerParams(String name) {
		String[] values = request.getParameterValues(name);
		if (values != null) {
			Integer[] ids = new Integer[values.length];
			int i = 0;
			for (String v : values) {
				ids[i] = Integer.parseInt(v);
				i++;
			}
			if (i == values.length) {
				return ids;
			}
		}else{
			return new Integer[0];
		}
		return null;
	}

	public Date getDateParam(String name) {
		String value = getStringOptParam(name);
		Date date = null;
		if (!value.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = sdf.parse(value);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;

	}

	public Date getDateTimeParam(String name) {
		String value = getStringOptParam(name);
		Date date = null;
		if (!value.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
			try {
				date = sdf.parse(value);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;

	}

	public Date stringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void inicializarUsuario() {
		if (request.getSession().getAttribute("usuario") != null) {
			usuario = (Usuario) request.getSession().getAttribute("usuario");
			map.put("usuario", usuario);
		} else {
			usuario = new Usuario();
		}
	}
}
