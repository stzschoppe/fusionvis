/**
 * 
 */
package de.unibw.battlesimvis.backend;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.unibw.fusionvis.backend.Importer;
import de.unibw.fusionvis.datamodel.Data;
import de.unibw.fusionvis.datamodel.Type;
import de.unibw.fusionvis.datamodel.properties.AbstractProperty;
import de.unibw.fusionvis.datamodel.properties.ContainerProperty;
import de.unibw.fusionvis.frontend.ImporterPanel;

/**
 * <p>
 * Baut einen DOM-Baum aus einem XML-Datensatz eines Battle Simulators und
 * wandelt diesen in eine Collection von einzelnen Dateneinträgen um.
 * </p>
 * 
 * @author stzschoppe
 */
public class BattleSimImporter extends Importer {

	/**
	 * Konstruktor eines Importers für Ausgaben eines Battle Simulators unter
	 * Angabe der zu importierenden XML-Datei
	 */
	public BattleSimImporter() {
		this.panel = new ImporterPanel(this);
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
	protected Data extractDataFromNode(Node unitNode) throws Exception {
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
	 * Extrahiert eine einfache Eigenschaft vom Typ Float, Date oder String
	 * sonst.
	 * 
	 * @param node
	 * @return
	 * @throws Exception 
	 */
	private AbstractProperty extractSimpleProperty(Node node) throws Exception {
		try {
			Float.valueOf(node.getTextContent());
			return extractSimpleProperty(node, Type.TFloat);
		} catch (NumberFormatException e) {
			try {
				parseDate(node.getTextContent());
				return extractSimpleProperty(node, Type.TDate);
			} catch (Exception e2) {
				return extractSimpleProperty(node, Type.TString);
			}
		}
	}

	/**
	 * Extrahiert eine Vektorielle Eigenschaft.
	 * 
	 * @param node
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	private ContainerProperty extractContainerProperty(Node node, String id) throws Exception {
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
