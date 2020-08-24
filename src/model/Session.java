/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**stores one server log entry
 * @author sarunasil
 *
 */


public class Session extends Unit {
	private LocalDateTime exitDate = null;
	private int pagesViewed;
	private boolean conversion;

	//constructor with correct types
	public Session(LocalDateTime date, long userId, LocalDateTime exitDate, int pagesViewed, boolean conversion) {
		super(date, userId);
		
		this.exitDate = exitDate;
		this.pagesViewed = pagesViewed;
		this.conversion = conversion;
	}
	
	//constructor with type conversion
	public Session(String[] data) throws Exception {
		super(Arrays.copyOfRange(data, 0, 2));
		
		if (data.length!=5) throw new Exception("Incorrect number of paramaters");
		
		//converts exitDate, pagesViewed and conversion
		if (!data[2].equals("n/a")) 
			exitDate = LocalDateTime.parse( data[2].replace(' ', 'T'), DateTimeFormatter.ISO_DATE_TIME );
		pagesViewed = Integer.parseInt(data[3]);
		conversion = toBoolean(data[4]);	
	}
	
	private boolean toBoolean(String b) {
		return (b.equals("Yes")?true:false);
	}
	
	//getters
	public LocalDateTime GetEntryDate() {
		return super.getDate();
	}
	
	public LocalDateTime GetExitDate() {
		return exitDate;
	}
	
	public int GetPagesCount() {
		return pagesViewed;
	}
	
	public boolean GetConversion() {
		return conversion;
	}

}
