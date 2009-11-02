import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ParseTest {

	
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
//	2009-02-20T10:25:36.8125+01:00
//	yyyy-MM-ddTHH:mm:ss.SSSSzzz
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParseTest pt = new ParseTest();
		try {
			pt.parseDate("2009-02-20T10:25:36+01:00");
			System.out.println("Pass");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}

}
