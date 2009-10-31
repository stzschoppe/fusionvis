/**
 * 
 */
package de.unibw.fusionvis.importer;

import java.util.ArrayList;

import org.w3c.dom.Document;
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
	private final String standardInputFile = "\\res\\sit8979.xml";
	
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

	/**
	 * DOM Struktur des Datensatzes. Ist verantwortlich die Instanzvariable 
	 * <code>dataSet</code> zu initialisieren.
	 * @return Satz von Daten
	 */
	public DataSet getDataSet() {
		if (dataSet == null) {
			if (document == null) {
				runImport(standardInputFile);
			}
		}
		return dataSet;
	}
	
	public abstract void runImport(String file);

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
	 * @param dataSet neuer Datensatz.
	 */
	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}

}
