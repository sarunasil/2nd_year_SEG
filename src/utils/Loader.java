/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Campaign;
import model.Click;
import model.Impression;
import model.Impression.Age;
import model.Impression.Context;
import model.Impression.Gender;
import model.Impression.Income;
import model.Session;
import model.Unit;

/**This utility class is responsible for loading data to and from the database
 * @author sarunasil, imladenov
 *
 *
 */

public class Loader {
	//max number of statements to be committed at one time
	private static final int MAX_UPLOAD = 100000;

	//Connection from connection
	protected static Connection connection = null;

	// ResultSet of a Query
	private static ResultSet rs;
	
	public static void set_up_connection(String dbName) {
		connection = initialiseConnection(dbName);
	}

	/**
	 * Initialise connection with the database
	 * @param url
	 * @return Statement that will deal with query execution
	 */
	protected static Connection initialiseConnection(String url) {
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection("jdbc:sqlite:"+url);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * General method that actually deals with the query - every added admin or client is considered as a user in the DB
	 * @param username
	 * @param password
	 * @param level
	 * @return Boolean that shows if the execution is successful
	 */
	private static boolean createUser(String username, String password, int level) {
		String combinationQuery = "INSERT INTO User('Username', 'Password', 'Level') VALUES('" + username + "', '" + password + "', " + level + ")";
		try {
			connection.createStatement().executeUpdate(combinationQuery);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Helper method for creating admins (access = 1)
	 * @param username
	 * @param password
	 * @return Boolean that shows if the execution is successful
	 */
	public static boolean createAdmin(String username, String password) {
		return createUser(username, password, 1);
	}

	/**
	 * Helper method for creating clients (access = 1)
	 * @param username
	 * @param password
	 * @return Boolean that shows if the execution is successful
	 */
	public static boolean createClient(String username, String password) {
		return createUser(username, password, 0);
	}

	/**
	 * Method for taking users OR admins depending on level parameter
	 * @param level
	 * @return
	 */
	public static Set<String> getUsers(int level) {
		Set<String> set = new HashSet<String>();
		String query = "SELECT Username FROM User " +
				"WHERE Level = "+level+";";
		try {
			rs = connection.createStatement().executeQuery(query);
			while (rs.next()) {
				set.add(rs.getString("Username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return set;
	}

	/**Gets the names of all the campaigns associated with this user
	 * @param username - owner name
	 * @return Set of campaign names
	 */
	//if the user calling is an admin - get the set of all names
	public static Set<String> getCampaignNames(String username){
		Set<String> set = new HashSet<String>();
		String combinationQuery = "SELECT c.Name " + 
				"FROM Campaign c " + 
				"WHERE c.ID IN ( " + 
				"SELECT CampaignID " + 
				"FROM Ownership WHERE Ownership.Username = '"+ username +"' );";
		try {
			rs = connection.createStatement().executeQuery(combinationQuery);
			while (rs.next()) {
				set.add(rs.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(set.size());
		return set;
	}

	public static Set<String> getAllCampaigns(){
		Set<String> set = new HashSet<String>();
		String combinationQuery = "SELECT Name From Campaign";
		try {
			rs = connection.createStatement().executeQuery(combinationQuery);
			while (rs.next()) {
				set.add(rs.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("ADMIN SET" + set.size());
		return set;
	}
	
	/**This method is used to get a Campaign object from the database
	 * @param username - owner of the campaign that's required to be loaded
	 * @param campaignName - campaign name
	 * @return Campaign
	 */
	public static Campaign loadCampaign(String username, String campaignName) {
		Campaign camp = populateMainCampaign(username, campaignName, 0);
		
		if (camp!=null) {
			populateCampaign(username, camp, Parser.Type.SERVER);
			populateCampaign(username, camp, Parser.Type.CLICK);
			populateCampaign(username, camp, Parser.Type.IMPRESSION);
		}
		return camp;
	}

	/**This method is used to get a Campaign object from the database
	 * @param username - owner of the campaign that's required to be loaded
	 * @param campaignName - campaign name
	 * @return Campaign
	 */
	public static Campaign loadCampaignAdmin(String username, String campaignName) {
		Campaign camp = populateMainCampaign(username, campaignName, 1);

		if (camp!=null) {
			populateCampaign(username, camp, Parser.Type.SERVER);
			populateCampaign(username, camp, Parser.Type.CLICK);
			populateCampaign(username, camp, Parser.Type.IMPRESSION);
		}
		return camp;
	}
	
	/**This method creates a new Campaign object and loads it's general information from the database
	 * 
	 * @param user - user name of a user who has access to this database
	 * @param campaignName - name of the campaign
	 * @return initiated Campaign object with Main stats loaded
	 */
	private static Campaign populateMainCampaign(String user, String campaignName, int level) {
		String combinationQuery = "";
		if (level == 0) {
			combinationQuery = "SELECT * " +
					"FROM Campaign c " +
					"WHERE c.ID IN ( " +
					"SELECT CampaignID " +
					"FROM Ownership WHERE Ownership.Username = '"+ user +"') "+
					"AND c.Name = '" + campaignName + "';";
		} else if (level == 1) {
			combinationQuery = "SELECT * FROM Campaign c" +
					"			WHERE c.Name = '" + campaignName + "' ";
		}
		try {
			if (!combinationQuery.equals("")) {
				rs = connection.createStatement().executeQuery(combinationQuery);

				while (rs.next()) {
					return new Campaign(rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7),
							rs.getFloat(8), rs.getFloat(10), rs.getFloat(11), rs.getFloat(9), rs.getFloat(12),  rs.getFloat(13));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**Get all the clicks of a specific campaign from the database and adds them to the Campaign object
	 * @param username - user who can view this campaign
	 * @param campaign - Campaign object
	 * @param type - what type of log file information database should pull: SERVER, CLICK, IMPRESSION
	 */
	private static void populateCampaign(String username, Campaign campaign, Parser.Type type) {
		String unit = "";
		switch (type){
		case SERVER:
			unit = "Session";
			break;
		case CLICK:
			unit = "Click";
			break;
		case IMPRESSION:
			unit = "Impression";
			break;
		}
		String combinationQuery = "SELECT * " + 
				"FROM "+ unit +" u " + 
				"WHERE u.CampaignID IN " + 
				"(SELECT c.ID " + 
				"FROM Campaign c " + 
				"WHERE c.ID IN ( " + 
				"SELECT CampaignID " + 
				"FROM Ownership WHERE Ownership.Username = '"+ username +"' ) " + 
				"AND c.Name = '"+campaign.getName()+"');";
		try {
			rs = connection.createStatement().executeQuery(combinationQuery);
			
			List<Unit> units = new ArrayList<Unit>();
			while (rs.next()) {
				
				switch (type) {
				case SERVER:
					long id = rs.getLong(2);
					LocalDateTime date = LocalDateTime.parse( rs.getString(3).replace(' ', 'T') , DateTimeFormatter.ISO_DATE_TIME );
					LocalDateTime dateE=null;
					String temp = rs.getString(4);
					if (!temp.equals("null"))
						dateE = LocalDateTime.parse( rs.getString(4).replace(' ', 'T') , DateTimeFormatter.ISO_DATE_TIME );
					int pages = rs.getInt(5);
					boolean conv = ( rs.getInt(6) != 0);
					
					units.add( new Session(date, id, dateE, pages, conv) );
					break;
				case CLICK:
					id = rs.getLong(2);
					date = LocalDateTime.parse( rs.getString(3).replace(' ', 'T') , DateTimeFormatter.ISO_DATE_TIME );
					Float cost = rs.getFloat(5);
					
					units.add( new Click(date, id, cost) );
					break;
				case IMPRESSION:
					id = rs.getLong(2);
					date = LocalDateTime.parse( rs.getString(3).replace(' ', 'T') , DateTimeFormatter.ISO_DATE_TIME );
					Gender gender = Gender.values()[rs.getInt(5)];
					Age age = Age.values()[rs.getInt(6)];
					Income income = Income.values()[rs.getInt(7)];
					Context context = Context.values()[rs.getInt(8)];
					cost = rs.getFloat(9);
					
					units.add( new Impression(date, id, gender, age, income, context, cost) );
					break;
				}
				
			}
			
			Parser.appendUnits(type, campaign, units);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**Upload a campaign to the database
	 * @param c - campaign to upload
	 * @version - ASSUMPTION - campaign has to belong to at least one user 
	 */
	public static boolean uploadCampaign(Campaign c, String username) {
		int id = -1; // campaignID
		List<Impression> impressionList = c.getImpressions();
		List<Session> sessionList = c.getSessions();
		List<Click> clickList = c.getClicks();

		String campaignQuery = "BEGIN TRANSACTION;" +
				"INSERT INTO Campaign('Name', 'Clicks', 'Uniques', 'Bounces'," +
				" 'Conversions', 'Impressions', 'TotalCost', 'CTR', 'CPA', 'CPC', 'CPM', 'BounceRate')" +
				"VALUES ('"+c.getName() +"'," +
				c.getNumberOfClicks()+","+
				c.getNumberOfUniques() +"," +
				c.getNumberOfBounces() + "," +
				c.getNumberOfConversions() + "," +
				c.getNumberOfImpressions() + "," +
				c.getTotalCost() + "," +
				c.getCtr() + "," +
				c.getCpa() + "," +
				c.getCpc() + ", "+
				c.getCpm() + "," +
				c.getBounceRate() + "); ";

		String campaignID = "SELECT MAX(ID) AS Maximum FROM Campaign";
		
		long start = System.currentTimeMillis(); // Used to see upload speed
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(campaignQuery);
			rs = statement.executeQuery(campaignID);
			while (rs.next()) {
				id = rs.getInt("Maximum");
			}
			
			if (id != -1) {
				generateInsertQueries(impressionList, clickList, sessionList, id, statement);
			}
			assignCampaign(id, username);
			statement.executeUpdate("COMMIT;");
			long end = System.currentTimeMillis();

			System.out.println("Time: " + (end - start) + "ms"); // Used to see upload speed
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**Add another user to be able to view a particular campaign
	 * This is how admin is able to view all campaigns
	 * @param username
	 * @return Boolean that shows if query execution is successful
	 */
	public static boolean assignCampaign(int id, String username) {
		String query = "INSERT INTO Ownership('Username', CampaignID) VALUES('"+username+"', '"+id+"');";

		try {
			connection.createStatement().executeUpdate(query);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**Builds insert sql statements and commits them in stacks of <i>MAX_UPLOAD</i> size to save Heap size.
	 * 
	 * @param impressionList
	 * @param clickList
	 * @param sessionList
	 * @param campaignId
	 * @return
	 * @throws SQLException 
	 */
	private static void generateInsertQueries(List<Impression> impressionList, List<Click> clickList, List<Session> sessionList, int campaignId, Statement stm) throws SQLException {
		StringBuilder builder = new StringBuilder();
		int counter = 0;
		
		//start uploading impressions
		for (Impression impression : impressionList) {
			++counter;
			if (counter > MAX_UPLOAD) {
				stm.executeUpdate(builder.toString());
				builder = new StringBuilder();	
				counter=0;
				System.out.println("reset");
			}
			
			builder.append("INSERT INTO Impression('UserID','Date', 'CampaignID', 'Gender', 'Age', 'Income', 'Context', 'Cost') VALUES (");
			builder.append(impression.getUserId());
			builder.append(",'");
			builder.append(impression.getDate());
			builder.append("',");
			builder.append(campaignId);
			builder.append(",");
			builder.append(impression.GetGender().ordinal());
			builder.append(",");
			builder.append(impression.GetAge().ordinal());
			builder.append(",");
			builder.append(impression.GetIncome().ordinal());
			builder.append(",");
			builder.append(impression.GetContext().ordinal());
			builder.append(",");
			builder.append(impression.GetCost());
			builder.append(");\n");
		}
		System.out.println("Impression ready");


		//start uploading clicks
		for (Click c : clickList) {
			++counter;
			if (counter > MAX_UPLOAD) {
				stm.executeUpdate(builder.toString());
				builder = new StringBuilder();	
				counter=0;
				System.out.println("reset");
			}
			
			builder.append("INSERT INTO Click('UserID', 'Date', 'CampaignID', 'Cost') VALUES (");
			builder.append(c.getUserId());
			builder.append(",'");
			builder.append(c.getDate());
			builder.append("',");
			builder.append(campaignId);
			builder.append(",");
			builder.append(c.getClickCost());
			builder.append(");\n");
		}
		System.out.println("Click ready");


		//start uploading sessions
		for (Session s : sessionList) {
			++counter;
			if (counter > MAX_UPLOAD) {
				stm.executeUpdate(builder.toString());
				builder = new StringBuilder();	
				counter=0;
				System.out.println("reset");
			}
			
			builder.append("INSERT INTO Session('UserID', 'Date', 'ExitDate', 'Pages', 'Conversion', 'CampaignID') VALUES (");
			builder.append(s.getUserId());
			builder.append(",'");
			builder.append(s.GetEntryDate());
			builder.append("','");
			builder.append(s.GetExitDate());
			builder.append("',");
			builder.append(s.GetPagesCount());
			builder.append(",");
			builder.append( (s.GetConversion())?1:0 );
			builder.append(",");
			builder.append(campaignId);
			builder.append(");\n");
		}
		System.out.println("Session ready");

		//commit anything that is left
		stm.executeUpdate(builder.toString());
	}

}
