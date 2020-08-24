/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**stores all the information about one campaign
 * @author sarunasil
 * @version It is currently assumed that that log files do not have repetitions.
 * 			It is also assumed that all the cached information can be stored in an int - max 2^32
 */

public class Campaign {
	private String name;
	private int numberOfClicks;
	private int numberOfUniques;
	private int numberOfBounces;
	private int numberOfConversions;
	private int numberOfImpressions;
	private float totalCost;
	private float cpa, cpc, ctr, cpm;
	private float bounceRate;
	
	private List<Click> clicks = new ArrayList<Click>();
	private List<Session> sessions = new ArrayList<Session>();
	private List<Impression> impressions = new ArrayList<Impression>();
	
	//constructor
	public Campaign(String name) {
		this.name = name;
	}
	
	//constructor for getting data from the database
	public Campaign(String name, int nc, int nu, int nb, int ncon, int ni, float tc, float cpa, float cpc, float ctr, float cpm, float br) {
		this.name = name;
		numberOfClicks = nc;
		numberOfUniques = nu;
		numberOfBounces = nb;
		numberOfConversions = ncon;
		numberOfImpressions = ni;
		totalCost = tc;
		this.cpa = cpa;
		this.cpc = cpc;
		this.ctr = ctr;
		this.cpm = cpm;
		bounceRate = br;
	}

	/**appends all the elements of a given list to the overall clicks list
	*/
	public void appendClicks(List<Click> c) throws Exception {
		if (!clicks.addAll(c)) throw new Exception("Failed to append clicks");
		
		System.out.println(clicks.size());
	}

	/**appends all the elements of a given list to the overall sessions list
	*/
	public void appendSessions(List<Session> s) throws Exception{
		if (!sessions.addAll(s)) throw new Exception("Failed to append sessions");
		System.out.println(sessions.size());
	}

	/**appends all the elements of a given list to the overall impressions list
	*/
	public void appendImpressions(List<Impression> i) throws Exception{
		if (!impressions.addAll(i)) throw new Exception("Failed to append sessions");
		System.out.println(impressions.size());
	}
	
	/**
	 * Recalculates all cached values (e.g. cpa, cpc, etc.)
	 * and sorts all three lists by their records dates
	 */
	public void recalculate() { //sorts click, session and impression list by date
		Collections.sort(clicks);
		Collections.sort(sessions);
		Collections.sort(impressions);

		//functions that calculate cached values
		numberOfClicks = calcNumberOfClicks();
		numberOfUniques = calcNumberOfUniques();
		numberOfBounces = calcNumberOfBounces();
		numberOfConversions = calcNumberOfConversions();
		numberOfImpressions = calcNumberOfImpressions();
		totalCost = calcTotalCost();
		ctr = calcCTR();
		cpa = calcCPA();
		cpc = calcCPC();
		cpm = calcCPM();
		bounceRate = calcBounceRate();
	}
	
	private int calcNumberOfClicks(){
		return clicks.size();
	}
	
	private int calcNumberOfUniques() {
		Set<Long> uniqueUsers = new HashSet<Long>();
		
		for (Unit u : clicks) {
			uniqueUsers.add(u.getUserId());
		}
		
		return uniqueUsers.size();
	}
	
	private int calcNumberOfBounces() {
		return 0;
	}
	
	private int calcNumberOfConversions() {
		int count = 0;
		
		for (Session s : sessions) {
			if (s.GetConversion()) ++count;
		}
		return count;
	}
	
	private int calcNumberOfImpressions() {
		return impressions.size();
	}
	
	private float calcTotalCost() {
		float acc = 0;
		
		for (Click c : clicks) {
			acc+=c.getClickCost();
		}
		
		return acc;
	}
	
	private float calcCTR() {
		return ((float)numberOfClicks) / numberOfImpressions;
	}
	
	private float calcCPA() {
		return totalCost / numberOfConversions;
	}
	
	private float calcCPC() {
		return totalCost / numberOfClicks;
	}
	
	private float calcCPM() {
		return totalCost / (numberOfImpressions / 1000f);
	}
	
	private int calcBounceRate() {
		return 0;
	}

	//getters
	public String getName() {
		return name;
	}

	public int getNumberOfClicks() {
		return numberOfClicks;
	}

	public int getNumberOfUniques() {
		return numberOfUniques;
	}

	public int getNumberOfBounces() {
		return numberOfBounces;
	}

	public int getNumberOfConversions() {
		return numberOfConversions;
	}
	
	public int getNumberOfImpressions() {
		return numberOfImpressions;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public float getCtr() {
		return ctr;
	}

	public float getCpa() {
		return cpa;
	}

	public float getCpc() {
		return cpc;
	}

	public float getCpm() {
		return cpm;
	}

	public float getBounceRate() {
		return bounceRate;
	}

	public List<Impression> getImpressions() {
		return this.impressions;
	}

	public List<Session> getSessions() {
		return this.sessions;
	}

	public List<Click> getClicks() {
		return this.clicks;
	}
}
