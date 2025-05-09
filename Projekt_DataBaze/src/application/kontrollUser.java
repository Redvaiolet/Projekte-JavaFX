package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.AudioClip;

public class kontrollUser {
	 //array per te mbajtu karakteret speciale te palejuara qe mund te vendos perdoruesi
     private static char[] karakter_special = { '!', '@', '#', '$', '%', '&', '*', '(', ')','-' ,'_', '=', '[',']', '{', '}', ';', '"', '<', '>', '?', '\\', '|', '~', '§' };

	//---------------------------------------------Tabela Trenat--------------------------------------------------
     //lidh variablin tblTreniUser me elementin TableView ne skedarin e tabels se databazes admindb
     @FXML private TableView<Treni> tblTreniUser;
	 @FXML private TableColumn<Treni, Integer> colNr;
	 @FXML private TableColumn<Treni, String> colEmri, colNisja, colMberritja, colData, colOra;
	 @FXML private TableColumn<Treni, Double> colCmimi;

	 //ObservableList ku mban obj e klases Treni qe do te shfaqen ne tabelen tblTreniUser
	 //dhe  FXCollections.observableArrayList() krijon nje listë qe TableView mund ta "vezhgoje" per ndryshime
    private ObservableList<Treni> trenatList = FXCollections.observableArrayList();
    
    //metode ndihmese per te shfaqur alertet 
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

    //metoda initialize thirret automatikisht pasi skedari FXML eshte ngarkuar 1 here!!
    //ngarkuar te dhenat fillestare
    public void initialize() {
    	//thirret metoda per te lidhur kolonat e tabeles se trenave me obj e klasës Treni
        //per tabelen Treni
        lidhjeTabele_Trenat();

        populateTable();//per te mbushur tabelen e trenit me te dhenat e tabeles se databazes admindb
        
        //thirret metoda per te lidhur kolonat e tabeles se pasagjereve me obj e klases User
        //per tabelen Pasegjeret
        lidhjeTabele_Pasegjeret();
        
        
        mbushTblPasegjeretNgaDB(); //per te mbushur tabelen e pasegjerit me te dhenat e databazes, tabeles user
        
        //me action hendel me shprehje lambda
        shtoPasegjer.setOnAction(event -> shtoPasegjer());
        perditesoPasegjer.setOnAction(event -> perditesoPasegjer());
        fshijPasegjer.setOnAction(event -> fshijPasegjer());
        
       //per kapjen e zgjedhjes ne tabeln pasegjeret
        tblPasegjeret.setOnMouseClicked(event -> merrperzgjedhjen());
        
        //per tu kthyer ne Home Page
        kthehuHome.setOnAction(event -> {
         Main.stage1.show();
         Main.stage4.hide();
       });
        
        //per te hapur dritaren Chart
        Chart.setOnAction(event -> {
            Main.k5.perditesoChart(); //<- rifreskon te dhenat nga databaza user
            Main.stage5.show();     //hap dritaren e Chart
            Main.stage4.hide();     //mbyll dritaren aktuale
        });
    }

    //metode ndihmese per te lidhur tabeln trenat me kolonat perkastese
    private void lidhjeTabele_Trenat() {
    	colNr.setCellValueFactory(new PropertyValueFactory<>("nrTreni"));
        colEmri.setCellValueFactory(new PropertyValueFactory<>("emriTreni"));
        colNisja.setCellValueFactory(new PropertyValueFactory<>("nisja"));
        colMberritja.setCellValueFactory(new PropertyValueFactory<>("mberritja"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colOra.setCellValueFactory(new PropertyValueFactory<>("ora"));
        colCmimi.setCellValueFactory(new PropertyValueFactory<>("cmimi"));
    }
    
    //e njeta gje edhe per tabeln pasegjert
    private void lidhjeTabele_Pasegjeret() {
    	columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnEmriPasegjer.setCellValueFactory(new PropertyValueFactory<>("emerPasegjeri"));
        columnMbiemriPasegjer.setCellValueFactory(new PropertyValueFactory<>("mbiemrPasegjeri"));
        columnNrTreni.setCellValueFactory(new PropertyValueFactory<>("nrUserTreni"));
        columnEmerTreni.setCellValueFactory(new PropertyValueFactory<>("emriUserTreni"));
        columnNisja.setCellValueFactory(new PropertyValueFactory<>("nisjaUser"));
        columnMberritja.setCellValueFactory(new PropertyValueFactory<>("mberritjaUser"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("dataUser"));
        columnOra.setCellValueFactory(new PropertyValueFactory<>("oraUser"));
        columnCmimi.setCellValueFactory(new PropertyValueFactory<>("cmimiUser"));
    }
    
    //meode ndihmese per te mbushur tabeln trenat me te dhenat perkatese te databazes admindb
    private void populateTable() {
    	//e pastrojme ne fillimisht listen e trenave 
        trenatList.clear();
        try {
        	//lidhemi me databazen
            Connection conn = Databaze.methodConnect();
            //krijijme nje obj Statement per te ekzekutuar nje komande SQL 
            Statement stmt = conn.createStatement();
            
            //ekzekutojme nje sql statement per te marre te dhenat e te gjitha kolonave 
            //ne databezen admindb
            ResultSet rs = stmt.executeQuery("SELECT * FROM admindb");

            //iterim neper çdo rresht te databazes me metoden next()
            while (rs.next()) {
            	 //per çdo rresht,krijon nje obj te ri Treni duke marrë vlerat nga kolonat perkatëse te rs 
                trenatList.add(new Treni(
                    rs.getInt("Nr"),
                    rs.getInt("Kapaciteti"),
                    rs.getString("Emri"),
                    rs.getString("Nisja"),
                    rs.getString("Mberritja"),
                    rs.getString("Data"),
                    rs.getString("Ora"),
                    rs.getDouble("Cmimi")
                ));
            }

            
            //vendosim listen e populluar trenatList per tabelen tblTreniUser
            //tabela do te shfaqe automatikisht te dhenat nga kjo liste me metoden setItems()
            tblTreniUser.setItems(trenatList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //rifreskojme tabeln nga jashte per cdo ndryshim qe mund te beje admin -->kontroll1
    public void refreshtabelaUser() {
        populateTable();
    }

    //---------------------------------Tabela Pasegjeret----------------------------------------------------------------------------------------------------------------
  //lidh variablin tblPasegjeret me elementin TableView ne skedarin e tabels se databazes user
    @FXML private TableView<User> tblPasegjeret;
    @FXML private TableColumn<User, Integer> columnId, columnNrTreni;
    @FXML private TableColumn<User, String> columnEmriPasegjer, columnMbiemriPasegjer,columnEmerTreni, columnNisja, columnMberritja, columnData, columnOra;
    @FXML private TableColumn<User, Double> columnCmimi;

    @FXML private TextField txtemri, txtmbiemri, id;
    @FXML private Button shtoPasegjer, perditesoPasegjer, fshijPasegjer, kthehuHome;
    @FXML private Button Chart;

    //krijojme nje ObservableList te quajtur userList per te mbajtur obj e klases User qe do t shfaqen ne tabelën tblPasegjeret
    private ObservableList<User> userList = FXCollections.observableArrayList();
    
    
    //metode ndihmese per te mbushur tabeln e Pasegjereve me te dheant e databazes,tabels user
    private void mbushTblPasegjeretNgaDB() {
    	//si fillimi pastrojme listen userList
        userList.clear();
        try {
             
        	 //krijime nje lidhje me databezen 
        	 Connection conn = Databaze.methodConnect();
        	 //krijijme nje obj Statement per te ekzekutuar nje komande SQL
             Statement stmt = conn.createStatement();
             
             //ekzekutojm nje query SQL per te marre te gjitha kolonat nga te gjitha rreshtat e tabeles user
             //me metoden executeQuery()
             ResultSet rs = stmt.executeQuery("SELECT * FROM user");
        	
            //iterojme rezultatet e query-t rresht pas rreshti me metoden next() 
            while (rs.next()) {
                userList.add(new User(
                    rs.getInt("id"),
                    rs.getString("Emri"),
                    rs.getString("Mbiemri"),
                    rs.getInt("NrTren"),
                    rs.getString("EmriTreni"),
                    rs.getString("Nisja"),
                    rs.getString("Mberritja"),
                    rs.getString("Data"),
                    rs.getString("Ora"),
                    rs.getDouble("Cmimi")
                ));
            }

            //vendosim listen e populluar userList tek tabela tblPasegjeret
            tblPasegjeret.setItems(userList);
            tblTreniUser.getSelectionModel().clearSelection(); // pastron përzgjedhjen nga tabela "tblTreniUser"

        } catch (SQLException e) {//kap dhe shfaq nqs ndoll ndonje gabim
            e.printStackTrace();
        }
    }

    //metoda per te shtuar nje pasegjer 
    public void shtoPasegjer() {
       try {
    	   //marrim textfields/obj e trenit qe eshte zgjedhuar aktualisht nga perdoruesi
            Treni treniZgjedhur = tblTreniUser.getSelectionModel().getSelectedItem();
            String emri = txtemri.getText();
            String mbiemri = txtmbiemri.getText();
            String idStr = id.getText();

          
          //kontroll nqs te gjitha fushat jane bosh
  		  if((treniZgjedhur == null) && (emri == null || emri.length() == 0) && (mbiemri == null || mbiemri.length() == 0) && (idStr == null || idStr.length() == 0)) {
  			 shfaqAlert(AlertType.WARNING, "Gabim!", "Ju lutem plotesoni ID-ne, emrin, mbiemrin dhe zgjidhni nje tren per te shtuar nje pasegjer.");
  			 return; //per te ndalur veprimet 
  		  } 
        
  
          //kontroll nese perdoruesi nuk ka zgjedhuar asnje tren per shtim
           if (treniZgjedhur == null) {
               shfaqAlert(Alert.AlertType.WARNING, "Gabim", "Zgjidh nje tren per te shtuar ne tabelen e pasegjereve per te kryer shtimin.");
               return;
           }
  		  
           
  		  //kontroll per emrin e pasegjerit
		  if (emri == null || emri.length() == 0) {
			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			 return; //per te ndalur veprimet 
		  }
  		  
  		  
		 //kontrollo per karaktere speciale dhe numra
		 for (int i = 0; i < emri.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			   char c = emri.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

				//kontrollon per numra
				if (c >= '0' && c <= '9') {
					shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te kete numra.");
					return;
				}

				//kontrollon per karaktere speciale
				for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
						shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te kete karaktere speciale.");
						return; //per te ndalur veprimet 
					}
				}
			}
		 
		 //kontroll per mbiemrin e pasegjerit
		  if (mbiemri == null || mbiemri.length() == 0) {
			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Mbiemri nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			 return; //per te ndalur veprimet 
		  }
 		  
 		  
		 //kontrollo per karaktere speciale dhe numra
		 for (int i = 0; i < mbiemri.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			   char c = mbiemri.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

				//kontrollon per numra
				if (c >= '0' && c <= '9') {
					shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Mbiemri nuk mund te kete numra.");
					return;
				}

				//kontrollon per karaktere speciale
				for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
						shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Mbiemri nuk mund te kete karaktere speciale.");
						return; //per te ndalur veprimet 
					}
				}
			}
		  
	      //kontroll per id
		  if (idStr == null || idStr.length() == 0) {
			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha ID nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			 return; //per te ndalur veprimet 
		  }

		  //kontrollo per karaktere speciale dhe shkronja
		  for (int i = 0; i < idStr.length(); i++) {
			  char c = idStr.charAt(i);
             
			  //kontroll per shkronja
			  if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha ID nuk mund te kete shkronja.");
	               return;
	           }

			//kontrollon per karaktere speciale
			  for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
				 if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
					 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha ID nuk mund te kete karaktere speciale.");
					 return; //per te ndalur veprimet 
				 }
			  }
		  }
		    
		    //e kthejme ne int id
            int idVlere = Integer.parseInt(idStr);
            //kontroll
            if(idVlere <= 0) {
  			  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha ID mund te kete vetem numra pozitive.");
  			  return;
  		    }
            
            //lidhemi me databazen
            Connection conn = Databaze.methodConnect();
            
            //kontroll nqs id eshte unike(nuk ekziston) per cdo pasegjer 
            PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
            stmt1.setInt(1, idVlere); //pozicioni i id ne databaze
            ResultSet rset = stmt1.executeQuery();//ekzekutojme komanden sql
            
            //nqs ekziston nje id tashme nxjerr nje alert (njesoj sikur if(idStr == id.getText()) )
            if (rset.next()) {
                shfaqAlert(AlertType.WARNING, "Gabim!", "Ekziston tashme nje pasegjer me kete numer identifikmi. Ju lutemi perdorni nje numer tjeter.");
                return;
            }

            //kontroll per kapacitetin 
            PreparedStatement stmt2 = conn.prepareStatement("SELECT Kapaciteti FROM admindb WHERE Nr = ?");
            stmt2.setInt(1, treniZgjedhur.getNrTreni());//marrim numrin e trenit nga trni i zgjedhur
            ResultSet rs = stmt2.executeQuery();//ekzekutojme sql

            //iterojme rresht per rresht ne tabelen e sql dhe e gjejme trenin
            //ne tabeln admindb
            if (rs.next()) {
                int kapaciteti = rs.getInt("Kapaciteti"); //marrim vleren e kapacitetit

                //nqs kapaciteti ka arritur ne 0
                if (kapaciteti <= 0) {
                    shfaqAlert(Alert.AlertType.WARNING, "Gabim!", "Nuk ka me vende ne kete tren. Ju lutem zgjidhni nje tjeter.");
                    return;
                }

                //pergatisim komanden sql per ekzekutim ku do te shtojme pasagjerin e ri ne tabelen pasagjeret dhe databazen user
                PreparedStatement ps = conn.prepareStatement("INSERT INTO user (id, Emri, Mbiemri, NrTren, EmriTreni, Nisja, Mberritja, Data, Ora, Cmimi) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                ps.setInt(1, idVlere);
                ps.setString(2, emri);
                ps.setString(3, mbiemri);
                ps.setInt(4, treniZgjedhur.getNrTreni());
                ps.setString(5, treniZgjedhur.getEmriTreni());
                ps.setString(6, treniZgjedhur.getNisja());
                ps.setString(7, treniZgjedhur.getMberritja());
                ps.setString(8, treniZgjedhur.getData());
                ps.setString(9, treniZgjedhur.getOra());
                ps.setDouble(10, treniZgjedhur.getCmimi());

                //ekzekutojme komanden sql
                ps.executeUpdate();

                //perditesojme tabeln e admindb me konden sql --> -1 sepse eshte shtuar nje pasagjer ne tren
                PreparedStatement perditesoStmt = conn.prepareStatement("UPDATE admindb SET Kapaciteti = Kapaciteti - 1 WHERE Nr = ?");
                perditesoStmt.setInt(1, treniZgjedhur.getNrTreni());
                //ekzekutojme statement
                perditesoStmt.executeUpdate();

                shfaqAlert(Alert.AlertType.INFORMATION, "Sukses", "Pasagjeri u shtua me sukses!");

                //pastrojm textfields 
                txtemri.clear();
                txtmbiemri.clear();
                id.clear();
                //pastrojm zgjedhjen
                tblTreniUser.getSelectionModel().clearSelection(); 

                //mbushim te dheant ne tabeln e databazes user
                mbushTblPasegjeretNgaDB();
            }

        } catch (NumberFormatException e) {//nqs validimet nuk kapin te gjitha simbolet 
            shfaqAlert(Alert.AlertType.ERROR, "Gabim", "ID duhet te jete numer.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //metode per perditesimin e te dhenave 
    public void perditesoPasegjer() {
    	try {
    		
    		 //kontrollojme nqs perdoruesi ka zgjedhur nje pasegjer per te bere perditesimin e tij e tij 
    		 User pasegjeri_i_zgjedhuar = tblPasegjeret.getSelectionModel().getSelectedItem();
    		
    		 if(pasegjeri_i_zgjedhuar == null) {
    			 shfaqAlert(AlertType.WARNING, "Gabim!", "Ju lutemi zgjidhni ne fillim nje pasegjer nga tabela pasegjeret \n per te kryer perditesimin.");
    		     return;
    		 }
    		
              //marrim textfields
              String emri = txtemri.getText();
              String mbiemri = txtmbiemri.getText();
              String idStr = id.getText();
              
              
            //kontroll nqs te gjitha fushat jane bosh
      		  if((emri == null || emri.length() == 0) && (mbiemri == null || mbiemri.length() == 0) && (idStr == null || idStr.length() == 0)) {
      			 shfaqAlert(AlertType.WARNING, "Gabim!", "Ju lutem zgjidhni nje pasegjer per te kryer ndryshimet pa lene fusht bosh.");
      			 return; //per te ndalur veprimet 
      		  } 
            
      		  //kontroll per emrin e pasegjerit
    		  if (emri == null || emri.length() == 0) {
    			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te lihet bosh.Ju lutemi plotesojeni.");
    			 return; //per te ndalur veprimet 
    		  }
      		  
      		  
    		 //kontrollo per karaktere speciale dhe numra
    		 for (int i = 0; i < emri.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
    			   char c = emri.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

    				//kontrollon per numra
    				if (c >= '0' && c <= '9') {
    					shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te kete numra.");
    					return;
    				}

    				//kontrollon per karaktere speciale
    				for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
    					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
    						shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te kete karaktere speciale.");
    						return; //per te ndalur veprimet 
    					}
    				}
    			}
    		 
    		 //kontroll per mbiemrin e pasegjerit
    		  if (mbiemri == null || mbiemri.length() == 0) {
    			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Mbiemri nuk mund te lihet bosh.Ju lutemi plotesojeni.");
    			 return; //per te ndalur veprimet 
    		  }
     		  
     		  
    		 //kontrollo per karaktere speciale dhe numra
    		 for (int i = 0; i < mbiemri.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
    			   char c = mbiemri.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

    				//kontrollon per numra
    				if (c >= '0' && c <= '9') {
    					shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Mbiemri nuk mund te kete numra.");
    					return;
    				}

    				//kontrollon per karaktere speciale
    				for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
    					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
    						shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Mbiemri nuk mund te kete karaktere speciale.");
    						return; //per te ndalur veprimet 
    					}
    				}
    			}
    		  
    	      //kontroll per id
    		  if (idStr == null || idStr.length() == 0) {
    			 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha ID nuk mund te lihet bosh.Ju lutemi plotesojeni.");
    			 return; //per te ndalur veprimet 
    		  }

    		  //kontrollo per karaktere speciale dhe shkronja
    		  for (int i = 0; i < idStr.length(); i++) {
    			  char c = idStr.charAt(i);
                 
    			  //kontroll per shkronja
    			  if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
    				   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha ID nuk mund te kete shkronja.");
    	               return;
    	           }

    			//kontrollon per karaktere speciale
    			  for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
    				 if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
    					 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha ID nuk mund te kete karaktere speciale.");
    					 return; //per te ndalur veprimet 
    				 }
    			  }
    		  }
    		    
    		    //e kthejme ne int id
                int idVlere = Integer.parseInt(idStr);
                //kontroll
                if(idVlere <= 0) {
      			  shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha ID mund te kete vetem numra pozitive.");
      			  return;
      		    }
                
                
            //lidhemi me databazen 
            Connection conn = Databaze.methodConnect();

            PreparedStatement psmerrUser = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
            psmerrUser.setInt(1, idVlere);
            ResultSet rs = psmerrUser.executeQuery();

                // e njeta gji si rs.next() == null -->nuk ekziston e kam perdorur si validim 
            	if (!rs.next()) {
            	    shfaqAlert(AlertType.WARNING, "Gabim!", "Nuk lejohet ndryshimi i ID-se sepse ajo eshte unike.");
            	    return;
            	}
            
            //do marrim vlerat e tables treni aktuale qe do ti karhasojme me ato te rejat 
            int nrTreniAktual = rs.getInt("NrTren");
            String emriTreni = rs.getString("EmriTreni");
            String nisja = rs.getString("Nisja");
            String mberritja = rs.getString("Mberritja");
            String data = rs.getString("Data");
            String ora = rs.getString("Ora");
            double cmimi = rs.getDouble("Cmimi");

            //marrim te thenat e treni te ri / te zgjedhuar 
            Treni treniZgjedhur = tblTreniUser.getSelectionModel().getSelectedItem();
            
      
            int nrTreniIRi;

            //Rasti 1:Nqs kemi ndryshuar trnin 
            if (treniZgjedhur != null) {
            	//marrim numri e trenit te ri 
                nrTreniIRi = treniZgjedhur.getNrTreni();

                //dhe meqs te dheant e trni te ri kane ndryshar 
                if (nrTreniAktual != nrTreniIRi) {
                	//prep stmt qe do rrisimi me 1 kapacitenit e trenit te trenti aktual
                    PreparedStatement psRritVjeter = conn.prepareStatement("UPDATE admindb SET Kapaciteti = Kapaciteti + 1 WHERE Nr = ?");
                   //tregojm pozicionin 
                    psRritVjeter.setInt(1, nrTreniAktual);
                    //ekzekutojme 
                    psRritVjeter.executeUpdate();

                    //prep stmt per te ulur me 1 kapacitetin e trenit te ri te zgjedhur 
                    PreparedStatement psZbritIRi = conn.prepareStatement("UPDATE admindb SET Kapaciteti = Kapaciteti - 1 WHERE Nr = ?");
                    //tregojm pozicionin 
                    psZbritIRi.setInt(1, nrTreniIRi);
                    //ekzekutojme
                    psZbritIRi.executeUpdate();
                }
                 
                //marrim te dheant e reja pqe trenint e ri te zgjedhuar
                emriTreni = treniZgjedhur.getEmriTreni();
                nisja = treniZgjedhur.getNisja();
                mberritja = treniZgjedhur.getMberritja();
                data = treniZgjedhur.getData();
                ora = treniZgjedhur.getOra();
                cmimi = treniZgjedhur.getCmimi();
            } else {
            	//Rasti 2:nuk ndryshojme tren por vetem te dheant e pasegjerit (emer,mbiemr)
                nrTreniIRi = nrTreniAktual;
            }

            //ndertojm ne prep stmt per ekzekutim 
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET Emri=?, Mbiemri=?, NrTren=?, EmriTreni=?, Nisja=?, Mberritja=?, Data=?, Ora=?, Cmimi=? WHERE id=?");

            //mbushim vendet ne databaze
            ps.setString(1, emri);
            ps.setString(2, mbiemri);
            ps.setInt(3, nrTreniIRi);
            ps.setString(4, emriTreni);
            ps.setString(5, nisja);
            ps.setString(6, mberritja);
            ps.setString(7, data);
            ps.setString(8, ora);
            ps.setDouble(9, cmimi);
            ps.setInt(10, idVlere);

            //marim nje int per pozcionin e rrshtit te ndryshuar
            int kolona_te_ndryshuara = ps.executeUpdate(); //kjo gjithmone kthen nje int 
            
            //tek ai pozicion (qe marrim id e masagjerit qe eshte vendodhja)
            if (kolona_te_ndryshuara > 0) {
                shfaqAlert(Alert.AlertType.INFORMATION, "Sukses", "Pasagjeri u perditesua me sukses!");
                //peditesojme databazen
                mbushTblPasegjeretNgaDB();
                //pastrojme fields
                txtemri.clear();
                txtmbiemri.clear();
                id.clear();
                tblTreniUser.getSelectionModel().clearSelection(); //pastron perzgjedhjen
            }
                
        } catch (NumberFormatException e) {//nqs array per karakteret_speciale nuk i trajton te gjitha vlerat
            shfaqAlert(Alert.AlertType.ERROR, "Gabim", "ID duhet te jete numer.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //metoda per te fshire te dhenat
    public void fshijPasegjer() {
    	try {
    		//kontrollojme nqs perdoruesi ka zgjedhur nje pasegjer per te bere fshirjen e tij 
    		User pasegjeri_i_zgjedhuar = tblPasegjeret.getSelectionModel().getSelectedItem();
    		
    		if(pasegjeri_i_zgjedhuar == null) {
    			shfaqAlert(AlertType.WARNING, "Gabim!", "Ju lutemi zgjidhni ne fillim nje pasegjer nga tabela pasegjeret \n per te kryer fshirjen.");
    		    return;
    		}
    		
             String idStr = id.getText();
             String emri = txtemri.getText();
             String mbiemri = txtmbiemri.getText();
             
             
             
            //kontroll per id
   		    if (idStr == null || idStr.length() == 0) {
   			   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha ID nuk mund te lihet bosh.Ju lutemi plotesojeni qe te fshini pasagjerin.");
   			   return; //per te ndalur veprimet 
   		     }
   		    
   		    //e kthejme ne int 
            int idVlera = Integer.parseInt(idStr);
            
   		    
   		    //kontroll per id nqs nuk eshte i njete me ata te databazes
   		    if (idVlera != pasegjeri_i_zgjedhuar.getId()) {
   			   shfaqAlert(AlertType.WARNING, "Gabim!", ".Ju nuk mund te ndryshoni id gjate fshirjes se pasagjerit.");
   			   return; //per te ndalur veprimet 
   		     }
   		    
   		    //kontroll per emrin 
   		    if (emri == null || emri.length() == 0) {
   			   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te lihet bosh.Ju lutemi plotesojeni qe te fshini pasagjerin.");
   			   return; //per te ndalur veprimet 
   		     }
   		     
   		     //kontroll per emrin nqs nuk eshte i njete me ata te databazes
   		    if (!emri.equals(pasegjeri_i_zgjedhuar.getEmerPasegjeri())) {
   			   shfaqAlert(AlertType.WARNING, "Gabim!", ".Ju nuk mund te ndryshoni emrin e pasegjerit gjate fshirjes se pasagjerit.");
   			   return; //per te ndalur veprimet 
   		     }
   		    
   		    //kontroll per mbiemrin
   		    if (mbiemri == null || mbiemri.length() == 0) {
   			   shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Mbiemri nuk mund te lihet bosh.Ju lutemi plotesojeni qe te fshini pasagjerin.");
   			   return; //per te ndalur veprimet 
   		     }
   		    
   		    //kontroll per emrin nqs nuk eshte i njete me ata te databazes
   		    if (!mbiemri.equals(pasegjeri_i_zgjedhuar.getMbiemrPasegjeri())) {
   			   shfaqAlert(AlertType.WARNING, "Gabim!", ".Ju nuk mund te ndryshoni mbiemrin e pasegjerit gjate fshirjes se pasagjerit.");
   			   return; //per te ndalur veprimet 
   		     }
       
            
            //lidhemi me databazen
            Connection conn = Databaze.methodConnect();
            //prep stmt per ekzekutim ku do te marrim numrin e trinit tek id (key) e perdoruesit ne tabeln user 
            PreparedStatement psMerrTreni = conn.prepareStatement("SELECT NrTren FROM user WHERE id = ?");
            psMerrTreni.setInt(1, idVlera);
            //e ekzekutojme 
            ResultSet rs = psMerrTreni.executeQuery();

         
            
            
            //marrim numrin e trnit sepse do te na duhet ta rrimisim me 1 kapacitetin pas heqjes se pasgjerit 
            //int nrTreni = rs.getInt("NrTren");
            //kontrollojme nqs ka rezultat
            int nrTreni = -1;
            if (rs.next()) {
                nrTreni = rs.getInt("NrTren");
            } else {
                shfaqAlert(Alert.AlertType.ERROR, "Gabim", "Nuk u gjet treni për pasagjerin me ID: " + idVlera);
                return;
            }

            PreparedStatement psFshij = conn.prepareStatement("DELETE FROM user WHERE id=?");
            psFshij.setInt(1, idVlera);

            //marrim poicionin  
            int rreshtiHequr = psFshij.executeUpdate(); //sepse excecute jep nje int (numrin e rreshtit te prekur)
            
            //tek ky pozicion  qe hoqem trenin (pra marrim numrin e trenit) kryejm 
            if (rreshtiHequr > 0) {
            	//rrisim kapacitetin e atij treni me 1 
                PreparedStatement psRritKapacaitet = conn.prepareStatement("UPDATE admindb SET Kapaciteti = Kapaciteti + 1 WHERE Nr = ?");
                //gjen tek kolona 1 (pozicioni) me te dhenet e nrTreni 
                psRritKapacaitet.setInt(1, nrTreni);
                //ekzekutojme sql
                psRritKapacaitet.executeUpdate(); 

                shfaqAlert(Alert.AlertType.INFORMATION, "Sukses", "Pasagjeri u fshi me sukses!");
                //perditesojme tabeln e databazes
                mbushTblPasegjeretNgaDB();
                
                //dhe pastrojme fieldsat
                id.clear();
                txtemri.clear();
                txtmbiemri.clear();
                tblTreniUser.getSelectionModel().clearSelection(); // pastron përzgjedhjen
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


 //metoda per te marre perzgjedhjen nga tabela dhe per te mbushur fushat
    private void merrperzgjedhjen() {
        User pasagjerZgjedhur = tblPasegjeret.getSelectionModel().getSelectedItem();
        if (pasagjerZgjedhur != null) {
            txtemri.setText(pasagjerZgjedhur.getEmerPasegjeri());
            txtmbiemri.setText(pasagjerZgjedhur.getMbiemrPasegjeri());
            id.setText(String.valueOf(pasagjerZgjedhur.getId()));
        }
    }
}
    