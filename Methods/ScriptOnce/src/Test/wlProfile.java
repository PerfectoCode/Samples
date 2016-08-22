package Test;

public class wlProfile {
	private String fname;
	private String gender;
	private Integer age;
	private Integer ht;
	private Integer wt;
	private Integer lossG;

	public wlProfile(String name, String sex, Integer age, Integer h, Integer w, Integer lose) {
		fname = name;
		gender = sex;
		ht = h;
		wt = w;
		lossG = lose;
		this.age = age;
	}
	
	public String getFname() {
		return fname;
	}
	
	public String getGender() {
		return gender;
	}

	public Integer getHeight() {
		return ht;
	}
	
	public Integer getWeight() {
		return wt;
	}
	
	public Integer getGoal() {
		return lossG;
	}
	
	public Integer getAge() {
		return age;
	}
}
