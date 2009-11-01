/**
 * 
 */
package de.unibw.fusionvis.implementation.battlesimvis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import de.unibw.fusionvis.common.Type;
import de.unibw.fusionvis.common.properties.AbstractProperty;
import de.unibw.fusionvis.common.properties.ContainerProperty;
import de.unibw.fusionvis.importer.Importer;

/**
 * <p>
 * Baut einen DOM-Baum aus einem XML-Datensatz eines Battle Simulators und
 * wandelt diesen in eine Collection von einzelnen Dateneinträgen um.
 * </p>
 * 
 * @author stzschoppe
 */
public class BattleSimImporter extends Importer {
	private Logger logger;

	/**
	 * Konstruktor eines Importers für Ausgaben eines Battle Simulators unter
	 * Angabe der zu importierenden XML-Datei
	 * 
	 * @param logger Übergeordneter Logger
	 *            Pfad zur XML-Datei
	 */
	public BattleSimImporter(Logger logger) {
		super(logger);
		id = "Name";
		position = "Location";

		// Listen der zu übernehmenden Elemente initialisieren.
		simplePropertyList.add("FormalAbbrdName");
		// simplePropertyList.add("SisoEbv");
		simplePropertyList.add("IsPlatform");
		simplePropertyList.add("HostilityCode");
		// simplePropertyList.add("AffiliationCode");
		// simplePropertyList.add("SizeCode");
		// simplePropertyList.add("OperStateCode");
		// simplePropertyList.add("TypeId");
		// simplePropertyList.add("SymbolId");
		simplePropertyList.add("Certainty");
		simplePropertyList.add("Reliability");
		simplePropertyList.add("LastModified");

		vectorPropertyList.add("Velocity");
		vectorPropertyList.add("Orientation");
	}

	@Override
	protected Data extractUnit(Node unitNode) {
		Data result = null;
		NodeList list = unitNode.getChildNodes();

		// Erster Durchlauf zum Finden des Bezeichners
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue; // wenn kein Element, dann skip
			}
			Node element = list.item(i);
			if (element.getNodeName().equals(id)) {
				result = new Data(element.getTextContent());
			}
		}

		// Zweiter Durchlauf für das setzen der restlichen Eigenschaften
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue; // wenn kein Element, dann skip
			}
			Node element = list.item(i);
			// Position setzen
			if (element.getNodeName().equals(position)) {
				result
						.setPosition(extractContainerProperty(element,
								"Position"));
			} else if (simplePropertyList.contains(element.getNodeName())) {
				if (element.getNodeName().equals("IsPlatform")) {
					result.addAbstractProperty(extractSimpleProperty(element,
							Type.TBool));
				} else
					result.addAbstractProperty(extractSimpleProperty(element,
							Type.TString));
			} else if (vectorPropertyList.contains(element.getNodeName())) {
				result.addContainerProperty(extractContainerProperty(element,
						element.getNodeName()));
			} else if (taxonomyList.contains(element.getNodeName())) {
				result.addTaxonomie((extractSimpleProperty(element)));
			} else {
				// skip
			}
		}

		return result;
	}

	/**
	 * Extrahiert eine einfache Eigenschaft vom Typ Float, fallsmöglich, String
	 * sonst.
	 * 
	 * @param node
	 * @return
	 */
	private AbstractProperty extractSimpleProperty(Node node) {
		try {
			Float.valueOf(node.getTextContent());
			return extractSimpleProperty(node, Type.TFloat);
		} catch (NumberFormatException e) {
			return extractSimpleProperty(node, Type.TString);
		}
	}

	/**
	 * Extrahiert eine Vektorielle Eigenschaft.
	 * 
	 * @param node
	 * @param id
	 * @return
	 */
	private ContainerProperty extractContainerProperty(Node node, String id) {
		ContainerProperty result = new ContainerProperty(id);
		NodeList list = node.getChildNodes();

		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue; // wenn kein Element, dann skip
			}
			result.addComponent(extractSimpleProperty(list.item(i)));
		}

		return result;
	}

}
