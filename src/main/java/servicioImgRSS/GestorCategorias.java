package servicioImgRSS;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GestorCategorias {
   public ArrayList <InfoServicioWeb>  lServicios = new ArrayList <InfoServicioWeb>();
   private static String ficheroXML = "serviciosRSS.xml";

   /**
    * Procesar el fichero XML cuyo nombre se recibe en el par�metro "sFile" 
    *  usando la API DOM y generar la lista de servicios "lServicios"
	 * 
    * @param sFile Fichero XML que contiene la lista de servicios, con sus categorias asociadas
    */
   public GestorCategorias( String sFile /* fichero de configuracion XML */ ) {
	   Document docServicios = null;
	   File xmlFile = new File(sFile);
	   DocumentBuilder builder = null;
	   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
	// Transformación del documento XML a un documento DOM
	   try{
		   builder = factory.newDocumentBuilder();
		   docServicios = builder.parse(xmlFile);
	   } catch (ParserConfigurationException e) {
		   e.printStackTrace();
	   } catch (SAXException e) {
		   e.printStackTrace();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
	   docServicios.getDocumentElement().normalize();
	   
	// Se recorren todos los elementos con el tag <servicio> que cuelgan del elemento raiz
	   NodeList nl = docServicios.getElementsByTagName("servicio");
	   for(int i=0;i<nl.getLength();i++){
		   Node n = nl.item(i);
		   Element e = (Element) n;
	    // Se crea el objeto de la clase InfoServicioWeb y se añade a la lista de servicios
		   InfoServicioWeb iw = new InfoServicioWeb(e.getNodeName(), e.getElementsByTagName("url").item(0).getTextContent(), e.getAttribute("tipo") );
		   iw.addCat(e.getElementsByTagName("codCat").item(0).getTextContent());
		   lServicios.add(iw);
	   } // for
   } // GestorCategorias()
	
   public List <InfoServicioWeb> getInfoCategoria(String sCodCategoria) {
	   ArrayList <InfoServicioWeb>  listaCategoria  = new ArrayList <InfoServicioWeb>();
	   
	   for(InfoServicioWeb iw : lServicios ) {			
		   // System.out.println( "URL: " + iw.getURL() );
		   int nCats = iw.getNumCats();
		   boolean found = false;
		   int i = 0;
		   while( ( i<nCats ) && !found) {		
			   String cat = iw.getCat( i );			
			   if ( cat.equals( sCodCategoria ) ) {
				   listaCategoria.add(iw);
				   found = true;					
			   }
			   i++;
			} // while					
	   } // for
	   return listaCategoria;
	   
   } // getInfoCategoria(String sCodCategoria)


// ------------------------------------------------------
// Programa principal de prueba de la clase
//

public static void main( String[] args )
{
	if ( args.length == 0 )
	{
		System.out.println("Usage: GestorCategorias <codigo categoria>");
		System.exit(0);
	}
	
	GestorCategorias gc = new GestorCategorias( ficheroXML /* nombre del fichero */ );
	
	List <InfoServicioWeb> lista = gc.getInfoCategoria( args[0] );

	for(InfoServicioWeb iw : lista) {				
			System.out.println( "(" + iw.getType() + ") URL: " + iw.getURL() );
	}
} 

} // class
