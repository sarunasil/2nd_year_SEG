/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import model.Campaign;
import users.Admin;

/**For testing and storing top level data model references 
 * @author sarunasil
 * 
 */

public class MainModel {
	private Admin admin;
	
	public MainModel() {

		DBInitializer.initilizeDatabase();
		
		admin = new Admin("SEG16", "SEG16");
		if ( !Loader.createAdmin("SEG16", "SEG16") ) {
            System.err.println("User "+admin.getUsername()+" was not created");
        }

//		File serverLog = new File("./datasets/2_month_campaign/server_log.csv");
//		File clickLog = new File("./datasets/2_month_campaign/click_log.csv");
//		File impressionLog = new File("./datasets/2_month_campaign/impression_log.csv");
        //
		File serverLog = new File("./datasets/2_week_campaign/server_log.csv");
		File clickLog = new File("./datasets/2_week_campaign/click_log.csv");
		File impressionLog = new File("./datasets/2_week_campaign/impression_log.csv");
		
//		try {
//			admin.provideLogFiles("SEG16","test", serverLog, clickLog, impressionLog);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		admin.loadCampaign("test");
		
		for (String s : Loader.getCampaignNames(admin.getUsername())) {
			System.out.println(s);
		}
		
		System.out.println(admin.getCampaign("test").getClicks().size());
		System.out.println(admin.getCampaign("test").getImpressions().size());
		System.out.println(admin.getCampaign("test").getSessions().size());
		
		System.out.println(Verifier.verify("SE1G", "SEG"));

	}

	public Set<String> getCampaignNames(){
		return admin.getCampaignNames();
	}

	public Campaign getCampaign(String name) {
		return admin.getCampaign(name);
	}

	public static void main(String[] args) {
		new MainModel();
	}

}
