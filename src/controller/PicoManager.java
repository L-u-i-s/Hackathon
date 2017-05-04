package controller;

import static org.picocontainer.Characteristics.CACHE;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

public class PicoManager {

	private static MutablePicoContainer appContainer = new DefaultPicoContainer();

	public static void register(String path, boolean b) throws ClassNotFoundException, URISyntaxException {
		URL dir = PicoManager.class.getResource(path);
		String pac = path.substring(1, path.length()).replace("/", ".");
		String ext = ".Class";
		findFiles(dir, ext, pac, b);
	}

	public static MutablePicoContainer getPicoContainer() {

		return appContainer;
	}

	private static void findFiles(URL dir, String ext, String paquete, boolean b)
			throws URISyntaxException, ClassNotFoundException {

		Class<?> c = null;

		File file = new File(dir.toURI());
		if (!file.exists())
			System.out.println(dir + " Directory doesn't exists");
		File[] listFiles = file.listFiles(new MyFileNameFilter(ext));
		if (listFiles.length == 0) {
			System.out.println(dir + "doesn't have any file with extension " + ext);
		} else {
			for (File f : listFiles) {
				String nombreClase = paquete.concat(f.getName().substring(0, f.getName().length() - 6));
				c = Class.forName(nombreClase);
				if (!c.isInterface()) {
					if (b) {
						//System.out.println("con cache");
						appContainer.as(CACHE).addComponent(c);
					} else {
						//System.out.println("sin CACHE");
						appContainer.addComponent(c);
					}

				} else {
					//System.out.println(nombreClase + " es una interface");
				}
				//System.out.println("se agrego [ " + nombreClase + " ] ");
			}
		}
	}

	public static class MyFileNameFilter implements FilenameFilter {
		private String ext;

		public MyFileNameFilter(String ext) {
			this.ext = ext.toLowerCase();
		}

		@Override
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith(ext);
		}

	}
}