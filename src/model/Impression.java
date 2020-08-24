/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package model;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;

/**stores one impression log entry
 * @author sarunasil
 *
 */

public class Impression extends Unit{
	public enum Gender {MALE, FEMALE};
	public enum Age {LESS25, BW25_34, BW35_44, BW45_54, MORE54};
	public enum Income{LOW, MEDIUM, HIGH};
	public enum Context{NEWS, SHOPPING, SOCIAL_MEDIA, BLOG, HOBBIES, TRAVEL};

	private Gender gender;
	private Age age;
	private Income income;
	private Context context;
	private float cost;

	//constructor with correct types
	public Impression(LocalDateTime date, long userId, Gender gender, Age age, Income income, Context context, float cost) {
		super(date, userId);
		
		this.gender = gender;
		this.age = age;
		this.income = income;
		this.context = context;
		this.cost = cost;
	}
	
	//constructor with data conversion
	public Impression(String[] data) throws Exception {
		super(Arrays.copyOfRange(data, 0, 2));
		
		if (data.length!=7) throw new Exception("Incorrect number of paramaters");
		
		//converts gender, age, income, context and cost
		gender = toGender(data[2]);
		age = toAge(data[3]);
		income = toIncome(data[4]);
		context = toContext(data[5]);
		cost = Float.parseFloat(data[6]);
	}
	
	//methods to convert each array argument to specific enum value
	private Gender toGender(String g) throws ParseException {
		Gender gender = Gender.valueOf(g.toUpperCase());
		if (gender==null) throw new ParseException("Gender parse error", 2);
		return gender;
	}
	
	private Age toAge(String a) throws ParseException {
		switch (a) {
		case "<25":
			return Age.LESS25;
		case "25-34":
			return Age.BW25_34;
		case "35-44":
			return Age.BW35_44;
		case "45-54":
			return Age.BW45_54;
		case ">54":
			return Age.MORE54;
		default:
			throw new ParseException("Age parse error", 3);
		}
	}
	
	private Income toIncome(String i) throws ParseException{
		Income income = Income.valueOf(i.toUpperCase());
		if (income==null) throw new ParseException("Income parse error", 4);
		return income;
	}
	
	private Context toContext(String c) throws ParseException{
		Context context = Context.valueOf(c.toUpperCase().replace(' ', '_'));
		if (context==null) throw new ParseException("Context parse error", 5);
		return context;
	}
	
	//getters
	public Gender GetGender() {
		return gender;
	}
	
	public Age GetAge() {
		return age;
	}

	public Income GetIncome() {
		return income;
	}
	
	public Context GetContext() {
		return context;
	}
	
	public float GetCost() {
		return cost;
	}
}
