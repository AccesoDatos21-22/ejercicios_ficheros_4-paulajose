package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import dao.FarmaciaDOM;
import dao.MedicamentoAleatorio;
import modelo.*;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import dao.TrabajadorDAOImp;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class Main {

    private static final String JAXB_XML_FILE = "xml/EmpresaJAXB.xml";
    private static final String XSTREAM_XML_FILE = "xml/EmpresaXTREAM.xml";
    private static final String DOM_XML_FILE = "xml/EmpleadosDOM.xml";

    static Scanner sc = new Scanner(System.in);

 /* private static void ejemploLeerDOM() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(DOM_XML_FILE));
			document.getDocumentElement().normalize();
			// Obtenemos la lista de nodos con nombre empleado de todo el documento
			NodeList empleados = document.getElementsByTagName("empleado");
			for (int i = 0; i < empleados.getLength(); i++) {
				Node emple = empleados.item(i); // obtener un nodo
				if (emple.getNodeType() == Node.ELEMENT_NODE) {
					Element elemento = (Element) emple; // tipo de nodo
					System.out.println("ID: " + getNodo("id", elemento));
					System.out.println("Apellido: " + getNodo("nombre", elemento));
					System.out.println("Departamento: " + getNodo("dep", elemento));
					System.out.println("Salario: " + getNodo("salario", elemento));
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// obtener informaci??n de un nodo
	private static String getNodo(String etiqueta, Element elem) {
		NodeList nodo = elem.getElementsByTagName(etiqueta).item(0).getChildNodes();
		Node valornodo = (Node) nodo.item(0);
		return valornodo.getNodeValue(); // devuelve el valor del nodo
	}
	private static void ejemploEscribirDOM() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "Empleados", null);
			document.setXmlVersion("1.0"); // asignamos la version de nuestro XML
			for (int i = 1; i < 10; i++) {
				Element raiz = document.createElement("empleado");
				document.getDocumentElement().appendChild(raiz);
				CrearElemento("id", Integer.toString(i), raiz, document);
				CrearElemento("nombre", "Empleado " + i, raiz, document);
				CrearElemento("dep", "01", raiz, document);
				CrearElemento("salario", "1000.0", raiz, document);
			}
			// Creamos la fuente XML a partir del documento
			Source source = new DOMSource(document);
			// Creamos el resultado en el fichero Empleados.xml
			Result result = new StreamResult(new java.io.File(DOM_XML_FILE));
			// Obtenemos un TransformerFactory
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			// Le damos formato y realizamos la transformaci??n del documento a fichero
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);
			// Mostramos el documento por pantalla especificando el canal de salida el
			// System.out
			Result console = new StreamResult(System.out);
			transformer.transform(source, console);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static void CrearElemento(String datoEmple, String valor, Element raiz, Document document) {
		Element elem = document.createElement(datoEmple);
		Text text = document.createTextNode(valor);
		raiz.appendChild(elem);
		elem.appendChild(text);
	}
private static void ejemploEscribirXSTREAM() {
		try {
			System.out.println("Comienza el proceso de creaci??n del fichero a XML...");
			XStream xstream = new XStream(new DomDriver());
			
			long time = System.currentTimeMillis();
			System.out.println("Inicio: " + new Date(time));
			Empresa cc = new Empresa();
			cc.setIdEmpresa(1);
			cc.setDireccion("En la nube");
			cc.setNombreEmpresa("IES");
			cc.setNumEmpleados(10);
			ArrayList<Empleado> alCU = new ArrayList<Empleado>();
			int init = 20000;
			for (int i = 1; i < 10; i++) {
				Empleado cu = new Empleado();
				cu.setId(i);
				cu.setActivo(true);
				cu.setNumeroEmpl(init++);
				cu.setNombre("Empleado " + i);
				cu.setTitulo("SW Architect");
				cu.setFechaAlta(new Date(System.currentTimeMillis()));
				alCU.add(cu);
			}
			cc.setEmpleados(alCU);
			
			// cambiar de nombre a las etiquetas XML
			xstream.alias("Empleado", Empleado.class);
			xstream.alias("Empresa", Empresa.class);
			// quitar etiqueta lista (Atributo de la clase ListaEmpleados)
			xstream.addImplicitCollection(Empresa.class, "empleados");
			// Insertar los objetos en XML
			xstream.toXML(cc, new FileOutputStream(XSTREAM_XML_FILE));
			System.out.println("Creado fichero XML....");
		} catch (IOException e) {
			System.err.println("Error: " + e);
		}
	}
	private static void ejemploLeerXSTREAM() {
		Empresa empresa = new Empresa();
        try {
            Class<?>[] classes = new Class[] { Empresa.class, Empleado.class };
            XStream xstream = new XStream(new StaxDriver());
           // XStream.setupDefaultSecurity(xstream);
            xstream.allowTypes(classes);
           
            xstream.alias("Empresa", Empresa.class);
            xstream.alias("Empleado", Empleado.class);
            xstream.addImplicitCollection(Empresa.class, "empleados");
            empresa = (Empresa) xstream
                    .fromXML(new FileInputStream(XSTREAM_XML_FILE));
            for(Empleado e: empresa.getEmpleados()) {
            	System.out.println(e);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e);
        }
	}
  private static void ejemploJaxb() {
		long time = System.currentTimeMillis();
		System.out.println("Inicio: " + new Date(time));
		Empresa cc = new Empresa();
		cc.setIdEmpresa(1);
		cc.setDireccion("En la nube");
		cc.setNombreEmpresa("IES");
		cc.setNumEmpleados(10);
		ArrayList<Empleado> alCU = new ArrayList<Empleado>();
		int init = 20000;
		for (int i = 1; i < 10; i++) {
			Empleado cu = new Empleado();
			cu.setId(i);
			cu.setActivo(true);
			cu.setNumeroEmpl(init++);
			cu.setNombre("Empleado " + i);
			cu.setTitulo("SW Architect");
			cu.setFechaAlta(new Date(System.currentTimeMillis()));
			alCU.add(cu);
		}
		cc.setEmpleados(alCU);
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Empresa.class);
			// Si las clases a serializar est??n en otro paquete se indica el paquete
			// al crear el marshall
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// Provincia provincia = fillProvincia();
			// Mostramos el documento XML generado por la salida estandar
			marshaller.marshal(cc, System.out);
			// guardamos el objeto serializado en un documento XML
			marshaller.marshal(cc, Files.newOutputStream(Paths.get(JAXB_XML_FILE)));
			Unmarshaller unmarshaller = context.createUnmarshaller();
			// Deserealizamos a partir de un documento XML
			Empresa empresa = (Empresa) unmarshaller.unmarshal(Files.newInputStream(Paths.get(JAXB_XML_FILE)));
			System.out.println("********* Empresa cargado desde fichero XML***************");
			// Mostramos por linea de comandos el objeto Java obtenido
			// producto de la deserialziacion
			marshaller.marshal(empresa, System.out);
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
	}*/

   /* public static void testTrabajadorDAOImp() {
        TrabajadorDAOImp ficheroAleatorio = new TrabajadorDAOImp();
        Trabajador emp = new Trabajador(3, 1, 1000.0, "Pepito ");
        Trabajador emp2 = new Trabajador();
        ficheroAleatorio.insertar(emp);
        try {
            emp2 = ficheroAleatorio.leer(3);
        } catch (NoExisteTrabajador e) {
            System.err.println(e.getMessage());
        }
        System.out.println(emp2);
    }*/

    public static void main(String[] args) {

//***************************************Ejercicios ficheros aleatorios

        List<Medicamento> listBuscados = new ArrayList<>();
        List<Medicamento> listMedicamentos = new ArrayList<>();
        MedicamentoAleatorio MedAleatorio = new MedicamentoAleatorio();

        System.out.println("??Cu??nntos medicamentos quiere a??adir?");
        int cant = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < cant; i++) {
            System.out.println("\nVas a introducir los datos del medicamento " + (i + 1));

            Medicamento medicamento = anadirMedicamento();
            listMedicamentos.add(medicamento);
            MedAleatorio.guardar(medicamento);
        }

        System.out.println("\nIntroduzca el nombre del medicamento que quiere buscar: ");
        String nombre = sc.nextLine();

        listBuscados = MedAleatorio.buscar(nombre);

        System.out.println("\n----->> Medicamentos soliticados <<-----");

        for (Medicamento med : listBuscados) {

            System.out.println(med);

        }

        System.out.print("\nIntroduce el c??digo del medicamento que quiere borrar: ");
        int codigo = sc.nextInt();

        if (listMedicamentos.size() <= codigo - 1)
            for (Medicamento m : listMedicamentos)
                if (m.getCod() == codigo)
                    MedAleatorio.borrar(m);
                else
                    System.err.println("El codigo del medicamento introducido no es correcto");

        //********************************Ejercicios 4XML DOM

        FarmaciaDOM farmaciaDOM = new FarmaciaDOM();

        List<Medicamento> listFarmacia = new ArrayList<>();
        Farmacia farmacia1 = new Farmacia(listFarmacia);

        System.out.println("??Cu??ntos medicamentos quiere a??adir a la farmacia?");
        int cant = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < cant; i++) {
            System.out.println("\nVa a introducir los datos para el medicamento " + (i + 1));
            Medicamento medicamentoFar = anadirMedicamento();
            farmacia1.guardar(medicamentoFar);
        }

        farmaciaDOM.guardar(farmacia1);
	    
	System.out.println("\n-----> Medicamentos de la farmacia <-----");

        Path path = Paths.get("xml/FarmaciaDOM.xml");
        farmaciaDOM.leer(path);


        // ejemploJaxb();
        // ejemploEscribirDOM();
        // ejemploLeerDOM();
        // ejemploEscribirXSTREAM();
        // ejemploLeerXSTREAM();
    }

    public static Medicamento anadirMedicamento() {

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Precio: ");
        Double precio = sc.nextDouble();

        System.out.print("Stock m??ximo: ");
        int stockMax = sc.nextInt();

        System.out.print("Stock m??nimo: ");
        int stockMin = sc.nextInt();

        System.out.print("Stock: ");
        int stock = sc.nextInt();

        System.out.print("C??digo de proveedor: ");
        int codProveedor = sc.nextInt();

        sc.nextLine();

        Medicamento med = new Medicamento(nombre, precio, stock, stockMax, stockMin, codProveedor);

        return med;

    }


    // testTrabajadorDAOImp();
    // ejemploJaxb();
    //ejemploEscribirDOM();
    //ejemploLeerDOM();

    // ejemploEscribirXSTREAM();
    //ejemploLeerXSTREAM();
}
