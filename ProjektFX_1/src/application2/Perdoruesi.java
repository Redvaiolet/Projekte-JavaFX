package application2;

//klasa perdorues qe perfaqeson nje perdorues ne sistem
public class Perdoruesi {
    
	//variabla/atributet private qe mbajne te dhenat e perdoruesit
	private String emri;  //perdoruesi ka nje emer 
	private String email; //nje email
	private String password; //nje fjalkalim
	
	//konstruktor qe inicializon atributet e perdoruesit
	//ku merr tre argumenta
	public Perdoruesi(String emri, String email, String password) {
		this.emri = emri;  //inicializojme emrin qe i referohet variablit te emrit te kesaj klase me this
		this.email = email; //inicializojme email me vleren e dhene
		this.password = password; //inicializojme password me vleren e dhne 
	}
    
	//metoda getter per lexuar/per te marre emrin e perdoruesit 
	public String getEmri() {
		return emri;
	}

	//getter per te lexuar email-in
	public String getEmail() {
		return email;
	}

	//getter per te lexuar passwordin 
	public String getPassword() {
		return password;
	}
		
}
