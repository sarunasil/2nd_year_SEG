package test;

/**Tests if the calculated values are indeed correct using testing log files
 * @author andreas_paps
 */
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import users.Admin;
import utils.DBInitializer;

public class DataTesting {
	private static Admin admin;
	
	@BeforeClass
	public static void init() {
		DBInitializer.initilizeDatabase();
		admin = new Admin("SEG", "SEG");

		admin.loadCampaign("testCampaign");
	}
	
	@Test
	public void NumberOfClicks() {
		int nofclicks=admin.getCampaign("testCampaign").getNumberOfClicks();
		assertEquals(15,nofclicks);
	}
	
	@Test
	public void NumberOfUniqueClicks() {
		int nofunclicks=admin.getCampaign("testCampaign").getNumberOfUniques();
		assertEquals(14,nofunclicks);
	}
	
	@Test
	public void NumberOfBounces() {
		int nofbounces=admin.getCampaign("testCampaign").getNumberOfBounces();
		assertEquals(0,nofbounces);
	}
	
	@Test
	public void NumberOfConversions() {
		int nofconv=admin.getCampaign("testCampaign").getNumberOfConversions();
		assertEquals(2,nofconv);
	}
	
	@Test
	public void NumberOfImpressions() {
		int nofimpr=admin.getCampaign("testCampaign").getNumberOfImpressions();
		assertEquals(17,nofimpr);
	}
	
	@Test
	public void BounceRate() {
		float bouncerate=admin.getCampaign("testCampaign").getBounceRate();
		Assert.assertEquals(0.0, bouncerate,0.0);
	}	
	
	@Test
	public void TotalCost() {
		float totalcost=admin.getCampaign("testCampaign").getTotalCost();
		Assert.assertEquals(73.655555,totalcost,0.000001);
	}	
	
	@Test
	public void ClickThroughRate() {
		float ctr=admin.getCampaign("testCampaign").getCtr();
		Assert.assertEquals(0.882353,ctr,0.000001);
	}	
	
	@Test
	public void CostPerAcquisition() {
		float cpa=admin.getCampaign("testCampaign").getCpa();
		Assert.assertEquals(36.827778,cpa,0.000001);
	}	
	
	@Test
	public void CostPerClick() {
		float cpc=admin.getCampaign("testCampaign").getCpc();
		Assert.assertEquals(4.9103704,cpc,0.000001);
	}	

	@Test
	public void CostPerThousandImpressions() {
		float cpm=admin.getCampaign("testCampaign").getCpm();
		Assert.assertEquals(4332.6798,cpm,0.00015);
	}	

}
