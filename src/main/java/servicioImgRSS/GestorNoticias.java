package servicioImgRSS;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
 
public class GestorNoticias {

	public GestorCategorias gc;

	public GestorNoticias(String iniFile) {
		gc = new GestorCategorias( iniFile );
	}
	
	public String getNoticiasXML(String sCodCat) throws Exception {
		XMLNoticiasHandler  nhXML  = null;
		JSONNoticiasHandler nhJSON = null;

		// Enacabezado del XML de salida
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
				"<!DOCTYPE names [\n" +
				"<!ENTITY nbsp \"&#160;\">\n" +
				"<!ENTITY copy \"&#169;\">\n" +
				"<!ENTITY raquo \"&#187;\">" +
				"<!ENTITY middot \"&#183;\">" +
				"]>\n" +
				"<noticias>\n";
		
		
		// Crear Parser SAX
		SAXParserFactory factoria = SAXParserFactory.newInstance();
		factoria.setNamespaceAware(true);
		SAXParser parser = factoria.newSAXParser();

		// Recorre la lista de servicios WEB que ofrecen noticias de la
		// categor�a pasada como par�metro
		for(InfoServicioWeb iw : gc.getInfoCategoria(sCodCat)) {
		
			if ( iw.getType().equals( "XML" ) ) // Solo los servicios XML
			{
				nhXML = new XMLNoticiasHandler();
				parser.parse (iw.getURL(),nhXML);
				result += nhXML.getResult();
			} 
			else  // Suponemos que es JSON
			{
				nhJSON = new JSONNoticiasHandler(iw.getURL());
				result += nhJSON.getResult();
			}
		}
		
		result += "\n</noticias>"; // Cierre del XML de salida
		return result;
	}

    //----------
    // Segunda parte de la pr�ctica 5
    //----------
	public String getNoticiasHTML() {	
		
		// Procesar el contenido del fichero XML_Transform.xml generado por getNoticiasXML 
		// y generar un fichero HTML que se pueda visualizar en el navegador. Para ello
		// se usar� un fichero de transformaci�n XSL y la clase Trasformer
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = null;
		StreamSource xslStream = new StreamSource("DocXSL.xsl");
		StreamSource xmlStream = new StreamSource("XML_transform.xml");
		StringWriter sw = new StringWriter();
		StreamResult res = new StreamResult(sw);
		
		try {
			// se crea el transformer según las reglas especificadas en el documento de estilo xsl
			t = tf.newTransformer(xslStream);
			// se transforma el contenido del documento xml a un documento html mediante el transformer recien creado
			t.transform(xmlStream, res);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		// se extrae el resultado y se devuelve como un String
		StringBuffer sb = sw.getBuffer();
		return sb.toString();
	}

	public static void main(String[] args) {
		
		if(args.length == 0) {
			System.out.println("Falta el codigo de categoria...");
			System.exit(0);  
		}
		
		// serviciosRSS.xml contiene la lista de servidores
		GestorNoticias gn = new GestorNoticias( "serviciosRSS.xml" );
			
        FileOutputStream ficheroSalida;
        PrintStream flujoSalida;
        try {
            // XML_transform.xml contiene las noticias leidas 
            ficheroSalida = new FileOutputStream( "XML_transform.xml" );
            flujoSalida   = new PrintStream(ficheroSalida);
            String XML_Transformado = gn.getNoticiasXML( args[0]);
            flujoSalida.println ( XML_Transformado );
            ficheroSalida.close();
            flujoSalida.close();
         // Segunda parte de la práctica 5
            String HTML_Transformado = gn.getNoticiasHTML( );
            ficheroSalida = new FileOutputStream( "HTML_transform.html" );
            flujoSalida   = new PrintStream(ficheroSalida);
            flujoSalida.println ( HTML_Transformado );
            ficheroSalida.close();
            flujoSalida.close();

        }catch (Exception e) {
			e.printStackTrace();
            System.err.println ("Error writing to file");
        }
    }

}