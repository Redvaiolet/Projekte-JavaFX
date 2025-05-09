package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;

public class kontrollLogIn {
	@FXML private Button enter;
	@FXML private Button back;

	@FXML private TextField txtname;
    @FXML private PasswordField txtpassword;
	
	//metode ndihmese per te shfaqur Alert
    private void showAlert(AlertType alertType, String title, String content) {
        AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Background.wav");
        clip.play();

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
	
	
	//inicializimi kur hapet dritarja
	public void initialize() {
		  enter.setOnAction(event -> { logIn_kontroll_DB(); });
		  
		  
		  back.setOnAction(event -> {
		         Main.stage1.show();
		         Main.stage2.hide();
		    });
	    }
	
	//metoda qe kontrollon lonin
	public void logIn_kontroll_DB() {//kontrollojme databazen per username dhe password per te dhene akses
	 try {
		  String username = txtname.getText();
		  String password = txtpassword.getText();
		
		  if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
			 showAlert(AlertType.WARNING, "Gabim!", "Ju lutemi plotesoni te gjitha fushat.");
			 return;
		  }
		
		    //lidhemi me databazen
			Connection conn = Databaze.methodConnect();
			//shohim tabeln qe ka te dheant e aksesit
			String sql = "SELECT * FROM login WHERE Name = ? AND Password = ?";
			PreparedStatement prepst = conn.prepareStatement(sql);
			prepst.setString(1, username);
			prepst.setString(2, password);
			
			//per te ekzekutaur komanden ne sql
			ResultSet rs = prepst.executeQuery();
			
		
			if(rs.next()) {
			 //nqs ekziston useri dhe passwordi --> hap dritaren 3
			 Main.k3.refreshtabelaAdmin(); //rifresko tabelen sapo te hapet dritarja	
			 Main.stage3.show();
			 Main.stage2.hide();
			 
			 //kontrollojme per vendet ne trena 
			 Main.k3.kontroll_kapacitet();
			 
			 //pastrojme textfieldsat
			 txtname.clear();
		     txtpassword.clear();
			}else {
				showAlert(AlertType.WARNING, "Gabim!", "Username ose Password i gabuar, ju luetmi kontrolloni perseri.");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
