package servicioImgRSS;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.*;

// Imports para usar GSON


/**
 * Clase que permite analizar en detalle un documento en formato json.
 */
public class JSONNoticiasHandler {
	private URL url;
	private String result;
	private Gson gson;
	/**
	 * Constructor de la clase, inicializa el parser Gson y comprueba la URL pasada como parametro
	 * @param url URL de la fuente 
	 */
	public JSONNoticiasHandler(String url) {
		gson = new Gson();
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	// Dem�s metodos del Parser JSON
	/**
	 * Metodo que evuelve el resultado de parsear el documento JSON y filtrar el resultado adecuadamente,
	 * dandole formato XML.
	 * 
	 * @return result.toString(), String con el resultado en fomrato XML (le faltan la etiqueta
	 * <noticias>, que han de ser añadidas por la clase GestorNoticias (la clase que invoque este método)
	 * 
	 */
	public String getResult() {
		_RSS resultado = null;
		try {
			resultado = gson.fromJson(new InputStreamReader(url.openStream()),
					_RSS.class);
			Entrie[] entries = resultado.getResponseData().getFeed()
					.getEntries();
			for (int i = 0; i < entries.length; i++) {
				result += "\n     <articulo";
				String id = entries[i].getId();
				if(id!=null)
					result += " id=" + id;
				result += ">\n          <titulo>";
				String titulo = entries[i].getTitle();
				if (titulo != null)
					result += titulo;
				result += "</titulo>\n          <descripcion>";
				String descripcion = entries[i].getContentSnippet();
				String creador = entries[i].getAuthor();
				if (descripcion != null && creador != null)
					result += descripcion + creador;
				else if (descripcion != null) 
					result += descripcion;
				else if (creador != null) 
					result += creador;
				result += "</descripcion>\n          <fecha>";
				String fecha = entries[i].getPublishedDate();
				if (fecha != null)
					result += fecha;
				result += "</fecha>\n          <canal>";
				String canal = resultado.getResponseData().getFeed().getTitle();
				if (canal != null)
					result += canal;
				result += "</canal>\n          <categoria>";
				String[] categorias = entries[i].getCategories();
				if (categorias.length > 0) {
					for (int j = 0; j < categorias.length; j++) {
						result += categorias[j];
					}
				}else {
					result += resultado.getResponseData().getFeed().getDescription();
				}
				result += "</categoria>\n          <contenido>";
				String content = entries[i].getContent();
				if (content != null)
				//	result += content;
				result += "</contenido>";
				MediaGroup[] mediagroups = entries[i].getMediaGroups();
				if (mediagroups != null) {
					for (int j = 0; j < mediagroups.length; j++) {
						Content[] contents = mediagroups[j].getContents();
						if (contents != null) {
							for (int k = 0; k < contents.length; k++) {
								String imagen = contents[k].getUrl();
								result += "\n          <imagen>" + imagen
										+ "</imagen>";
							}
						}
					}
				} else {
					result += "\n          <imagen></imagen>";
				}
				result += "\n     </articulo>";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// Clases privadas con la plantilla para GSON.
	// Estas clases representan, en sintaxis Java, el modelo de datos recibido en JSON, estan 
	// organizadas en una estructura de arbol, y cada una implementa los métodos necesarios para
	// acceder a los datos de interes.
	/**
	 * Clases privadas con la plantilla para GSON
	 * 
	 */
	private class Content {
		private String url; // URL de la imagen
		private String medium;

		private String getUrl() {
			return url;
		}

		private String getMedium() {
			return medium;
		}
	}
	/**
	 * Clases privadas con la plantilla para GSON
	 * 
	 */
	private class MediaGroup {
		private Content[] contents;

		private Content[] getContents() {
			return contents;
		}
	}
	/**
	 * Clases privadas con la plantilla para GSON
	 * 
	 */
	private class Entrie { // Articulo
		private MediaGroup[] mediaGroups;
		private String title;
		private String link;
		private String author;
		private String publishedDate;
		private String contentSnippet;
		private String content;
		private String[] categories;
		private String guid;

		private MediaGroup[] getMediaGroups() {
			return mediaGroups;
		}

		private String getTitle() {
			return title;
		}

		private String getLink() {
			return link;
		}

		private String getAuthor() {
			return author;
		}

		private String getPublishedDate() {
			return publishedDate;
		}
		
		public String getContentSnippet() {
			return contentSnippet;
		}

		private String getContent() {
			return content;
		}

		private String[] getCategories() {
			return categories;
		}

		private String getId() {
			return guid;
		}

	}
	/**
	 * Clases privadas con la plantilla para GSON
	 * 
	 */
	private class Feed { // Noticias
		private String title;
		private String description;
		private Entrie[] entries;

		private String getTitle() {
			return title;
		}

		private String getDescription() {
			return description;
		}
		
		private Entrie[] getEntries() {
			return entries;
		}
	}
	/**
	 * Clases privadas con la plantilla para GSON
	 * 
	 */
	private class ResponseData {
		private Feed feed;

		private Feed getFeed() {
			return feed;
		}
	}
	/**
	 * Clase raiz del arbol. Contiene todos los datos necesarios para extraer el resultado.
	 *
	 */
	class _RSS {
		private ResponseData responseData;

		private ResponseData getResponseData() {
			return responseData;
		}
	}
} // Class JSONNoticiasHandler
