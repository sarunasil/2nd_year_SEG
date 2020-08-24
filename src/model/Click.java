/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package model;

import java.time.LocalDateTime;
import java.util.Arrays;

/**stores one click log entry
 * @author sarunasil
 *
 */


public class Click extends Unit{
	private float clickCost;

	//constructor with correct types
	public Click(LocalDateTime date, long userId, float clickCost) {
		super(date, userId);
		
		this.clickCost = clickCost;
	}
	
	//constructor with type conversion
	public Click(String[] data) throws Exception {
		super(Arrays.copyOfRange(data, 0, 2));
		
		if (data.length!=3) throw new Exception("Incorrect number of paramaters");
		
		//convert click cost
		clickCost = Float.parseFloat(data[2]);
	}
	
	//getters
	public float getClickCost() {
		return clickCost;
	}

}
