/**
 * 
 */
package de.unibw.fusionvis.datamodel;

import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Mögliche Typen für eine Eigenschaft:
 * <ul>
 * <li><code>TInt</code></li>
 * <li><code>TFloat</code></li>
 * <li><code>TChar</code></li>
 * <li><code>TBool</code></li>
 * <li><code>TDate</code></li>
 * <li><code>TString</code></li>
 * <li><code>TContainer</code></li>
 * </ul>
 * 
 * @author stzschoppe
 * 
 */
public enum Type {
	/**
	 * Integertyp
	 */
	TInt, 
	
	/**
	 * Floattyp
	 */
	TFloat, 
	
	/**
	 * Chartyp
	 */
	TChar, 
	
	/**
	 * Booleantyp
	 */
	TBool, 
	
	/**
	 * Datetype
	 */
	TDate, 
	
	/**
	 * Stringtyp
	 */
	TString,
	
	/**
	 * Containertyp
	 */
	TContainer;
	
	/**
	 * Prüft, ob ein in <code>value</code> angegebener String in den in <code>type</code>
	 * angegebenen Typ umgewandelt werden kann.
	 * @param value zu prüfender Wert
	 * @param type Zieltyp
	 * @return <code>true</code> bei korrrektem Wert, <code>false</code> sonst
	 */
	public static boolean valueCorrect(String value, Type type){ //TODO Testen
		try {
			switch (type) {
			case TInt:
				Integer.valueOf(value);
				return true;
			case TChar: {
				if (value.length() == 1) return true;
				else return false;
				}
			case TBool: {
				if (value.equals("true") || value.equals("false")) return true;
				else return false;
				}
			case TFloat:
				Float.valueOf(value);
				return true;
			case TString:
				return true;
			case TDate:
				new GregorianCalendar(new Locale(value));
				return true;
			default:
				return false;
			}
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Gibt den neutralen Wert für den jeweiligen Typen wieder. 
	 * <ul>
	 * <li><code>TInt 0</code></li>
	 * <li><code>TFloat 0.0</code></li>
	 * <li><code>TChar "\0"</code></li>
	 * <li><code>TBool false</code></li>
	 * <li><code>TDate new GregorianCalendar()</code></li>
	 * <li><code>TString ""</code></li>
	 * </ul>
	 * 
	 * @param type Der Typ, für den das neutrale Element ausgegeben werden soll
	 * @return Das Neutrale Element zum angegebenen Typen.
	 */
	public static String getNeutral(Type type) { //TODO Testen
		switch (type) {
		case TInt:
			return "0";
		case TChar: 
			return "\0";
		case TBool: 
			return "false";
		case TFloat:
			return "0.0";
		case TString:
			return "";
		case TDate:
			return new GregorianCalendar().toString();
		default:
			return "";
		}
	}
}
