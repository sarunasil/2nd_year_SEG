package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Impression.Age;
import model.Impression.Gender;
import model.Impression.Income;
import users.Admin;
import utils.DBInitializer;

/**Tests if the log files are parsed correctly
 * @author andreas_paps
 */
public class ParserTesting {
	
private static Admin admin;
	
	@BeforeClass
	public static void init() {
		DBInitializer.initilizeDatabase();
		admin = new Admin("SEG", "SEG");
		File serverLog = new File("./datasets/custom/server_log_test.csv");
		File clickLog = new File("./datasets/custom/click_log_test.csv");
		File impressionLog = new File("./datasets/custom/impression_log_test.csv");
		try {
			admin.provideLogFiles("SEG","testCampaign", serverLog, clickLog, impressionLog);
			//System.out.println("No of clicks->"+admin.getCampaign("campaignName2700").getNumberOfClicks());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Impression Log File Testing
	
	@Test
	public void Gender() {
		Gender gender=admin.getCampaign("testCampaign").getImpressions().get(0).GetGender();
		Assert.assertEquals("MALE",gender.toString());
		
		gender=admin.getCampaign("testCampaign").getImpressions().get(3).GetGender();
		Assert.assertEquals("FEMALE",gender.toString());
	}

	@Test
	public void AgeGroup() {
		Age age=admin.getCampaign("testCampaign").getImpressions().get(16).GetAge();
		Assert.assertEquals("BW25_34",age.toString());
		
		age=admin.getCampaign("testCampaign").getImpressions().get(14).GetAge();
		Assert.assertEquals("LESS25",age.toString());

		age=admin.getCampaign("testCampaign").getImpressions().get(1).GetAge();
		Assert.assertEquals("BW35_44",age.toString());

		age=admin.getCampaign("testCampaign").getImpressions().get(9).GetAge();
		Assert.assertEquals("BW45_54",age.toString());

		age=admin.getCampaign("testCampaign").getImpressions().get(7).GetAge();
		Assert.assertEquals("MORE54",age.toString());
	}
	
	@Test
	public void CostTest() {
		Float cost=admin.getCampaign("testCampaign").getImpressions().get(1).GetCost();
		Assert.assertEquals(0.002762,cost,0.000001);

		cost=admin.getCampaign("testCampaign").getImpressions().get(15).GetCost();
		Assert.assertEquals(0.000000,cost,0.000001);
	}
	
	@Test
	public void ImprIDTest() {
		long id=admin.getCampaign("testCampaign").getImpressions().get(0).getUserId();
		Assert.assertEquals(4620864431353617408L,id,0);

		id=admin.getCampaign("testCampaign").getImpressions().get(6).getUserId();
		Assert.assertEquals(1804784971213607936L,id,0);
	}
	

	@Test
	public void ImpDateTest() {
		LocalDateTime date=admin.getCampaign("testCampaign").getImpressions().get(16).getDate();
		Assert.assertEquals("2015-01-01 12:00:25",date.toString().replaceAll("T", " "));

		date=admin.getCampaign("testCampaign").getImpressions().get(7).getDate();
		Assert.assertEquals("2015-01-01 12:00:13",date.toString().replaceAll("T", " "));
	}	
	
	
	@Test
	public void Context() {
		model.Impression.Context cont=admin.getCampaign("testCampaign").getImpressions().get(1).GetContext();
		Assert.assertEquals("NEWS",cont.toString());

		cont=admin.getCampaign("testCampaign").getImpressions().get(15).GetContext();
		Assert.assertEquals("SHOPPING",cont.toString());

		cont=admin.getCampaign("testCampaign").getImpressions().get(3).GetContext();
		Assert.assertEquals("SOCIAL_MEDIA",cont.toString());

		cont=admin.getCampaign("testCampaign").getImpressions().get(13).GetContext();
		Assert.assertEquals("BLOG",cont.toString());

		cont=admin.getCampaign("testCampaign").getImpressions().get(13).GetContext();
		Assert.assertEquals("BLOG",cont.toString());

		cont=admin.getCampaign("testCampaign").getImpressions().get(0).GetContext();
		Assert.assertEquals("HOBBIES",cont.toString());

		cont=admin.getCampaign("testCampaign").getImpressions().get(0).GetContext();
		Assert.assertEquals("HOBBIES",cont.toString());

		cont=admin.getCampaign("testCampaign").getImpressions().get(16).GetContext();
		Assert.assertEquals("TRAVEL",cont.toString());
	}

	@Test
	public void Income() {
		Income cont=admin.getCampaign("testCampaign").getImpressions().get(16).GetIncome();
		Assert.assertEquals("MEDIUM",cont.toString());

		cont=admin.getCampaign("testCampaign").getImpressions().get(0).GetIncome();
		Assert.assertEquals("HIGH",cont.toString());

		cont=admin.getCampaign("testCampaign").getImpressions().get(14).GetIncome();
		Assert.assertEquals("LOW",cont.toString());
	}
	
	
	//Click Log File Testing
	
	@Test
	public void ClickDateTest() {
		LocalDateTime date=admin.getCampaign("testCampaign").getClicks().get(0).getDate();
		Assert.assertEquals("2015-01-01 12:01:21",date.toString().replaceAll("T", " "));

		date=admin.getCampaign("testCampaign").getClicks().get(3).getDate();
		Assert.assertEquals("2015-01-01 12:02:57",date.toString().replaceAll("T", " "));
	}
	
	@Test
	public void ClickIDTest() {
		long id=admin.getCampaign("testCampaign").getClicks().get(3).getUserId();
		Assert.assertEquals(3777890599251549184L,id);

		id=admin.getCampaign("testCampaign").getClicks().get(13).getUserId();
		Assert.assertEquals(5901145211964154880L,id);
	}

	@Test
	public void ClickCostTest() {
		float cost=admin.getCampaign("testCampaign").getClicks().get(14).getClickCost();
		Assert.assertEquals(9.555555,cost,0.0000015);

		cost=admin.getCampaign("testCampaign").getClicks().get(8).getClickCost();
		Assert.assertEquals(0.000000,cost,0.0000015);
	}
	
	//Server Log File Testing
	
	@Test
	public void Conversion() {
		boolean conv=admin.getCampaign("testCampaign").getSessions().get(8).GetConversion();
		Assert.assertEquals(false,conv);

		conv=admin.getCampaign("testCampaign").getSessions().get(9).GetConversion();
		Assert.assertEquals(true,conv);
	}
	
	@Test
	public void PagesViewedTest() {
		int pages=admin.getCampaign("testCampaign").getSessions().get(9).GetPagesCount();
		Assert.assertEquals(15,pages);

		pages=admin.getCampaign("testCampaign").getSessions().get(3).GetPagesCount();
		Assert.assertEquals(3,pages);
	}
	
	@Test
	public void ServerIDTest() {
		long id=admin.getCampaign("testCampaign").getSessions().get(9).getUserId();
		Assert.assertEquals(9205559084602150912L,id);
		
		id=admin.getCampaign("testCampaign").getSessions().get(11).getUserId();
		Assert.assertEquals(5024355283837802496L,id);
	}
	
	@Test
	public void EntryDateTest() {
		LocalDateTime date=admin.getCampaign("testCampaign").getSessions().get(3).GetEntryDate();
		Assert.assertEquals("2015-01-01 12:02:58",date.toString().replaceAll("T", " "));

		date=admin.getCampaign("testCampaign").getSessions().get(13).GetEntryDate();
		Assert.assertEquals("2015-01-01 12:07:40",date.toString().replaceAll("T", " "));
	}
	
	@Test
	public void ExitDateTest() {
		LocalDateTime date=admin.getCampaign("testCampaign").getSessions().get(18).GetExitDate();
		Assert.assertEquals("2015-01-01 12:15:06",date.toString().replaceAll("T", " "));

		date=admin.getCampaign("testCampaign").getSessions().get(12).GetExitDate();
		Assert.assertEquals("2015-01-01 12:08:49",date.toString().replaceAll("T", " "));
	}
	
}
