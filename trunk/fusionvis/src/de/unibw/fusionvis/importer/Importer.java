/**
 * 
 */
package de.unibw.fusionvis.importer;

import org.w3c.dom.Node;

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
	/**Ort der XML-Datei, aus der die Informationen zu lesen sind */
	protected final String xmlDataLocation;
	
	/**Datensatz*/
	protected DataSet dataSet = null;
	
	/**
	 * Konstruktor eines Importers unter Angabe der zu importierenden XML-Datei
	 * @param xmlDataLocation Pfad zur XML-Datei
	 */
	public Importer(String xmlDataLocation){
		this.xmlDataLocation = xmlDataLocation; 
	}
	
	/**
	 * <p>Ort der XML-Datei, aus der die Informationen zu lesen sind</p>
	 * @return xmlDataLocation
	 */
	public String getXmlDataLocation() {
		return xmlDataLocation;
	}

	/**
	 * DOM Struktur des Datensatzes. Ist verantwortlich die Instanzvariable 
	 * <code>dataSet</code> zu initialisieren.
	 * @return Satz von Daten
	 */
	public abstract DataSet getDataSet();
	
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

}
