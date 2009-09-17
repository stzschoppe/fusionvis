/**
 * 
 */
package de.unibw.fusionvis.common;

import java.util.Date;

/**
 * M�gliche Typen f�r eine Eigenschaft:
 * <ul>
 * <li><code>TInt</code></li>
 * <li><code>TFloat</code></li>
 * <li><code>TChar</code></li>
 * <li><code>TDate</code></li>
 * <li><code>TString</code></li>
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
	 * Datetype
	 */
	TDate, 
	
	/**
	 * Stringtyp
	 */
	TString;
	
	/**
	 * Pr�ft, ob ein in <code>value</code> angegebener String in den in <code>type</code>
	 * angegebenen Typ umgewandelt werden kann.
	 * @param value zu pr�fender Wert
	 * @param type Zieltyp
	 * @return <code>true</code> bei korrrektem Wert, <code>false</code> sonst
	 */
	public static boolean valueCorrect(String value, Type type){ //TODO Testen
		try {
			switch (type) {
			case TInt:
				Integer.valueOf(value);
				break;
			case TChar: {
				if (value.length() <= 1) return true;
				else return false;
				}
			case TFloat:
				Float.valueOf(value);
				break;
			case TString:
				break;
			case TDate:
				new Date(value);
				break;
			default:
				return false;
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}
	
	public static String getNeutral(Type type) { //TODO Testen
		switch (type) {
		case TInt:
			return "0";
		case TChar: 
			return "";
		case TFloat:
			return "0.0";
		case TString:
			return "";
		case TDate:
			return (new Date()).toGMTString();
		default:
			return "";
		}
	}
}
