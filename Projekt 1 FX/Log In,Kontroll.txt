package application;


import javafx.fxml.FXML;//import anotacionin @FXML per te lidhur fushat dhe metodat me skedarin FXML
import javafx.fxml.FXMLLoader;//import FXMLLoader per te ngarkuar nderfaqen grafike nga skedari FXML
import javafx.scene.Parent; //import Parent,qe eshte elementi baze per skenen  --->root
import javafx.scene.Scene;//import Scene per te krijuar skena grafike
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;//importon PasswordField per te marre inputin e fjalekalimit nga perdoruesi
import javafx.scene.control.TextField;//importon TextField per te marre inputin e emrit te perdoruesit
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;//importon Image per te vendosur ikonen e aplikacionit
import javafx.scene.media.AudioClip; //import per zerin 
import javafx.stage.Stage;//importon Stage per te menaxhuar dritaret e aplikacionit
import java.util.List;

import application2.Kontroll2;//import klasen Kontroll2,qe permban listen e perdoruesve te regjistruar
import application2.Perdoruesi;//importon klasen Perdoruesi,qe perfaqeson nje perdorues me emër dhe fjalëkalim

//klasa kontroll qe menaxhon hyrjet e perdoruesit ne program
public class Kontroll {
	@FXML
	private TextField emri; //fusha e inputit per emrin e perdoruesit,e lidhur me skdarin FXML

	@FXML
	private PasswordField password;//fusha e inputit per passwordin e perdoruesit

	
	
	 //metode per te shfaqur aleretet  ne ekran perdoruesit 
    public void shfaqAlert(AlertType alertType, String title, String content) {
    	//per audio
    	AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Background.wav");
    	clip.play(); 
    	
    	Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();//shfaqim alertet 
    }
    
    //metoda per te shfaqur alert konfirmimin e log in
    public void konfirmimAlert(AlertType alertType, String title, String content) {
    	//per audio
    	AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Unlock.wav");
    	clip.play(); 
    	
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();//shfaqim alertet 
    }
	
	// metoda per te kontrolluar inputet e perdoruesit gjate Log In-->hapja e dritares 03 
	public void kontrollnPerdoruesin() {
		try {
            
			//kopjoj listen e perdorueseve te rregjistruar nga klasa Kontroll2 me obj perdoruesit_e_loguar--> e Sign In
			List<Perdoruesi> perdoruesit_e_loguar = Kontroll2.getPerdoruesi();

			//kontroll nqs lisat eshte bosh ,per mungese llogarish ne program
			if (perdoruesit_e_loguar.size() == 0) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Nuk kemi asnje student te rregjistruar ne sistem.Ju lutemi rregistrohuni.");
				return; //per te ndalur veprimet 
			}

			//merr te dhenat e futura nga perdoruesi ne fushat textfield dhe passwordfield
			String emerPerdoruesi = emri.getText();
			String passwordPerdoruesi = password.getText();
			
			//kontroll nqs fusha e Username eshte bosh
			if(emerPerdoruesi == null || emerPerdoruesi.length() == 0) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Username eshte bosh.Ju lutemi plotesojeni ate.");
				return; //per te ndalur veprimet 
			}
			
			//kontroll nqs fusha e Password eshte bosh
			if(passwordPerdoruesi == null || passwordPerdoruesi.length() == 0) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Password eshte bosh.Ju lutemi plotesojeni ate.");
				return; //per te ndalur veprimet 
			}
			
			
            //variabel per te kontrolluar nese perdoruesi gjendet ne liste 
			boolean ugjet = false;

			//interim me nje for-each (el per el) 
			for (Perdoruesi perdorues : perdoruesit_e_loguar) {//per te kontrolluar nese username dhe password qe ka vendosur perdoruesi a jane te barabarte me log-et e listes  
				if (perdorues.getEmri().equals(emerPerdoruesi) && perdorues.getPassword().equals(passwordPerdoruesi)) {
					ugjet = true; //perdoruesi u gjet                                                //equals perdoret per te krahasuar permbajtjen e dy stringa ,vjen nga klasa String    
			                                                                                        //dhe ne krahasojme nese Username dhe Password qe ka vendosur perdoruesi eshte e njejte me
					konfirmimAlert(AlertType.INFORMATION, "Informacion", "Hyrja ne aplikacion u krye me sukses!");//ndonjeren prej acc e loguara ne listen perdoruesit e loguar (List, krahasojm karakter per karakter)
					
					//mbyllet dritarja aktuale
                    Stage stage_aktual = (Stage) emri.getScene().getWindow();
                    stage_aktual.close();
			
					//hap dritaren e re 
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application3/dritare_03.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root,719,492);//krijojm nje skene te re me permasa te caktuara 
                    Stage stage_i_ri = new Stage();
                    stage_i_ri.getIcons().add(new Image(getClass().getResourceAsStream("/android-icon-192x192.png")));

                    stage_i_ri.setScene(scene);//vendosim skenen ne dritaren e re me metoden setStage()
                    stage_i_ri.setTitle("Universiteti Luigj Gurakuqi"); //vendosim titull dritares 
                    stage_i_ri.show();//shfaqim dritaren e re

                    return; //dalim nga metoda pasi hapim dritaren e re

				}
			}
            
			//nqs asnje perdorues nuk u gjet
			if (ugjet == false) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Username ose Password eshte i gabuar.Ju lutem kontrolloni edhe nje here te dhenat.Mbase nuk jeni rregjistruar akoma.");
				return;//per te ndalur veprimet 
			}
		} catch (Exception e){//kap gabimet te tjera ne lidhje per JavaFX 
			e.printStackTrace();
		}
	}
	
	//metoda per tu kthyer ne faqen Sign In 
		public void back_SignIn() {
			try {
				//mbyllim dritaren aktuale
				Stage currentStage = (Stage) emri.getScene().getWindow();
				currentStage.close();

				
				//hap dritaren e Sign In 
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application2/dritare_02.fxml"));
	            Parent root = loader.load();

	            Scene scene = new Scene(root, 600, 400);//krijojme nje skene te re 
	            Stage stage_i_ri = new Stage();
	            stage_i_ri.getIcons().add(new Image(getClass().getResourceAsStream("/android-icon-192x192.png")));

	            stage_i_ri.setScene(scene);//vendosim sknen e re 
	            stage_i_ri.setTitle("Universiteti Luigj Gurakuqi");//vendosim titullin e dritares
	            stage_i_ri.show();//shfaq dritaren e Sign In
		
			}catch(Exception e) {//kap gb qe mund te shfaqe JavaFX
			e.printStackTrace();
		}
 }
}
