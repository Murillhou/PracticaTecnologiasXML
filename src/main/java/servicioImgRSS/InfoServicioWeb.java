package servicioImgRSS;

import java.util.ArrayList;

public class InfoServicioWeb {
	private ArrayList<String> cats; 
	private String nombreServicioWeb;
	private String urlServicioWeb;
	private String tipoServicioWeb;

	public InfoServicioWeb() {
		cats = new ArrayList<String>();
		nombreServicioWeb = "";
		urlServicioWeb = "";
		tipoServicioWeb = "";
	}


	public InfoServicioWeb(String nombreServicioWeb, String urlServicioWeb, String tipoServicioWeb) {
		cats = new ArrayList<String>();
		this.nombreServicioWeb=nombreServicioWeb;
		this.urlServicioWeb=urlServicioWeb;
		this.tipoServicioWeb=tipoServicioWeb;
	}


	public int getNumCats(){
		return cats.size();
	}
	
	public String getCat(int i) {
		return cats.get(i);
	}


	public void addCat(String categoria) {
		cats.add(categoria);
	}


	public String getNombreServicioWeb() {
		return this.nombreServicioWeb;
	}


	public void setNombreServicioWeb(String nombreServicioWeb) {
		this.nombreServicioWeb=nombreServicioWeb;
	}


	public String getURL() {
		return this.urlServicioWeb;
	}

	public void setURL(String url) {
		this.urlServicioWeb=url;
	}

	public String getType() {
		return this.tipoServicioWeb;
	}


	public void setType(String type) {
		this.tipoServicioWeb=type;
	}


}
