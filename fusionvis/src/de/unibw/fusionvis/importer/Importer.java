/**
 * 
 */
package de.unibw.fusionvis.importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.unibw.fusionvis.common.Data;
import de.unibw.fusionvis.common.DataSet;
import de.unibw.fusionvis.common.Type;
import de.unibw.fusionvis.common.properties.AbstractProperty;
import de.unibw.fusionvis.common.properties.BooleanProperty;
import de.unibw.fusionvis.common.properties.CharProperty;
import de.unibw.fusionvis.common.properties.FloatProperty;
import de.unibw.fusionvis.common.properties.IntProperty;
import de.unibw.fusionvis.common.properties.StringProperty;

/**
 * <p>Baut einen DOM-Baum aus einem XML-Datensatz und wandelt diesen in 
 * eine Collection von einzelnen Dateneinträgen um.</p>
 * @author stzschoppe
 */
public abstract class Importer {
	private final String standardInputFile = "\\res\\input.xml";
	
	protected Logger logger;
	
	/**Datensatz*/
	protected DataSet dataSet = null;
	
	/**
	 * DOM-Document der importierten XML Datei;
	 */
	protected Document document;
	
	/**
	 * Liste einfacher Eigenschaften
	 */
	protected ArrayList<String> simplePropertyList = new ArrayList<String>();
	
	/**
	 * Liste vektorieller Eigenschaften
	 */
	protected ArrayList<String> vectorPropertyList = new ArrayList<String>();

	protected ArrayList<String> taxonomyList = new ArrayList<String>();

	protected String position = "Location";

	protected String id = "Name";
	
	public Importer(Logger logger){
		this.logger = logger;
	}

	public void runImport(String file) {
		// Einlesen des Datensatzes
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(file);
			buildDataSet();

		} catch (ParserConfigurationException e) {
			logger.log(Level.SEVERE, "Fehler beim Initialisieren des Importers"
					+ "\n" + e.getLocalizedMessage() + "\n");
		} catch (SAXException e) {
			logger.log(Level.SEVERE,
					"Fehler beim Initialisieren des Importers " + "\n"
							+ e.getLocalizedMessage() + "\n");
		} catch (IOException e) {
			logger.log(Level.SEVERE,
					"Fehler beim Initialisieren des Importers " + "\n"
							+ e.getLocalizedMessage() + "\n");
		}

	}

	/**
	 * @param dataSet neuer Datensatz.
	 */
	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}

	/**
	 * DOM Struktur des Datensatzes. Ist verantwortlich die Instanzvariable 
	 * <code>dataSet</code> zu initialisieren.
	 * @return Satz von Daten
	 */
	public DataSet getDataSet() {
		if (dataSet == null) {
			if (document == null) {
				runImport(standardInputFile);
				logger.log(Level.SEVERE, "importiere Standardinput." + standardInputFile + "\n");
			}
		}
		return dataSet;
	}

	/**
	 * Hilfsfuktion zum Extrahieren der Units
	 * @param unitNode DOM Node &lt;Unit>
	 * @return Data-Objekt
	 */
	protected abstract Data extractUnit(Node item);

	/**
	 * Extrahiert eine einfache Eigenschaft.
	 * @param node XML-Node
	 * @param type Einer der folgenden Typen: <code>TString, TBool, TFloat, TInt, TChar</code>
	 * @return Die jeweilige Typ-Implementierung der AbstractProperty oder null, wenn
	 * keiner der o. a. Typen verwendet wurde
	 */
	protected AbstractProperty extractSimpleProperty(Node node, Type type) {
		switch (type) {
		case TString:
			return new StringProperty(node.getNodeName(), node.getTextContent());
		case TBool:
			return new BooleanProperty(node.getNodeName(), Boolean.parseBoolean(node.getTextContent()));
		case TFloat:
			return new FloatProperty(node.getNodeName(), Float.parseFloat(node.getTextContent()));
		case TInt:
			return new IntProperty(node.getNodeName(), Integer.parseInt(node.getTextContent()));
		case TChar:
			return new CharProperty(node.getNodeName(), node.getTextContent().charAt(0));
		default:
			return null;
		}
	}

	/**
	 * Erzeugt einen Satz von Daten
	 */
	protected void buildDataSet() {
		Node root = document.getDocumentElement();
		Node current = root;
		
		//Bezeicher für dataSet auslesen
		dataSet = new DataSet(root.getNodeName());
		
		current = current.getFirstChild().getNextSibling(); // current --> <Units>
		NodeList units = current.getChildNodes();
		
		for (int i=0; i<units.getLength();i++){
			if (units.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue; //wenn kein Element, dann skip
			}
			dataSet.addData(extractUnit(units.item(i)));
		}
	}

}
