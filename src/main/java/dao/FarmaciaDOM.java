package dao;

import java.nio.file.Path;
import java.util.List;

import modelo.Farmacia;
import modelo.Medicamento;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class FarmaciaDOM {

	/**
	 * Lee los medicamentos de la farmacia de un fichero xml
	 * mediante DOM
	 *
	 * @param farmaciaXML
	 * @return
	 */

	private static final String DOM_XML_FILE="xml/FarmaciaDOM.xml";


	public boolean leer(Path farmaciaXML) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;

        try {

            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(DOM_XML_FILE));
            document.getDocumentElement().normalize();

            NodeList medicamentos = document.getElementsByTagName("medicamento");

            for (int i = 0; i < medicamentos.getLength(); i++) {

                Node med = medicamentos.item(i);

                if (med.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) med;
                    System.out.println("Nombre: " + getNode("nombre", elemento));
                    System.out.println("Código: " + getNode("codigo", elemento));
                    System.out.println("Precio: " + getNode("precio", elemento));
                    System.out.println("Stock: " + getNode("stock", elemento));
                    System.out.println("Stock máximo: " + getNode("Stock_Maximo", elemento));
                    System.out.println("Stock mínimo: " + getNode("Stock_Minimo", elemento));
                    System.out.println("Código de proveedor: " + getNode("codigo_proveedor", elemento));

                }

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return false;

    }

	/**
	 * Guarda los medicamentos de la farmacia en un fichero XML
	 * mediamente DOM
	 *
	 * @param farmacia
	 * @return
	 */
	public boolean guardar(Farmacia farmacia) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		List<Medicamento> lista = farmacia.leerTodos();

		try {

			builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();

			Document document = implementation.createDocument(null, "Medicamentos", null);
			document.setXmlVersion("1.0");

			//Iterar el arraylist de medicamentos
			for (int i = 0; i < lista.size(); i++) {

				Element raiz = document.createElement("medicamento");
				document.getDocumentElement().appendChild(raiz);

				CrearElemento("nombre", lista.get(i).getNombre().toString(), raiz, document);
				CrearElemento("codigo", Integer.toString(lista.get(i).getCod()), raiz, document);
				CrearElemento("precio", Double.toString(lista.get(i).getPrecio()), raiz, document);
				CrearElemento("stock", Integer.toString(lista.get(i).getStock()), raiz, document);
				CrearElemento("Stock_Maximo", Integer.toString(lista.get(i).getStockMaximo()), raiz, document);
				CrearElemento("tock_Minimo", Integer.toString(lista.get(i).getStockMinimo()), raiz, document);
				CrearElemento("codigo_proveedor", Integer.toString(lista.get(i).getCodProveedor()), raiz, document);

			}

			Source source = new DOMSource(document);

			Result result = new StreamResult(new java.io.File(DOM_XML_FILE));

			Transformer transformer = TransformerFactory.newInstance().newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);

			Result console = new StreamResult(System.out);
			transformer.transform(source, console);

		} catch (ParserConfigurationException | TransformerConfigurationException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		}

		return false;

	}

	public static void CrearElemento(String dato, String valor, Element raiz, Document document) {

		Element elem = document.createElement(dato);
		Text text = document.createTextNode(valor);
		raiz.appendChild(elem);
		elem.appendChild(text);

	}

}
