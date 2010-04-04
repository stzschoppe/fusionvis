/**
 * 
 */
package de.unibw.fusionvis.backend;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.unibw.fusionvis.FusionVisForm;
import de.unibw.fusionvis.datamodel.Data;
import de.unibw.fusionvis.datamodel.DataSet;
import de.unibw.fusionvis.datamodel.Type;
import de.unibw.fusionvis.datamodel.properties.AbstractProperty;
import de.unibw.fusionvis.datamodel.properties.BooleanProperty;
import de.unibw.fusionvis.datamodel.properties.CharProperty;
import de.unibw.fusionvis.datamodel.properties.DateProperty;
import de.unibw.fusionvis.datamodel.properties.FloatProperty;
import de.unibw.fusionvis.datamodel.properties.IntProperty;
import de.unibw.fusionvis.datamodel.properties.StringProperty;

/**
 * <p>
 * Baut einen DOM-Baum aus einem XML-Datensatz und wandelt diesen in eine
 * Collection von einzelnen Dateneintr�gen um.
 * </p>
 * 
 * @author stzschoppe
 */
public abstract class Importer extends Observable {
	//private Mapper mapper;
	
	/** Datensatz */
	private DataSet dataSet = null;

	/**
	 * DOM-Document der importierten XML Datei;
	 */
	private Document document;

	private final String standardInputFile = "\\res\\input.xml";

	/**
	 * ImporterPanel
	 */
	//protected ImporterPanel panel;

	/**
	 * Logger
	 */
	protected Logger logger;
	
	/**
	 * Liste einfacher Eigenschaften, die aus der XML zu extrahieren ist.
	 */
	protected ArrayList<String> simplePropertyList = new ArrayList<String>();

	/**
	 * Liste der Taxonomien, die aus der XML zu extrahieren ist.
	 */
	protected ArrayList<String> taxonomyList = new ArrayList<String>();

	/**
	 * Liste zusammengestzter Eigenschaften, die aus der XML zu extrahieren ist.
	 */
	protected ArrayList<String> vectorPropertyList = new ArrayList<String>();

	/**
	 * Eigenschaft, die als Bezeichner aus der XML extrahiert werden soll.
	 */
	protected String id = "Name";

	/**
	 * Eigenschaft, die als Position aus der XML extrahiert werden soll.
	 */
	protected String position = "Location";

	/**
	 * Konstruktor
	 */
	public Importer() {
		this.logger = FusionVisForm.getLogger();
	}

	/**
	 * Hilfsfuktion zum Extrahieren der Units
	 * 
	 * @param node
	 *            DOM Node
	 * @return Data-Objekt
	 * @throws Exception
	 */
	protected abstract Data extractDataFromNode(Node node) throws Exception;

	/**
	 * Ausf�hren des Imports der angegeben Datein in das Datenmodell.
	 * @param file Datei im XML-Format
	 */
	public void runImport(File file) {
		// Einlesen des Datensatzes
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			logger.log(Level.INFO, "Starte Import von " + file + "\n");
			builder = factory.newDocumentBuilder();
			document = builder.parse(file);
			buildDataSet();
			
			// ImporterPanel aktualisieren
			setChanged();
			notifyObservers(dataSet);
			
		} catch (ParserConfigurationException e) {
			logger.log(Level.SEVERE, "Fehler beim Initialisieren des Importers (ParserConfigurationException)"
					+ "\n" + e.getLocalizedMessage() + "\n");
		} catch (SAXException e) {
			logger.log(Level.SEVERE,
					"Fehler beim Initialisieren des Importers (SAXException)" + "\n"
							+ e.getLocalizedMessage() + "\n");
		} catch (IOException e) {
			logger.log(Level.SEVERE,
					"Fehler beim Initialisieren des Importers (IOException)" + "\n"
							+ e.getLocalizedMessage() + "\n");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Fehler beim Importieren " + "\n"
					+ e.getLocalizedMessage() + "\n");
		}

	}

	/**
	 * @param dataSet
	 *            neuer Datensatz.
	 */
	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
		setChanged();
		notifyObservers(dataSet);
	}

	/**
	 * DOM Struktur des Datensatzes. Ist verantwortlich die Instanzvariable
	 * <code>dataSet</code> zu initialisieren.
	 * 
	 * @return Satz von Daten
	 */
	public DataSet getDataSet() {
		if (dataSet == null) {
			if (document == null) {
				runImport(new File(standardInputFile));
				logger.log(Level.SEVERE, "importiere Standardinput."
						+ standardInputFile + "\n");
			}
		}
		return dataSet;
	}

	/**
	 * Extrahiert eine einfache Eigenschaft.
	 * 
	 * @param node
	 *            XML-Node
	 * @param type
	 *            Einer der folgenden Typen:
	 *            <code>TString, TBool, TFloat, TInt, TChar</code>
	 * @return Die jeweilige Typ-Implementierung der AbstractProperty oder null,
	 *         wenn keiner der o. a. Typen verwendet wurde
	 * @throws Exception
	 */
	protected AbstractProperty extractSimpleProperty(Node node, Type type)
			throws Exception {
		switch (type) {
		case TString:
			return new StringProperty(node.getNodeName(), node.getTextContent());
		case TBool:
			return new BooleanProperty(node.getNodeName(), Boolean
					.parseBoolean(node.getTextContent()));
		case TFloat:
			return new FloatProperty(node.getNodeName(), Float.parseFloat(node
					.getTextContent()));
		case TInt:
			return new IntProperty(node.getNodeName(), Integer.parseInt(node
					.getTextContent()));
		case TChar:
			return new CharProperty(node.getNodeName(), node.getTextContent()
					.charAt(0));
		case TDate:
			Date date;
			date = parseDate(node.getTextContent());
			return new DateProperty(node.getNodeName(), date);
		default:
			return null;
		}
	}

	/**
	 * Parst ein Date aus einem String.
	 * Format ist ist yyyy-MM-dd'T'HH:mm:ss mit 0-7 Sekundennachkommastellen.
	 * @param stringToParse String, aus dem ein Date ausgelesen werden soll
	 * @return ausgelesenes Date
	 * @throws ParseException
	 */
	protected Date parseDate(String stringToParse) throws ParseException {
		// yyyy-MM-dd'T'HH:mm:ss.SSSSZ
		Date date = null;
		String formatString = "";
		String parameter = stringToParse;

		// Versuch ein Date ohne Nachkommasekunden zu Parsen
		try {
			if (parameter.length() > 18) {
				parameter = parameter.substring(0, 18) + "GMT"
						+ parameter.substring(19);
			}
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ssZ");
			date = (Date) formatter.parse(parameter);
			return date;
		} catch (Exception e) {
		}

		// Versuch ein Date mit 1-7 Nachkommasekundenstellen zu Parsen
		for (int i = 20; i < 27; i++) {
			formatString = "yyyy-MM-dd'T'HH:mm:ss.";
			int j = i - 19;
			while (j > 0) {
				formatString += "S";
				j--;
			}
			formatString += "Z";

			if (parameter.length() > i) {
				parameter = parameter.substring(0, i) + "GMT"
						+ parameter.substring(i + 1);
			}
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(formatString);
				date = (Date) formatter.parse(parameter);
				return date;
			} catch (Exception e) {
			}
			parameter = stringToParse;
		}
		throw new ParseException("Kein Date\t" + formatString + "\t"
				+ stringToParse, 0);
	}

	/**
	 * Erzeugt einen Satz von Daten
	 * 
	 * @throws Exception
	 */
	private void buildDataSet() throws Exception {
		Node root = document.getDocumentElement();
		Node current = root;
	
		// Bezeicher f�r dataSet auslesen
		dataSet = new DataSet(root.getNodeName());
	
		current = current.getFirstChild().getNextSibling(); // current -->
															// <Units>
		NodeList units = current.getChildNodes();
	
		for (int i = 0; i < units.getLength(); i++) {
			if (units.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue; // wenn kein Element, dann skip
			}
			dataSet.addData(extractDataFromNode(units.item(i)));
		}
	}	

}
