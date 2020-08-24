/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import model.Campaign;
import utils.Loader;
import utils.Parser;

/**Represents an administrator user who is able to upload log files and use all of the Client functionality as well.
 * @author sarunasil
 * @version ASSUMPTION - there can be multiple admin accounts (functionally they would do exactly the same thing)
 */

public class Admin extends Client {
	
	//constructor with campaigns already existing
	public Admin(String username, String password) {
		super(username, password);

		campaigns = new HashMap<String, Campaign>();
		for (String str : Loader.getAllCampaigns()) {
			campaigns.put(str, null);
		}
	}
	
	public void provideLogFiles(String owner, String campaignName, File sLog, File cLog, File iLog) throws FileNotFoundException, IOException {
		//create new campaign
		Campaign c = Parser.CreateCampaign(campaignName, sLog, cLog, iLog);

		//add it to current admin profile to be able to view
		campaigns.put(c.getName(), c); 
		
		//upload to the database for a specific user
		Loader.uploadCampaign(c, owner);
	}

	public void loadCampaign(String campaignName) {
		campaigns.computeIfAbsent(campaignName, k -> Loader.loadCampaignAdmin(super.getUsername(), campaignName));
	}
	
	public boolean addUser(String name, char[] password) {
		if (name != null && !name.equals("") && password != null & password.length != 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(password);
			return Loader.createClient(name, sb.toString());
		}
		return false;
	}
	
	public void removeCampaign(String name) {
		campaigns.remove(name);
	}

}
