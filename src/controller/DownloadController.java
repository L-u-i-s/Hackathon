package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;

@MultiPart
public class DownloadController extends Controller {
	public void getFile() {
		String upload_dir = request.getServletContext().getAttribute("upload_dir").toString();
		getF(upload_dir);
	}

	public void getFile2() {
		String upload_dir = request.getAttribute("upload_dir2").toString();
		getF(upload_dir);
	}
	
	public void getFileMercado() {
		String upload_dir = request.getAttribute("upload_dirMercado").toString();
		getF(upload_dir);
	}

	private void getF(String upload_dir) {
//		String filename = getStringOptParam("filename");
//		Integer anexo_id = getIntegerParam("anexo_id");
//		String ext = filename.substring(filename.lastIndexOf("."));
//		String realFilename = anexo_id + ext;
		Integer puesto_id = getIntegerParam("puesto_id");
		String filePath = upload_dir + File.separator + puesto_id+ ".png";
		File downloadFile = new File(filePath);
		try {
			FileInputStream inStream;

			inStream = new FileInputStream(downloadFile);

			ServletContext context = request.getServletContext();
			String mimeType = context.getMimeType(filePath);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			System.out.println("MIME type: " + mimeType);

			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", puesto_id+".png");
			response.setHeader(headerKey, headerValue);

			OutputStream outStream;

			outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inStream.close();
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
