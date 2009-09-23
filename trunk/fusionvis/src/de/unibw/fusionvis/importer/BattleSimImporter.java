/**
 * 
 */
package de.unibw.fusionvis.importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.unibw.fusionvis.FusionVis;
import de.unibw.fusionvis.common.Data;
import de.unibw.fusionvis.common.DataSet;
import de.unibw.fusionvis.common.SimpleProperty;
import de.unibw.fusionvis.common.Type;
import de.unibw.fusionvis.common.VectorProperty;

/**
 * <p>Baut einen DOM-Baum aus einem XML-Datensatz eines Battle Simulators 
 * und wandelt diesen in eine Collection von einzelnen Dateneinträgen um.</p>
 * @author stzschoppe
 */
public class BattleSimImporter extends Importer {
	/**
	 * DOM-Document der importierten XML Datei;
	 */
	private Document document;
	
	/**
	 * Liste einfacher Eigenschaften
	 */
	private ArrayList<String> simplePropertyList = new ArrayList<String>();
	/**
	 * Liste vektorieller Eigenschaften
	 */
	private ArrayList<String> vectorPropertyList = new ArrayList<String>();
	/**
	 * 
	 */
	private ArrayList<String> taxonomyList = new ArrayList<String>();
	/**
	 * 
	 */
	private String position = "Location";
	/**
	 * 
	 */
	private String id = "Name";
	
	
	
	/**
	 * Konstruktor eines Importers für Ausgaben eines Battle Simulators 
	 * unter Angabe der zu importierenden XML-Datei
	 * @param xmlDataLocation Pfad zur XML-Datei
	 */
	public BattleSimImporter(String xmlDataLocation) {
		super(xmlDataLocation);
		
		// Listen der zu übernehmenden Elemente initialisieren.
		simplePropertyList.add("FormalAbbrdName");
//		simplePropertyList.add("SisoEbv");
		simplePropertyList.add("IsPlatform");
		simplePropertyList.add("HostilityCode");
//		simplePropertyList.add("AffiliationCode");
//		simplePropertyList.add("SizeCode");
//		simplePropertyList.add("OperStateCode");
//		simplePropertyList.add("TypeId");
//		simplePropertyList.add("SymbolId");
		simplePropertyList.add("Certainty");
		simplePropertyList.add("Reliability");
		simplePropertyList.add("LastModified");
		
		vectorPropertyList.add("Velocity");
		vectorPropertyList.add("Orientation");
	}

	/* (non-Javadoc)
	 * @see de.unibw.fusionvis.importer.Importer#getDocument()
	 */
	@Override
	public DataSet getDataSet() {
		if (dataSet == null) {
			if (document == null) {
				// Einlesen des Datensatzes
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = null;
				try {
					builder = factory.newDocumentBuilder();
					document = builder.parse(xmlDataLocation);
					buildDataSet();

				} catch (ParserConfigurationException e) {
					FusionVis.getLogger().log(
							Level.SEVERE,
							"Fehler beim Initialisieren des Importers" + "\n"
									+ e.getLocalizedMessage() + "\n");
				} catch (SAXException e) {
					FusionVis.getLogger().log(
							Level.SEVERE,
							"Fehler beim Initialisieren des Importers " + "\n"
									+ e.getLocalizedMessage() + "\n");
				} catch (IOException e) {
					FusionVis.getLogger().log(
							Level.SEVERE,
							"Fehler beim Initialisieren des Importers " + "\n"
									+ e.getLocalizedMessage() + "\n");
				}
			}
		}
		return dataSet;
	}
	
	/**
	 * Erzeugt einen Satz von Daten
	 */
	private void buildDataSet() {
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
	
	/**
	 * Hilfsfuktion zum Extrahieren der Units
	 * @param unitNode DOM Node &lt;Unit>
	 * @return Data-Objekt
	 */
	private Data extractUnit(Node unitNode) {
		Data result = null;
		NodeList list = unitNode.getChildNodes();
		
		// Erster Durchlauf zum Finden des Bezeichners
		for (int i=0; i<list.getLength();i++){
			if (list.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue; //wenn kein Element, dann skip
			}
				Node element = list.item(i);
				if (element.getNodeName().equals(id)) {
					result = new Data(element.getTextContent());
				}
		}
		
		//Zweiter Durchlauf für das setzen der restlichen Eigenschaften
		for (int i=0; i<list.getLength();i++){
			if (list.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue; //wenn kein Element, dann skip
			}
				Node element = list.item(i);
				//Position setzen
				if (element.getNodeName().equals(position)) {
					result.setPosition(extractVectorProperty(element, "Position"));
				}
				else if (simplePropertyList.contains(element.getNodeName())) {
					if (element.getNodeName().equals("IsPlatform")) {
						result.addSimpleProperty(extractSimpleProperty(element,
								Type.TBool));
					}
					else result.addSimpleProperty(extractSimpleProperty(element,
							Type.TString));
				} 
				else if (vectorPropertyList.contains(element.getNodeName())) {
					result.addVectorProperty(extractVectorProperty(element, element.getNodeName()));
				}
				else if (taxonomyList.contains(element.getNodeName())) {
					result.addTaxonomie((extractSimpleProperty(element)));
				}
				else {
					//skip
				}
		}
		
		return result;
	}
	
	/**
	 * Extrahiert eine einfache Eigenschaft.
	 * @param node
	 * @param type
	 * @return
	 */
	private SimpleProperty extractSimpleProperty(Node node, Type type) {
		return new SimpleProperty(node.getNodeName(), 
				type, node.getTextContent()); //TODO Anpassen auf automatische Typerkennung.
	}
	
	
	/**
	 * Extrahiert eine einfache Eigenschaft vom Typ Float, fallsmöglich, String sonst.
	 * @param node
	 * @return
	 */
	private SimpleProperty extractSimpleProperty(Node node) {
		try {
			Float.valueOf(node.getTextContent());
			return new SimpleProperty(node.getNodeName(), 
					Type.TFloat, node.getTextContent());
		} catch (NumberFormatException e) {
			return new SimpleProperty(node.getNodeName(), 
					Type.TString, node.getTextContent());
		}
	}
	
	/**
	 * Extrahiert eine Vektorielle Eigenschaft.
	 * @param node
	 * @param id
	 * @return
	 */
	private VectorProperty extractVectorProperty(Node node, String id) {
		VectorProperty result = new VectorProperty(id);
		NodeList list = node.getChildNodes();
		
		for (int i=0; i<list.getLength();i++){
			if (list.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue; //wenn kein Element, dann skip
			}
				result.addComponent(extractSimpleProperty(list.item(i)));
		}
		
		return result;
	}

}
