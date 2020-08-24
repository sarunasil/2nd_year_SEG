/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package users;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.Campaign;
import utils.Loader;

/**Client can only view information
 * @author sarunasil
 * @see Admin
 * @version The simplest insecure version of the user.
 */

public class Client extends User {
	protected Map<String, Campaign> campaigns;

	//constructor
	public Client(String username, String password) {
		super(username, password);

		campaigns = new HashMap<String, Campaign>();
		for (String str : Loader.getCampaignNames(super.getUsername())) {
			campaigns.put(str, null);
		}
	}
	
	public void loadCampaign(String campaignName) {
		campaigns.computeIfAbsent(campaignName, k -> Loader.loadCampaign(super.getUsername(), campaignName));
	}

	//getters
	public Set<String> getCampaignNames(){
		return campaigns.keySet();
	}
	
	public Campaign getCampaign(String name) {		
		return campaigns.get(name);
	}
	
}
