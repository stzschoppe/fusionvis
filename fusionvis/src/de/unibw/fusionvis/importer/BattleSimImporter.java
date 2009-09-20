/**
 * 
 */
package de.unibw.fusionvis.importer;

import java.io.IOException;
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
	 * Konstruktor eines Importers für Ausgaben eines Battle Simulators 
	 * unter Angabe der zu importierenden XML-Datei
	 * @param xmlDataLocation Pfad zur XML-Datei
	 */
	public BattleSimImporter(String xmlDataLocation) {
		super(xmlDataLocation);
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
		
		FusionVis.getLogger().log(
				Level.INFO, ""+current);
	}
	
	/**
	 * Hilfsfuktion zum Extrahieren der Units
	 * @param unitNode DOM Node &lt;Unit>
	 * @return Data-Objekt
	 */
	private Data extractUnit(Node unitNode) {
		Data result = new Data(unitNode.getNodeName());
		
		return result;
	}

}
