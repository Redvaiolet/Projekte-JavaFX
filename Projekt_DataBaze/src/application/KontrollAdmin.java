package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.AudioClip;

public class KontrollAdmin {
	//array per te mbajtu karakteret speciale te palejuara qe mund te vendos perdoruesi
    private static char[] karakter_special = { '!', '@', '#', '$', '%', '&', '*', '(', ')','-' ,'_', '=', '[',']', '{', '}', ';', '"', '<', '>', '?', '\\', '|', '~', '§' };

    //butonat per shtimin,perditesimin,heqjen dhe fshirjen e te gjithe trenave dhe kthimi ne Home Page
    @FXML private Button kthehuHomePage, add, clear, remove, update;

    //TableView me emrin e listes tblTreni dhe kolonat perkatese ku columnNR eshte id e trenit dhe eshte unike 
    @FXML private TableColumn<Treni, Integer> columnNR, columnKapaciteti;
    @FXML private TableColumn<Treni, String> columnEmriTreni, columnNisja, columnMberritja, columnData, columnOra;
    @FXML private TableColumn<Treni, Double> columnBileta;
    @FXML private TableView<Treni> tblTreni;

    //textfields --> per lidhjen e fushave te tekstit
    @FXML private TextField txtCmimi, txtNr, txtOra, txtKapaciteti;

    //datapicker per zgjedhjne e dates 
    @FXML private DatePicker DataZgjedhje;
    
    //combobox per emri e trnit ,per statcionin e nisjes dhe mberritjes
    @FXML private ComboBox<String> ComboEmriTreni, ComboMberritja, ComboNisja;

    //ObservableList te tipit Treni te quajtur TrenatList
    //kjo liste do te mbaje te dhenat qe shfaqen ne TableView 'tblTreni' dhe lejon tabelen
    //te përditesohet automatikisht kur lista ndryshon,inicializohet si një ArrayList bosh
    private ObservableList<Treni> trenatList = FXCollections.observableArrayList();

    //metode ndihmese per te shfaqur alertet -->gabimet 
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
    
    //inicializimi-->perdoret per konfigurimin fillestar te kontrollAdmin
    public void initialize() {
    	//thirret metoda per te lidhur kolonat me obj e klases Treni
        lidhjeTabele();
        //thirret metoda per te mbushur tabelen me të dhena nga databaza
        populate();
        
        //thirret metoda per te mbushur combobox
        mbushcomboBox();

        //percaktojm veprimin qe do te kryhet kur klikohet butoni add-->therret metodën addTreni() me nje event hendeler 
        //dhe shprehje lambda
        add.setOnAction(event -> addTreni());
        //e njejta gje edhe per butonat e tjere 
        update.setOnAction(event -> updateTreni());
        remove.setOnAction(event -> removeTreni());
        clear.setOnAction(event -> clearTrenat());

        //per te marre trenin e zgjedhur me te dheant perkatese
        tblTreni.setOnMouseClicked(event -> Treni_i_zgjedhuar());

        
        //butoni i lidhur me nje event henderl me shprehje lambda
        //per tu kthyer ne dritatren Home Page
        kthehuHomePage.setOnAction(event -> {
          Main.stage1.show(); //e shfaq
          Main.stage3.hide(); //e mbyll -->aktualin
        });
    }

    //metoda ndihmese per te lidhur tabeln me kolonat dhe obj perkatese 
    private void lidhjeTabele() {
    	//psh lidh kolonen columnNR me atributin nrTreni te objektit Treni
        columnNR.setCellValueFactory(new PropertyValueFactory<>("nrTreni"));
        //e njata gje edhe per te tjerat 
        columnKapaciteti.setCellValueFactory(new PropertyValueFactory<>("kapaciteti"));
        columnEmriTreni.setCellValueFactory(new PropertyValueFactory<>("emriTreni"));
        columnNisja.setCellValueFactory(new PropertyValueFactory<>("nisja"));
        columnMberritja.setCellValueFactory(new PropertyValueFactory<>("mberritja"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        columnOra.setCellValueFactory(new PropertyValueFactory<>("ora"));
        columnBileta.setCellValueFactory(new PropertyValueFactory<>("cmimi"));
    }

    //metoda per te mbushur tabeln e Trenave me te dhenat e databazes te tabeles admindb
    private void populate() {
        //pastrojme listen aktuale para se ta mbushet me te dhena te reja,per te shmangur dublikimet
        trenatList.clear();
        try {
        	//merr nje lidhje me databazen duke perdorur metoden statike nga klasa Databaze
        	//sattike --> e aksesueshme nga klasa te tjera 
            Connection conn = Databaze.methodConnect();
            //krijojme nje obj Statement per te ekzekutuar query SQL me metodent createStatement()
            Statement st = conn.createStatement();
            //ekzekutojm nje query SQL per te marre te gjitha kolonat nga te gjitha rreshtat e tabeles admindb
            //me metoden executeQuery()
            ResultSet rs = st.executeQuery("SELECT * FROM admindb");

           //iterojme rezultatet e query-t rresht pas rreshti me metoden next()
            while (rs.next()) {
                trenatList.add(new Treni(
                    rs.getInt("Nr"),    //merr vleren Integer nga kolona Nr
                    rs.getInt("Kapaciteti"), //merr vleren Integer nga kolona Kapaciteti
                    rs.getString("Emri"),   //merr vleren String nga kolona Emri
                    rs.getString("Nisja"),  //merr vleren String nga kolona Nisja
                    rs.getString("Mberritja"), //merr vleren String nga kolona Mberritja
                    rs.getString("Data"),  //merr vleren String nga kolona Data
                    rs.getString("Ora"),   //merr vleren String nga kolona Ora
                    rs.getDouble("Cmimi")  //merr vleren Double nga kolona Cmimi
                ));
            }
            //vendosim listen e te dhenave te trenaList ne tabeln TableView tablTreni me metodent setItems()
            tblTreni.setItems(trenatList);
        } catch (SQLException e) { //kapim gabimet 
            e.printStackTrace(); //dhe i shfaqim 
        }
    }

    //metode per te bere refresh te dhenave te tabels nga jashte kur futemi  -->tek kontrollLogIn
    public void refreshtabelaAdmin() {
        populate();
    }

    
    //metoda per te mbushur combox-et nga klasa Treni 
    private void mbushcomboBox() {
        ComboEmriTreni.setItems(Treni.getTrenaEmra());
        //me te njejtat qytete 
        ComboNisja.setItems(Treni.getQytete());
        ComboMberritja.setItems(Treni.getQytete());
    }

    //metoda per te shtuar trenin
    public void addTreni() {
    	try {
    	 //merr vlerat nga fushat e tekstit si String	
    	 String nrTreni = txtNr.getText();
    	 String kapaciteti = txtKapaciteti.getText();
    	 String cmimi = txtCmimi.getText();
    	 String ora = txtOra.getText();
    	 
         //kontroll nqs te gjitha fushat jane bosh
		  if((nrTreni == null || nrTreni.length() == 0) && (kapaciteti == null || kapaciteti.length() == 0) && (cmimi == null || cmimi.length() == 0) && (ora == null || ora.length() == 0)) {
			 shfaqAlert(AlertType.WARNING, "Gabim!", "Ju keni lene te gjitha fushat bosh.Ju lutemi plotesojini ato.");
			 return; //per te ndalur veprimet 
		  }

		
		  //kontroll per numrin e trenit
		  if (nrTreni == null || nrTreni.length() == 0) {
			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nr. i trenit nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			 return; //per te ndalur veprimet 
		  }

		  //kontrollo per karaktere speciale dhe shkronja
		  for (int i = 0; i < nrTreni.length(); i++) {
			  char c = nrTreni.charAt(i);
             
			  //kontroll per shkronja
			  if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nr. i trenit nuk mund te kete shkronja.");
	               return;
	           }

			//kontrollon per karaktere speciale
			  for (char karakter : karakter_special) {     
				 if (c == karakter) {
					 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nr. i trenit nuk mund te kete karaktere speciale.");
					 return; //per te ndalur veprimet 
				 }
			  }
		  }
		
		  
		 //kontroll per cmimin
		 if (cmimi == null || cmimi.length() == 0) {
			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Cmimi nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			 return; //per te ndalur veprimet 
		 }

		 //kontrollo per karaktere speciale dhe shkronja
		 for (int i = 0; i < cmimi.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			 char c = cmimi.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

		    //kontroll per shkronja
			 if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Cmimi nuk mund te kete shkronja.");
	               return;
	          }
             
		     //kontrollon per karaktere speciale
			 for (char karakter : karakter_special) {
				if (c == karakter) {
					 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Cmimi nuk mund te kete karaktere speciale.");
					 return; //per te ndalur veprimet 
				}
			 }
		  }
		  
		//kontroll per kapacitetin
		  if (kapaciteti == null || kapaciteti.length() == 0) {
			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Kapaciteti nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			 return; //per te ndalur veprimet 
		  }

		  //kontrollo per karaktere speciale dhe shkronja
		  for (int i = 0; i < kapaciteti.length(); i++) {
			  char c = kapaciteti.charAt(i);
              
			  //kontroll per shkronja
			  if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Kapaciteti nuk mund te kete shkronja.");
	               return;
	           }

			//kontrollon per karaktere speciale
			  for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
				 if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
					 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Kapaciteti nuk mund te kete karaktere speciale.");
					 return; //per te ndalur veprimet 
				 }
			  }
		  }
		  
		  
		//kontroll per Oren
		  if (ora == null || ora.length() == 0 || ora.length() > 10) {
			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Ora nuk mund te lihet bosh ose te jete me e madhe se 10 karkatere.Ju lutemi plotesojeni.");
			 return; //per te ndalur veprimet 
		  }

		  //kontrollo per karaktere speciale dhe shkronja
		  for (int i = 0; i < ora.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			  char c = ora.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

			 //kontroll per shkronja
			  if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Ora nuk mund te kete shkronja.");
	               return;
	           }
             
			 //kontrollon per karaktere speciale
			  for (char karakter : karakter_special) {
				 if (c == karakter) {
					 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Ora nuk mund te kete karaktere speciale.");
					 return; //per te ndalur veprimet 
				 }
			  }
		  }
		  
		  //i kthejme ne int vlerat 
		  int intTreni = Integer.parseInt(nrTreni);
		  //kontroll nese numri i trnave eshte pozitiv 
		  if(intTreni <= 0) {
			  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nr. i trenit mund te kete vetem numra pozitive.");
			  return;//ndalon veprimin 
		  }
		  
		  int intKapaciteti = Integer.parseInt(kapaciteti);
		  //kontroll nqs kapaciteti eshte < 0 (jo negativ)
		  if(intKapaciteti < 0) {
			  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Kapaciteti mund te kete vetem numra pozitive.");
			  return;//ndalo veprimin
		  }
          
		  Double dobCmimi = Double.parseDouble(cmimi);
		  //kontroll nqs cmimi eshte pozitiv 
		  if(dobCmimi <= 0) {
			  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Cmimi mund te kete vetem numra pozitive.");
			  return;
		  }
		 
		  //kontroll per combobox-in e nisjes nqs eshte zgjedhuar
		  if (ComboNisja.getValue() == null || ComboNisja.getValue().isEmpty()) {
	            shfaqAlert(AlertType.WARNING, "Gabim!", "Ju lutemi zgjidhni nje vend per Nisjen.");
	            return;
	      }
		  
		  //kontroll per combobox-in e mberritjes nqs eshte zgjedhuar
	      if (ComboMberritja.getValue() == null || ComboMberritja.getValue().isEmpty()) {
	            shfaqAlert(AlertType.WARNING, "Gabim!", "Ju lutemi zgjidhni nje vend per Mberritjen.");
	            return;
	      }
	       
	      //nisja nuk mund te jete edhe mberritja
	      if (ComboNisja.getValue().equals(ComboMberritja.getValue())) {
	            shfaqAlert(AlertType.WARNING, "Gabim!", "Nisja dhe Mberritja nuk mund te jene te njejta.");
	            return;
	      }

	      //kontroll per daten qe nuk mund te jete bosh
	      if (DataZgjedhje.getValue() == null) {
	            shfaqAlert(AlertType.WARNING, "Gabim!", "Ju lutemi zgjidhni nje date.");
	            return;
	      }
	        
	        //lidhemi me databezen
            Connection conn = Databaze.methodConnect();
            
            //kontroll nqs treni me kete numer ekziston /duhet te jene unike
            PreparedStatement psCheck = conn.prepareStatement("SELECT * FROM admindb WHERE Nr = ?");
            psCheck.setInt(1, intTreni);//vendosim numrin e trenit ne query
            ResultSet rs = psCheck.executeQuery();//ekzekuton query-n e kontrollit
            
            //kontroll nqs ekziston nje id treni tashme ne databaze
            if (rs.next()) {
                shfaqAlert(AlertType.WARNING, "Gabim!", "Ekziston tashme nje tren me kete numer identifikmi. Ju lutemi perdorni nje numer tjeter.");
                return;
            }
            
            //vazhdojme me insertimin e te dhenave nqs nuk ka Nr duplikat dhe gjithcka eshte ok 
            //ekzekuton query-n INSERT
            PreparedStatement ps = conn.prepareStatement("INSERT INTO admindb (Nr, Kapaciteti, Emri, Nisja, Mberritja, Data, Ora, Cmimi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            //vendos vlerat e marra nga fxml ne parametrat e query-t INSERT
            ps.setInt(1, intTreni);
            ps.setInt(2, intKapaciteti);
            ps.setString(3, ComboEmriTreni.getValue());
            ps.setString(4, ComboNisja.getValue());
            ps.setString(5, ComboMberritja.getValue());
            //konvertim LocalDate nga DatePicker ne java.sql.Date per databazen
            ps.setDate(6, java.sql.Date.valueOf(DataZgjedhje.getValue()));
            ps.setString(7, ora);//vendos oren si String
            ps.setDouble(8, dobCmimi);//vendos cmimin si Double
            ps.executeUpdate();//ekzekuton komanden INSERT
            
            //rifreskojm tabelen per te shfaqur trenin e ri 
            populate();
            //pastrojme fushat
            pastroFields();
        } catch (NumberFormatException e) { //nqs nuk i kap te gjitha validimet arraylist
            shfaqAlert(Alert.AlertType.WARNING, "Gabim", "Numri i trenit dhe kapaciteti duhet te jene numra.");
        } catch (SQLException e) { //kap dhe shfaq gabimet 
            e.printStackTrace();
        }
    }

    //metoda per te perditesuar trenin
    public void updateTreni() {
    	 try {
    		  //merr obj Treni qe eshte zgjedhur nga tabela
              Treni treni_i_zgjedhur = tblTreni.getSelectionModel().getSelectedItem();
             //kontroll nese perdoruesi nuk ka zgjedhuar asnje tren per ndryshim
             if (treni_i_zgjedhur == null) {
               shfaqAlert(Alert.AlertType.WARNING, "Gabim", "Zgjidh nje tren per te perditesuar nga tabela trenat.");
               return;
             }
        
             //merr vlerat nga fushat e tekstit 
        	String nrTreni = txtNr.getText();
       	    String kapaciteti = txtKapaciteti.getText();
       	    String cmimi = txtCmimi.getText();
       	    String ora = txtOra.getText();
       	 
            //kontroll nqs te gjitha fushat jane bosh
   		    if((nrTreni == null || nrTreni.length() == 0) && (kapaciteti == null || kapaciteti.length() == 0) && (cmimi == null || cmimi.length() == 0) && (ora == null || ora.length() == 0)) {
   			   shfaqAlert(AlertType.WARNING, "Gabim!", "Ju keni lene te gjitha fushat bosh.Ju lutemi plotesojini ato.");
   			   return; //per te ndalur veprimet 
   		    }

   		
   		    //kontroll per numrin e trenit
   		    if (nrTreni == null || nrTreni.length() == 0) {
   			  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nr. i trenit nuk mund te lihet bosh.Ju lutemi plotesojeni.");
   			  return; //per te ndalur veprimet 
   		   }

   		   //kontrollo per karaktere speciale dhe shkronja
   		   for (int i = 0; i < nrTreni.length(); i++) {
   			   char c = nrTreni.charAt(i);
                
   			   //kontroll per shkronja
   			   if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
   				   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nr. i trenit nuk mund te kete shkronja.");
   	               return;
   	           }

   			 //kontrollon per karaktere speciale
   			   for (char karakter : karakter_special) {     
   				  if (c == karakter) {
   					  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nr. i trenit nuk mund te kete karaktere speciale.");
   					  return; //per te ndalur veprimet 
   				  }
   			   }
   		   }
   		
   		  
   		  //kontroll per cmimin
   		  if (cmimi == null || cmimi.length() == 0) {
   			  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Cmimi nuk mund te lihet bosh.Ju lutemi plotesojeni.");
   			  return; //per te ndalur veprimet 
   		  }

   		  //kontrollo per karaktere speciale dhe shkronja
   		  for (int i = 0; i < cmimi.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
   			  char c = cmimi.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

   		     //kontroll per shkronja
   			  if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
   				   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Cmimi nuk mund te kete shkronja.");
   	               return;
   	          }
                
   		     //kontrollon per karaktere speciale
   			 for (char karakter : karakter_special) {
   				if (c == karakter) {
   					 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Cmimi nuk mund te kete karaktere speciale.");
   					 return; //per te ndalur veprimet 
   				}
   			 }
   		  }
   		  
   		  //kontroll per kapacitetin
   		  if (kapaciteti == null || kapaciteti.length() == 0) {
   			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Kapaciteti nuk mund te lihet bosh.Ju lutemi plotesojeni.");
   			 return; //per te ndalur veprimet 
   		  }

   		  //kontrollo per karaktere speciale dhe shkronja
   		  for (int i = 0; i < kapaciteti.length(); i++) {
   			  char c = kapaciteti.charAt(i);
                 
   			  //kontroll per shkronja
   			  if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
   				   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Kapaciteti nuk mund te kete shkronja.");
   	               return;
   	           }

   			//kontrollon per karaktere speciale
   			  for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
   				 if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
   					 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Kapaciteti nuk mund te kete karaktere speciale.");
   					 return; //per te ndalur veprimet 
   				 }
   			  }
   		  }
   		  
   		  
   		  //kontroll per Oren
   		  if (ora == null || ora.length() == 0 || ora.length() > 10) {
   			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Ora nuk mund te lihet bosh ose te jete me e madhe se 10 karkatere.Ju lutemi plotesojeni.");
   			 return; //per te ndalur veprimet 
   		  }

   		  //kontrollo per karaktere speciale dhe shkronja
   		  for (int i = 0; i < ora.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
   			  char c = ora.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

   			 //kontroll per shkronja
   			  if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
   				   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Ora nuk mund te kete shkronja.");
   	               return;
   	           }
                
   			 //kontrollon per karaktere speciale
   			  for (char karakter : karakter_special) {
   				 if (c == karakter) {
   					 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Ora nuk mund te kete karaktere speciale.");
   					 return; //per te ndalur veprimet 
   				 }
   			  }
   		  }
   		  
   		  //i kthejme ne int vlerat 
   		  int intTreni = Integer.parseInt(nrTreni);
   		  //kontroll per numrin id te trenit
   		  if(intTreni <= 0) {
   			  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nr. i trenit mund te kete vetem numra pozitive.");
   			  return;
   		  }
   		  
   		  
   		  int intKapaciteti = Integer.parseInt(kapaciteti);
   		  //kontroll per kapacitetin e trnit qe dueht te jete nr pozitiv
   		  if(intKapaciteti < 0) {
   			  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Kapaciteti mund te kete vetem numra pozitive.");
   			  return;
   		  }
             
   		  Double dobCmimi = Double.parseDouble(cmimi);
   		  //kontroll per cmimin e biletes qe dueht te jete pozitiv
   		  if(dobCmimi <= 0) {
   			  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Cmimi mund te kete vetem numra pozitive.");
   			  return;
   		  }
   		 
   		  //kontroll per combobox-et Nisje dhe mberritje 
   		  if (ComboNisja.getValue() == null || ComboNisja.getValue().isEmpty()) {
   	            shfaqAlert(AlertType.WARNING, "Gabim!", "Ju lutemi zgjidhni nje vend per Nisjen.");
   	            return;
   	      }
   		  
   	      if (ComboMberritja.getValue() == null || ComboMberritja.getValue().isEmpty()) {
   	            shfaqAlert(AlertType.WARNING, "Gabim!", "Ju lutemi zgjidhni nje vend per Mberritjen.");
   	            return;
   	      }
   	       
   	      //nisja nuk mund te jete edhe mberritja
   	      if (ComboNisja.getValue().equals(ComboMberritja.getValue())) {
   	            shfaqAlert(AlertType.WARNING, "Gabim!", "Nisja dhe Mberritja nuk mund te jene te njejta.");
   	            return;
   	      }

   	      //kontroll per daten qe nuk mund te jete bosh
   	      if (DataZgjedhje.getValue() == null) {
   	            shfaqAlert(AlertType.WARNING, "Gabim!", "Ju lutemi zgjidhni nje date.");
   	            return;
   	      }
        	
   	   
   	        //lidhemi me databezen
   	        Connection conn = Databaze.methodConnect();
   	        PreparedStatement ps = conn.prepareStatement("UPDATE admindb SET Kapaciteti=?, Emri=?, Nisja=?, Mberritja=?, Data=?, Ora=?, Cmimi=? WHERE Nr=?");
   	        
   	        //nqs pepiqemi te vendosim nje numer tjter e vend te atij ekzistues 
   	        if(intTreni != treni_i_zgjedhur.getNrTreni()) {
   	        	shfaqAlert(AlertType.WARNING, "Gabim!", "Ju nuk mund te ndryshoni nr. e trenit gjate perditesimit sepse eshte unik per cdo tren.");
   	            return;
   	        }

            // Vendos vlerat e reja në parametrat e query-t UPDATE.
            ps.setInt(1, intKapaciteti); // Kapaciteti i ri.
            ps.setString(2, ComboEmriTreni.getValue()); // Emri i ri.
            ps.setString(3, ComboNisja.getValue());     // Nisja e re.
            ps.setString(4, ComboMberritja.getValue()); // Mberritja e re.
            ps.setDate(5, java.sql.Date.valueOf(DataZgjedhje.getValue())); // Data e re.
            ps.setString(6, ora); // Ora e re.
            ps.setDouble(7, dobCmimi); // Cmimi i ri.
            // Vendos Nr e trenit origjinal të zgjedhur në kushtin WHERE për të identifikuar rreshtin që do përditësohet.
            ps.setInt(8, treni_i_zgjedhur.getNrTreni());
            ps.executeUpdate();
   	        
   	        populate();
   	        pastroFields();
        } catch (NumberFormatException e) { //nqs nuk i kap te gjitha validimet
            shfaqAlert(Alert.AlertType.WARNING, "Gabim", "Numri i trenit dhe kapaciteti duhet te jene numra.");    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //metoda per te hequr nje tren
    public void removeTreni() {
    	try {
    		   String nrTreni = txtNr.getText();
          	   String kapaciteti = txtKapaciteti.getText();
          	   String cmimi = txtCmimi.getText();
          	  
    		   int intTreni = Integer.parseInt(nrTreni);
    		   int intKapaciteti = Integer.parseInt(kapaciteti);
    		   double dobcmimi = Double.parseDouble(cmimi);
    		   
    		  //merr obj Treni qe eshte zgjedhur nga tabela
              Treni treni_i_zgjedhur = tblTreni.getSelectionModel().getSelectedItem();
              //kontroll neser perdoruesi nuk ka zgjedhur asnje tren per fshirje
              if (treni_i_zgjedhur == null) {
                shfaqAlert(Alert.AlertType.WARNING, "Gabim", "Zgjidh nje tren per ta fshire nga tabela trenat.");
                return;
              }
        
            //lidhemi me databazen 
            Connection conn = Databaze.methodConnect();
            //pergatit komanden DELETE,fshin rreshtin ku Nr perputhet me Nr e trenit te zgjedhur
            PreparedStatement ps = conn.prepareStatement("DELETE FROM admindb WHERE Nr=?");
            
            //nqs pepiqemi te vendosim nje numer tjter e vend te atij ekzistues 
   	        if(intTreni != treni_i_zgjedhur.getNrTreni()) {
   	        	shfaqAlert(AlertType.WARNING, "Gabim!", "Ju nuk mund te ndryshoni nr. e trenit gjate fshirjes sepse eshte unik per cdo tren.");
   	            return;
   	        }
   	        
   	        //nqs pepiqemi te vendosim nje numer tjter per kapacitetin 
   	        if(intKapaciteti != treni_i_zgjedhur.getKapaciteti()) {
   	        	shfaqAlert(AlertType.WARNING, "Gabim!", "Ju nuk mund te ndryshoni kapacitetin gjate fshirjes.");
   	            return;
   	        }
   	        
   	        //nqs pepiqemi te vendosim nje numer tjter per kapacitetin 
   	        if(dobcmimi != treni_i_zgjedhur.getCmimi()) {
   	        	shfaqAlert(AlertType.WARNING, "Gabim!", "Ju nuk mund te ndryshoni cmimin e biletes gjate fshirjes.");
   	            return;
   	        }
            
   	       
            //ne baze te numrit te trenit bejme fshirjen 
            ps.setInt(1, treni_i_zgjedhur.getNrTreni());
            ps.executeUpdate(); //ekzekutojm komanden DELETE
            //rifreskojm tabelen 
            populate();
            //pastrojm fushat
            pastroFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //metode ndihemse per te marre trenin nga zgjedhjet
    private void Treni_i_zgjedhuar() {
    	//marrim modelin per te populluar fieldset-at,datapicker dhe combobox-et
        Treni treni_i_zgjedhur = tblTreni.getSelectionModel().getSelectedItem();
        //marri zgjedhjen -->nqs kemi zgjedhuar na mbush fieldsat
        if (treni_i_zgjedhur != null) {
            txtNr.setText(String.valueOf(treni_i_zgjedhur.getNrTreni()));
            txtKapaciteti.setText(String.valueOf(treni_i_zgjedhur.getKapaciteti()));
            ComboEmriTreni.setValue(treni_i_zgjedhur.getEmriTreni());
            ComboNisja.setValue(treni_i_zgjedhur.getNisja());
            ComboMberritja.setValue(treni_i_zgjedhur.getMberritja());
            DataZgjedhje.setValue(LocalDate.parse(treni_i_zgjedhur.getData()));
            
            String ora = treni_i_zgjedhur.getOra();
            //per oren qe te mos jete me e gjate se 5 karkatere  formatim HH:MM
            if (ora != null && ora.length() > 5) { //nqs don edhe sekondat beje 8 jo 5
                ora = ora.substring(0, 5);
            }
            //e vendosm oren 
            txtOra.setText(ora);
            //e vendosim cmimin ne textfield
            txtCmimi.setText(String.valueOf(treni_i_zgjedhur.getCmimi()));
        }
    }

    //metoda per te shire te gjitha te dhenat
    public void clearTrenat() {
    	 //pastron listen ObservableList
        trenatList.clear();
        //e shfaqim perditesimin pra tabela bosh 
        tblTreni.setItems(trenatList);

        try {
        	//lidhemi me databazen
            Connection conn = Databaze.methodConnect();
            //Pergatit komanden DELETE 
            PreparedStatement ps = conn.prepareStatement("DELETE FROM admindb");
            ps.executeUpdate();//dhe ekzekutoj komanden DELETE
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //pastrojm fushat
        pastroFields();
    }

    //metode ndihmese per te pastruar text,checkboox,datapicker (pra fieldsat)
    private void pastroFields() {
        txtNr.clear();
        txtKapaciteti.clear();
        ComboEmriTreni.getSelectionModel().clearSelection();
        ComboNisja.getSelectionModel().clearSelection();
        ComboMberritja.getSelectionModel().clearSelection();
        DataZgjedhje.getEditor().clear();
        txtOra.clear();
        txtCmimi.clear();
    }

    //metode qe do te ekzekutohet direkt nga kontrollLogIn
    //metode per te kontrolluar per kapacitetin e trenave -->edhe kjo e merr KontrollLogIn sepse dua te shfaq alertin sapo 
    public void kontroll_kapacitet() {                       //te futemi ne dritaren admin
    	//konntrollojme listen e treanve me nje for-each  ne trenaList
        for (Treni treni : trenatList) {
            if (treni.getKapaciteti() <= 0) { //nqs kapaciteti i trenit eshte 0 ose me i vogel 
                shfaqAlert(Alert.AlertType.WARNING, "Njoftim",
                    "Treni me emrin " + treni.getEmriTreni() + " nuk ka me vende per pasagjere.\n" + "Ndrysho tren ose shto nje te ri.");
            }   
        }
     }
  
}

