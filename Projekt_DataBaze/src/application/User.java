package application;

//klasa user me objektet e saj me getter dhe setter (read,write)
//-->qe do te perdoren nga TableView  tblPasegjeret i klases kontrollUser
public class User {
	private int id;
	private String emerPasegjeri;
	private String mbiemrPasegjeri;
	private int nrUserTreni;
	private String emriUserTreni;
	private String nisjaUser;
	private String mberritjaUser;
	private String dataUser;
	private String oraUser;
	private Double cmimiUser;
	    
	public User(int id, String emri, String mbimeri, int nrUserTreni, String emriUserTreni,String nisjaUser, String mberritjaUser, String dataUser, String oraUser, Double cmimiUser) {
		this.id = id;
		this.emerPasegjeri = emri;
		this.mbiemrPasegjeri = mbimeri;
		this.nrUserTreni = nrUserTreni;
		this.emriUserTreni = emriUserTreni;
		this.nisjaUser = nisjaUser;
		this.mberritjaUser = mberritjaUser;
		this.dataUser = dataUser;
		this.oraUser = oraUser;
		this.cmimiUser = cmimiUser;
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getEmerPasegjeri() {
		return emerPasegjeri;
	}

	public void setEmerPasegjeri(String emerPasegjeri) {
		this.emerPasegjeri = emerPasegjeri;
	}

	public String getMbiemrPasegjeri() {
		return mbiemrPasegjeri;
	}

	public void setMbiemrPasegjeri(String mbiemrPasegjeri) {
		this.mbiemrPasegjeri = mbiemrPasegjeri;
	}

	public int getNrUserTreni() {
		return nrUserTreni;
	}

	public void setNrUserTreni(int nrUserTreni) {
		this.nrUserTreni = nrUserTreni;
	}

	public String getEmriUserTreni() {
		return emriUserTreni;
	}

	public void setEmriUserTreni(String emriUserTreni) {
		this.emriUserTreni = emriUserTreni;
	}

	public String getNisjaUser() {
		return nisjaUser;
	}

	public void setNisjaUser(String nisjaUser) {
		this.nisjaUser = nisjaUser;
	}

	public String getMberritjaUser() {
		return mberritjaUser;
	}

	public void setMberritjaUser(String mberritjaUser) {
		this.mberritjaUser = mberritjaUser;
	}

	public String getDataUser() {
		return dataUser;
	}

	public void setDataUser(String dataUser) {
		this.dataUser = dataUser;
	}

	public String getOraUser() {
		return oraUser;
	}

	public void setOraUser(String oraUser) {
		this.oraUser = oraUser;
	}

	public Double getCmimiUser() {
		return cmimiUser;
	}

	public void setCmimiUser(Double cmimiUser) {
		this.cmimiUser = cmimiUser;
	} 
	
}
