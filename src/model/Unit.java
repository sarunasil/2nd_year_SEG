/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**stores common information of Session, Click and Impression
 * @author sarunasil
 *
 */

public abstract class Unit implements Comparable<Unit>{	
	private LocalDateTime date;
	private long userId;
	
	
	//constructor with correct types
	public Unit(LocalDateTime date, long userId) {
		this.date = date;
		this.userId = userId;
	}
	
	//constructor with type conversion
	public Unit(String[] data) throws Exception {
		
		
		if (data.length!=2) throw new Exception("Incorrect number of paramaters");
		
		//converts date and userId;
		date = LocalDateTime.parse( data[0].replace(' ', 'T') , DateTimeFormatter.ISO_DATE_TIME );
		userId = Long.parseLong(data[1]);
	}
	
	//this way Unit types will be sorted by date
	@Override
	public int compareTo(Unit u) {
		return getDate().compareTo(u.getDate());
	}
	
	//getters
	public LocalDateTime getDate() {
		return date;
	}
	public long getUserId() {
		return userId;
	}
	
}
