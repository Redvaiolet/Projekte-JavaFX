package application2;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class Kontroll2 {

	//anotimi tregon se keto fusha lidhen me elementet ne skedarin FXML
	@FXML
	private TextField txtemail;

	@FXML
	private TextField txtemri;

	@FXML
	private TextField txtpassword;

	//liste ku do te ruehn te dheant e perdoruesve te rregjistruar 
	private static List<Perdoruesi> perdorues = new ArrayList<>();
	
	// metode getter per te marre listen e te regjistruarve(perdoret ne Log In)
	public static List<Perdoruesi> getPerdoruesi() {
		return perdorues;
	}

	
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
     
   //metoda per te shfaqur alert konfirmimin e sign in
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
	
	//metoda per te rregjistruar perdoruesin Sign In 
	public void vendosPerdorues() {
		try {
             //marrja e te dhenave mga fushat e textfield
			String emri = txtemri.getText();
			String email = txtemail.getText();
			String password = txtpassword.getText();
			
			//kontroll nqs Username eshte bosh 
			if(emri == null || emri.length() == 0) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Username eshte bosh.Ju lutemi plotesojeni ate.");
				return;
			}
			
			//kontroll nqs Email eshte bosh 
			if(email == null || email.length() == 0) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Email eshte bosh.Ju lutemi plotesojeni ate.");
				return;
			}
			
			//kontroll nqs fush e Password eshte bosh
			if(password == null || password.length() == 0) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Password eshte bosh.Ju lutemi plotesojeni ate.");
				return;
			}
			

			//shtojme perdoruesin ne listë me metoden add() duke krijuar nje objekt te ri te klases Perdoruesi
			//dhe thirret konstruktori i saj per te inicializuar atributet
			perdorues.add(new Perdoruesi(emri, email, password));
            
			konfirmimAlert(AlertType.INFORMATION, "Informacion", "Studenti u rregjistrua ne aplikacion!");
			
			//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
            Stage stage_aktual = (Stage) txtemri.getScene().getWindow();
            stage_aktual.close();
			
			//ngarkojme dhe hapim dritaren e re
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dritatre_01.fxml"));
            Parent root = loader.load();
            
            //krijojme nje skene te re dhe i caktojme permasat
            Scene scene = new Scene(root, 600, 400);
            Stage stage_i_ri = new Stage();
            
            //vendosim nje ikone dritares 
            stage_i_ri.getIcons().add(new Image(getClass().getResourceAsStream("/android-icon-192x192.png")));

            //stage e re e vendosim ne scene/dritare dhe i vendosim nje titull 
            stage_i_ri.setScene(scene);
            stage_i_ri.setTitle("Universiteti Luigj Gurakuqi");
            stage_i_ri.show();
	
		} catch (Exception e) {//kap dhe shfaq gabimet e JavaFX ne konsole 
			e.printStackTrace();
		}

	}

	
	//metode per tu kthyre ne faqen e hyrjes (Log In)
	public void back_LogIn() {
	   try {
		   
		//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
        Stage currentStage = (Stage) txtemri.getScene().getWindow();
        currentStage.close();
		
		//ngarkon dhe hap faqen e hyrjes (Log In) 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dritatre_01.fxml"));
        Parent root = loader.load();

        //krojojme nje skene te re per faqen kryesore
        Scene scene = new Scene(root, 600, 400);
        Stage stage_i_ri = new Stage();
        
        //i vendosim nje imazh dritares
        stage_i_ri.getIcons().add(new Image(getClass().getResourceAsStream("/android-icon-192x192.png")));

        //caktojme skene dhe titullin e dritares 
        stage_i_ri.setScene(scene);
        stage_i_ri.setTitle("Universiteti Luigj Gurakuqi");
        //shfaq dritaren 
        stage_i_ri.show();
	
	   }catch(Exception e) {//kap gb dhe i shfaq ne konsole nese ka te JavaFX
		e.printStackTrace();
	}
  }
}
	
