package servicioImgRSS;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLNoticiasHandler extends DefaultHandler {
	private String result;
	private String elementoAbierto;
	private String id;
	private String titulo;
	private String descripcion;
	private String creador;
	private String fecha;
	private String canal;
	private String categoria;
	private String contenido;
	private String imagen;

	public XMLNoticiasHandler() {
	}

	@Override
	public void startDocument() {
		elementoAbierto = "";
		result = "";
	}

	@Override
	public void endDocument() {
	}

	@Override
	public void startElement(String espacioNombres, String nomLocal,
			String nomCompleto, Attributes atrs) {
		if (elementoAbierto == "" && nomLocal == "channel") {
			canal = "";
			elementoAbierto = "channel";
		} else if (elementoAbierto == "channel") {
			switch (nomLocal) {
			case "item":
				id = "";
				titulo = "";
				descripcion = "";
				creador = "";
				fecha = "";
				categoria = "";
				contenido = "";
				imagen = "";
				elementoAbierto = "channel/item";
				break;
			case "title":
				if (canal == "") {
					elementoAbierto = "channel/title";
				}
				break;
			}
		} else if (elementoAbierto == "channel/item") {
			switch (nomLocal) {
			case "title":
				elementoAbierto = "channel/item/title";
				break;
			case "guid":
				elementoAbierto = "channel/item/guid";
				break;
			case "creator":
				elementoAbierto = "channel/item/creator";
				break;
			case "description":
				elementoAbierto = "channel/item/description";
				break;
			case "pubDate":
				elementoAbierto = "channel/item/pubDate";
				break;
			case "category":
				elementoAbierto = "channel/item/category";
				break;
			case "content":
				elementoAbierto = "channel/item/content";
				break;
			case "image":
				elementoAbierto = "channel/item/image";
				break;
			}
		} else if (elementoAbierto == "channel/item/image" && nomLocal == "url") {
			elementoAbierto = "channel/item/image/url";
		}
	}

	@Override
	public void characters(char[] ch, int inicio, int longitud) {
		String string = new String(ch, inicio, longitud);
		if (string != "") {
			switch (elementoAbierto) {
			case "channel/title":
				canal += string;
				break;
			case "channel/item/title":
				titulo += string;
				break;
			case "channel/item/guid":
				id += string;
				break;
			case "channel/item/creator":
				creador += string;
				break;
			case "channel/item/description":
				descripcion += string;
				break;
			case "channel/item/pubDate":
				fecha += string;
				break;
			case "channel/item/category":
				categoria += string;
				break;
			case "channel/item/content":
				contenido += string;
				break;
			case "channel/item/image/url":
				imagen += string;
				break;
			}
		}
	}

	@Override
	public void endElement(String espacio, String nomLocal, String nomCompleto) {
		if (elementoAbierto != "") {
			switch (nomLocal) {
			case "title":
				if (elementoAbierto == "channel/title") {
					elementoAbierto = "channel";
				}
				if (elementoAbierto == "channel/item/title") {
					elementoAbierto = "channel/item";
				}
				break;
			case "guid":
				if (elementoAbierto == "channel/item/guid") {
					elementoAbierto = "channel/item";
				}
				break;
			case "creator":
				if (elementoAbierto == "channel/item/creator") {
					elementoAbierto = "channel/item";
				}
				break;
			case "description":
				if (elementoAbierto == "channel/item/description") {
					elementoAbierto = "channel/item";
				}
				break;
			case "pubDate":
				if (elementoAbierto == "channel/item/pubDate") {
					elementoAbierto = "channel/item";
				}
				break;
			case "category":
				if (elementoAbierto == "channel/item/category") {
					elementoAbierto = "channel/item";
				}
				break;
			case "content":
				if (elementoAbierto == "channel/item/content") {
					elementoAbierto = "channel/item";
				}
			case "url":
				if (elementoAbierto == "channel/item/image/url") {
					elementoAbierto = "channel/item";
				}
				break;
			case "item":
				if (elementoAbierto == "channel/item") {
					elementoAbierto = "channel";
					if (id != "") {
						result += "\n     <articulo id=\"" + id + "\">\n";
					} else {
						result += "\n\n     <articulo>\n";
					}
					result += "          <titulo>";
					if (titulo != "") {
						result += titulo;
					}
					result += "</titulo>\n          <descripcion>";
					if (descripcion != "" && creador != "") {
						result += descripcion;
						result += creador;
					} else if (descripcion != "") {
						result += descripcion;
					} else if (creador != "") {
						result += creador;
					}
					result += "</descripcion>\n          <fecha>";
					if (fecha != "") {
						result += fecha;
					}
					result += "</fecha>\n          <canal>";
					if (canal != "") {
						result += canal;
					}
					result += "</canal>\n          <categoria>";
					if (categoria != "") {
						result += categoria;
					}
					result += "</categoria>\n          <contenido>";
					if (contenido != "") {
						result += contenido;
					}
					result += "</contenido>\n          <imagen>";
					if (imagen != "") {
						result += imagen;
					}
					result += "</imagen>\n     </articulo>";
				}
				break;
			case "channel":
				elementoAbierto = "";
				break;
			}
		}
	}

	@Override
	public void ignorableWhitespace(char[] ch, int comienzo, int fin) {
		System.out.println("ignorablewhitespace");
	}

	@Override
	public void skippedEntity(String nombre) {
		System.out.println("skippedentity");
	}

	@Override
	public void error(SAXParseException exc) throws SAXException {
		mostrarError(exc, "Se encontró un error");
	}

	@Override
	public void fatalError(SAXParseException exc) throws SAXException {
		mostrarError(exc, "Se encontró un error fatal");
	}

	public void mostrarError(SAXParseException exc, String aviso)
			throws SAXException {
		System.out.println(aviso + ".  Línea:    " + exc.getLineNumber());
		System.out.println("URI:     " + exc.getSystemId());
		System.out.println("Mensaje: " + exc.getMessage());
		throw new SAXException(aviso);//
	}

	@Override
	public void setDocumentLocator(Locator loc) {

	}

	@Override
	public void processingInstruction(String destino, String datos) {

	}

	@Override
	public void startPrefixMapping(String prefijo, String uri) {

	}

	@Override
	public void endPrefixMapping(String prefijo) {

	}

	public String getResult() {
		return result.toString();
	}
}