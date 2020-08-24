/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import model.Campaign;
import model.Click;
import model.Impression;
import model.Session;
import model.Unit;

/**reads 3 log files and stores data in campaign object
 * @author sarunasil
 *
 */

public class Parser {
	protected static enum Type {SERVER, CLICK, IMPRESSION};
	
	/**Creates a new campaign and populates it with the data from the files supplied. In the end it will sort all the entries of all the log files by date and calculates any cached data required.
	 * @param name - name for the campaign being created
	 * @param serverLog - server log File object
	 * @param clickLog - click log File object
	 * @param impressionLog - impression log File object
	 * @return Fully configured and ready Campaign object reference 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Campaign CreateCampaign(String name, File serverLog, File clickLog, File impressionLog) throws FileNotFoundException, IOException {
		Campaign campaign = new Campaign(name);
		
		//parse each of the logs
		parseLog(Type.SERVER, campaign, serverLog);
		parseLog(Type.CLICK, campaign, clickLog);
		parseLog(Type.IMPRESSION, campaign, impressionLog);
		
		//sort and set cached values
		campaign.recalculate();
	
		return campaign;
	}
	
	/**
	 * Reads any log file, according to <i>type</i> parameter it creates Session, Click or Impression type object. These objects are stored in a List<Unit> list <i>(accumulator)</i>. After reading all the files it calls the right Campaign's method to append the right log list with the <i>accumulator</i>
	 * @param type - specifies log type (Server, Click or Impression)
	 * @param campaign - Campaign object which Click list is going to be appended with new entries
	 * @param file - File object of the specified click log
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static void parseLog(Type type, Campaign campaign, File file) throws FileNotFoundException, IOException {		
		List<Unit> accumulator = new ArrayList<Unit>();
		Unit unit = null;
		
		//univocity parser methods
		CsvParserSettings settings = new CsvParserSettings();
	    settings.getFormat().setLineSeparator("\n");
	    settings.setHeaderExtractionEnabled(true);

	    // creates a CSV parser
	    CsvParser parser = new CsvParser(settings);
		
		//opens up the file
		try (InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8")){
		    // call beginParsing to read records one by one, iterator-style.
		    parser.beginParsing(isr);

		    String[] row;
		    while ((row = parser.parseNext()) != null) {
		    	//puts values into Unit object
				try {
					unit = createUnit(type, row);
				} catch (Exception e) {
					// TODO DEAL WITH PARSING ERRORS
					e.printStackTrace();
				}
				
				accumulator.add(unit);
		    }

		    // Not necessary but doesn't hurt to call it anyway.
		    parser.stopParsing();
			
		}
		
		try {
			appendUnits(type, campaign, accumulator);
		} catch (Exception e) {
			// TODO DEAL WITH FAILED APPEND ACTION
			e.printStackTrace();
		}
	}
	
	protected static Unit createUnit(Type t, String[] row) throws Exception {
		switch (t) {
		case SERVER:
			return new Session(row);
		case CLICK:
			return new Click(row);
		case IMPRESSION:
			return new Impression(row);
		default:
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected static void appendUnits(Type t, Campaign campaign, List<Unit> acc) throws Exception {
		switch (t) {
		case SERVER:
			campaign.appendSessions((List<Session>)(List<?>)acc);
			break;
		case CLICK:
			campaign.appendClicks((List<Click>)(List<?>)acc);
			break;
		case IMPRESSION:
			campaign.appendImpressions((List<Impression>)(List<?>)acc);
			break;
		}
	}
	
}
