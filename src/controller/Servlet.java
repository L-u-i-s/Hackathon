package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import net.minidev.json.JSONValue;

/**
 * Servlet implementation class Servlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 50, // 50 MB
maxRequestSize = 1024 * 1024 * 100) // 100 MB
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Properties pDestinos, pClases;
	private static Logger log4j = Logger.getLogger(Servlet.class.getName());
	private static String upload_dir;
	private static String upload_dir2;
	private static String upload_dirMercado;

	URL url;

	VelocityEngine ve;
	VelocityContext context;
	Template t;
	StringWriter writer;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		System.out.println("INIT");
		String destinospath = config.getInitParameter("destinospath");
		String clasespath = config.getInitParameter("clasespath");
		upload_dir = System.getProperty("user.dir") + File.separator + config.getInitParameter("upload_dir");
		upload_dir2 = System.getProperty("user.dir") + File.separator + config.getInitParameter("upload_dir2");
		upload_dirMercado = System.getProperty("user.dir") + File.separator + config.getInitParameter("upload_dirMercado");
		leerPropiedades(destinospath, clasespath);
		inicializarVelocity();
		inicializarPicoManager();
//		// Inicializar estados
//		EstadoDAO estadoDAO = new EstadoDAO();
//		List<Estado> e = estadoDAO.getAll();
//		HashMap<String, Integer> estados = new HashMap<>();
//		for (Estado estado : e) {
//			estados.put(estado.getNombre(), estado.getId());
//		}
//		this.getServletContext().setAttribute("estados", estados);
		this.getServletContext().setAttribute("upload_dir", upload_dir);
		this.getServletContext().setAttribute("upload_dir2", upload_dir2);
		this.getServletContext().setAttribute("upload_dirMercado", upload_dirMercado);
		
		
		// Inicializar roles de usuario
//		RolDAO rolDAO = new RolDAO();
//		List<Rol> rols = rolDAO.getAll();
//		HashMap<String, Integer> user_roles = new HashMap<>();
//		for (Rol rol : rols) {
//			user_roles.put(rol.getNombre(), rol.getId());
//		}
//		this.getServletContext().setAttribute("user_roles", user_roles);
		// String logproppath = config.getInitParameter("logproppath");
		// Configurator.initialize(null, logproppath); //Se agrego /config al
		// classpath

	}

	

	private void inicializarPicoManager() {
		String p1 = getInitParameter("con_cache");
		String p2 = getInitParameter("sin_cache");
		String array_con_cache[] = p1.split(",");
		String array_sin_cache[] = p2.split(",");
		try {
			for (int i = 0; i < array_con_cache.length; i++) {
				PicoManager.register(array_con_cache[i], true);
			}
			for (int i = 0; i < array_sin_cache.length; i++) {
				PicoManager.register(array_sin_cache[i], false);

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}

	}

	private void inicializarVelocity() {
		// TODO Auto-generated method stub
		url = Servlet.class.getResource("/html");
		ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, url.getPath());
		ve.init();
		context = new VelocityContext();

	}

	private void leerPropiedades(String destinospath, String clasespath) {
		pClases = new Properties();
		InputStream is = Servlet.class.getResourceAsStream(clasespath);
		if (is != null)
			try {
				pClases.load(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			System.out.println("Archivo clases.properties no encontrado");

		pDestinos = new Properties();
		is = Servlet.class.getResourceAsStream(destinospath);
		if (is != null)
			try {
				pDestinos.load(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			System.out.println("Archivo destinos.properties no encontrado");

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String[] path = request.getPathInfo().split("/");
		String className = path[1];
		String methodName = path[2];

		HttpSession session = request.getSession(false);

		context = new VelocityContext();
		String contextPath = request.getContextPath();
		context.put("contextPath", contextPath);

		String servletPath = request.getServletPath();
		String realPath = "";
		if (path.length == 4) {
			int id = 0;
			if (Character.isDigit(methodName.charAt(0))) { // /html/pet/1/services
				id = Integer.parseInt(path[2]);
				methodName = path[3];
				realPath = contextPath + servletPath + "/" + className + "/" + id;
			}

			else if (Character.isDigit(path[3].charAt(0))) { // /html/pet/edit/1
				id = Integer.parseInt(path[3]);
				realPath = contextPath + servletPath + "/" + className;
			}
			request.setAttribute("id", id);
		} else {
			realPath = contextPath + servletPath + "/" + className;
		}
		try {
			Class<?> clazz = Class.forName(pClases.getProperty(className));
//			if (session == null && !clazz.isAnnotationPresent(PublicClass.class)) {
//				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//				return;
//			}
			Controller controller = (Controller) PicoManager.getPicoContainer().getComponent(clazz);
			controller.setRequest(request);
			controller.setResponse(response);
			controller.inicializarUsuario();
			Method m = controller.getClass().getDeclaredMethod(methodName);
			Object o = m.invoke(controller);

			context.put("path", realPath);

			if (servletPath.equals("/json")) {
				if (o != null) {
					OutputStream os = response.getOutputStream();
					response.setContentType("application/json");
					os.write(JSONValue.toJSONString(o).getBytes());
					os.close();
				}
			} else if (servletPath.equals("/html")) {
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				

				if (o instanceof java.util.List) {
					List<?> list = (List<?>) o;
					context.put("list", list);

				} else if (o instanceof HashMap) {
					HashMap<String, Object> map = (HashMap<String, Object>) o;
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						String key = entry.getKey();
						Object value = entry.getValue();
						context.put(key, value);
					}
				} else if (o instanceof Object) {
					context.put(className.toLowerCase(), o);
				}
				t = ve.getTemplate(pDestinos.getProperty(className + "_" + methodName), "UTF-8");
				
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
				try {

					t.merge(context, writer);
				} catch (Exception e) {
					System.out.println("Problem merging template : " + e);
				}
				writer.close();
			}

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * String action = request.getParameter("action"); String forward = "";
		 * 
		 * context = new VelocityContext();
		 * 
		 * if (action.equals("list")) { forward = "1"; List<Persona> pets =
		 * PersonaDAO.getPets(); context.put("petList", pets); //
		 * request.setAttribute("petList", pets); } else if
		 * (action.equals("create")) { forward = "2"; } else if
		 * (action.equals("edit")) { forward = "2"; int id =
		 * Integer.parseInt(request.getParameter("petId")); Persona pet =
		 * PersonaDAO.getPet(id); context.put("pet", pet); //
		 * request.setAttribute("petList", pet); } else if
		 * (action.equals("delete")) { forward = "1"; int id =
		 * Integer.parseInt(request.getParameter("petId"));
		 * PersonaDAO.delete(id); }
		 * 
		 * t = ve.getTemplate(pDestinos.getProperty(forward)); writer = new
		 * StringWriter(); try {
		 * 
		 * t.merge(context, writer); } catch (Exception e) { System.out.println(
		 * "Problem merging template : " + e); }
		 * 
		 * PrintWriter w = response.getWriter(); w.write(writer.toString());
		 */

		// RequestDispatcher requestDispatcher =
		// request.getRequestDispatcher(pDestinos.getProperty(forward));
		// requestDispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		/*
		 * String name = request.getParameter("name"); double price =
		 * Double.parseDouble(request.getParameter("price")); String id =
		 * request.getParameter("petId"); Persona pet = new Persona(name,
		 * price); if (id == null || id.isEmpty()) { PersonaDAO.add(pet); } else
		 * { pet.setId(Integer.parseInt(id)); PersonaDAO.update(pet); }
		 * 
		 * List<Persona> pets = PersonaDAO.getPets(); context = new
		 * VelocityContext(); context.put("petList", pets); t =
		 * ve.getTemplate(pDestinos.getProperty("1")); writer = new
		 * StringWriter(); try {
		 * 
		 * t.merge(context, writer); } catch (Exception e) { System.out.println(
		 * "Problem merging template : " + e); }
		 * 
		 * PrintWriter w = response.getWriter(); w.write(writer.toString());
		 * 
		 * // RequestDispatcher requestDispatcher = //
		 * request.getRequestDispatcher(pDestinos.getProperty("1")); //
		 * requestDispatcher.forward(request, response);
		 */

	}
}
