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
import org.xml.sax.SAXException;

import de.unibw.fusionvis.FusionVis;

/**
 * <p>Baut einen DOM-Baum aus einem XML-Datensatz eines Battle Simulators 
 * und wandelt diesen in eine Collection von einzelnen Dateneinträgen um.</p>
 * @author stzschoppe
 */
public class BattleSimImporter extends Importer {

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
	public Document getDocument() {
		if (document == null) {
			// Einlesen des Datensatzes
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = null;
			try {
				builder = factory.newDocumentBuilder();
				document = builder.parse(xmlDataLocation);

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
		return document;
	}

}
