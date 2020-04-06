package UserManager;

@SuppressWarnings("unused")
public class User {
	private String fname, lname, email, password, gender;


	private boolean admin;

	public User(String fname, String lname, String email, String password, String gender, boolean admin) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User{" +
				"fname='" + fname + '\'' +
				", lname='" + lname + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", gender='" + gender + '\'' +
				", admin='" + admin + '\'' +
				'}';
	}

	public String getfname() {
		return this.fname;
	}

	public String getlname() {
		return this.lname;
	}

	public String getname() {
		return (this.fname + " " + this.lname);
	}

	public String getemail() {
		return this.email;
	}

	public String getpassword() {
		return this.password;
	}

	public String getgender() {
		return this.gender;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void setfname(String fname) {
		this.fname = fname;
	}

	public void setlname(String lname) {
		this.lname = lname;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public void setgender(String gender) {
		this.gender = gender;
	}

	public boolean isValid() {
		return (this.fname != null &&
				this.lname != null &&
				this.email != null &&
				this.password != null &&
				this.gender != null &&
				this.fname.length() > 0 &&
				this.lname.length() > 0 &&
				this.email.length() > 0 &&
				this.password.length() > 0 &&
				this.gender.length() > 0);
	}
}

