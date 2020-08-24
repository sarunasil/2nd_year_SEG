/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package test;

import java.time.LocalDateTime;
import java.util.Date;

import controller.*;
import model.Campaign;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Impression.Age;
import model.Impression.Gender;
import model.Impression.Income;
import users.Admin;
import utils.DBInitializer;
import utils.Loader;
import utils.Verifier;

/**Tests are the database queries correctly parsed and loaded
 * @author sarunasil
 *
 */

public class ControllerTesting {
	
private static AdminTest admin;
	
	@BeforeClass
	public static void init() {
		DBInitializer.initilizeDatabase();
		admin = new AdminTest("SEG", "SEG");

		admin.loadCampaign("testCampaign");

	}

	//Impression Log File Testing
	
	@Test
	public void verifyUser() {
		LoginController login = new LoginController();
		String username = "SEG11";
		String password = "SEG11";
		Assert.assertEquals(login.verify(username, password.toCharArray()), Verifier.verify(username, password));

		username = "NOSEG";
		password = "NOSEG";
		Assert.assertEquals(login.verify(username, password.toCharArray()), Verifier.verify(username, password));
	}

	@Test
	public void mainController() {
		MainController mainController = new MainController(admin);
		AddCampaignController acc = new AddCampaignController(admin);
		Assert.assertEquals(mainController.getController("AddCampainController"), null);
		mainController.setController(acc, "AddCampainController");
		Assert.assertEquals(mainController.getController("AddCampainController"), acc);
	}

	@Test
	public void lfController() {
		LFController lf = new LFController(admin);
		Campaign c = new Campaign("CampaignOneTesting", 1,2,3,4,5,6,7,8,9,10,11);
		int size = Loader.getAllCampaigns().size();
		Assert.assertEquals(lf.getCampaignNames().size(), size);
		admin.addCampaign("CampaignOneTesting", c);
		Assert.assertEquals(lf.getCampaignNames().size(), size+1);
		Assert.assertEquals(lf.getNumberOfClicks("CampaignOneTesting"), 1);
		Assert.assertEquals(lf.getNumberOfUniques("CampaignOneTesting"), 2);
		Assert.assertEquals(lf.getNumberOfBounces("CampaignOneTesting"), 3);
		Assert.assertEquals(lf.getNumberOfConversions("CampaignOneTesting"), 4);
		Assert.assertEquals(lf.getNumberOfImpressions("CampaignOneTesting"), 5);
		Assert.assertEquals(lf.getTotalCost("CampaignOneTesting"), 6, 0);
		Assert.assertEquals(lf.getCpa("CampaignOneTesting"), 7, 0);
		Assert.assertEquals(lf.getCpc("CampaignOneTesting"), 8,0);
		Assert.assertEquals(lf.getCpm("CampaignOneTesting"), 10, 0);
		Assert.assertEquals(lf.getCtr("CampaignOneTesting"), 9, 0);
		Assert.assertEquals(lf.getBounceRate("CampaignOneTesting"), 11, 0);
	}

	@Test
	public void addClientFrameController() {
		AddClientFrameController controller = new AddClientFrameController(admin);
		Date date = new Date();
		String username = date.toString();
		char[] password = "UniqueTestT".toCharArray();
		Assert.assertEquals(controller.submit(username, password), true);
		Assert.assertEquals(controller.submit(username, password), false);
	}

	
	
}
