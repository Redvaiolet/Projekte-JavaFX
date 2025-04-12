package application3;

import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class Kontroll3 {
	//array per te mbajtu karakteret speciale te palejuara qe mund te vendos perdoruesi
    private static char[] karakter_special = { '!', '@', '#', '$', '%', '&', '*', '(', ')','-' ,'_', '=', '[',']', '{', '}', ';', ':', '"', '<', '>', '?', '\\', '|', '~', '§' };

   
    @FXML
    private Button Dil, Kthehu, fshij, llogaritMes, shto, modifiko, pastro; //butona per veprime te ndryshme 

    @FXML
    private ListView<String> lst; //ListView per te ruajtur dhe shfaqur te dhenat 

    @FXML
    private TextField txtData, txtLenda, txtMesatare, txtNota, txtProfesor; //fushat e inputit te perdoruesit 

    //liste per te ruajtur notat e perdoruesit dhe per te llogaritur mesataren 
    private ArrayList<Double> listaNotave = new ArrayList<>();

    //metode initialize per te lidhur ListView dhe me nje stil css
    public void initialize() {
        ObservableList<String> dataList = FXCollections.observableArrayList();
        lst.setItems(dataList);
        
        //ndryshim i fontit per te siguruar nje pamje me te mire te ListView
        lst.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px;");
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
    
    //metoda per te shfaqur alert konfirmimin e mbylljes se programit
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
    
    
  //metode per te shtuar nje element ne ListView dhe per te ruajtur noten ne liste
    public void shtoElement() {
    	try {
    		//variabla per te marre dhe  kontrolluar inputin e perdoruesit 
    		String profesori = txtProfesor.getText();
    		String lenda = txtLenda.getText();
    		String nota = txtNota.getText();
    		String data = txtData.getText();
    		
    		
    		//kontroll nqs te gjitha fushat jane bosh
    		if((profesori == null || profesori.length() == 0) && (lenda == null || lenda.length() == 0) && (nota == null || nota.length() == 0) && (data == null || data.length() == 0)) {
    			shfaqAlert(AlertType.WARNING, "Gabim!", "Ju keni lene te gjitha fushat bosh.Ju lutemi plotesojini ato.");
    			return; //per te ndalur veprimet 
    		}
    		
    		
    		//kontroll per emrin e profesorit
			if (profesori == null || profesori.length() == 0) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Profesori nuk mund te lihet bosh.Ju lutemi plotesojeni.");
				return; //per te ndalur veprimet 
			}

			//kontrollo per karaktere speciale dhe numra
			for (int i = 0; i < profesori.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
				char c = profesori.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

				//kontrollon per numra
				if (c >= '0' && c <= '9') {
					shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Profesor nuk mund te kete numra.");
					return;
				}

				//kontrollon per karaktere speciale
				for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
						shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Profesor nuk mund te kete karaktere speciale.");
						return; //per te ndalur veprimet 
					}
				}
			}
    		
			
			//kontroll per lenden 
			if (lenda == null || lenda.length() == 0) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Lenda nuk mund te lihet bosh.Ju lutemi plotesojeni.");
				return;
			}

			//kontrollo per karaktere speciale 
			for (int i = 0; i < lenda.length(); i++) {
				char c = lenda.charAt(i);


				//kontrollon per karaktere speciale
				for (char karakter : karakter_special) {//for-each qe kontrollon element per element 
					if (c == karakter) {
						shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Lenda nuk mund te kete karaktere speciale.");
						return;
					}
				}
			}
    		
			//kontroll per noten 
			if (nota == null || nota.length() == 0) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nota nuk mund te lihet bosh.Ju lutemi plotesojeni.");
				return;
			}

			//kontrollo per karaktere speciale dhe shkronjat
			for (int i = 0; i < nota.length(); i++) {
				char c = nota.charAt(i);

				//kontroll per shkronja
				 if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
					    shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nota nuk mund te kete shkronja.");
	                    return;
	                }
             
				//kontrollon per karaktere speciale
				for (char karakter : karakter_special) {
					if (c == karakter) {
						 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nota nuk mund te kete karaktere speciale.");
						 return;
					}
				}
			}
    		
			//kontroll per itervalin e notes
			double notaVlere = Double.parseDouble(nota);  //metoda perseDouble ben pjese ne klasen Double qe ben kthimin e nje String numer ne nje double per manipulim
			if(notaVlere < 4 || notaVlere > 10) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nota mund te jete vetem mes 4 dhe 10.");
				return;
				
			}
			
			
			//kontroll nqs data eshte bosh ose me i madh se 10 karaktere
			if(data == null || data.length() == 0 || data.length() > 10) {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te jete bosh ose me e madhe se 10 karaktere.Ju lutemi plotesojeni.");
                return;
			}
			
			//kontroll per shkronja dhe karaktere speciale
			for(int i = 0; i < data.length(); i++) {
				char c = data.charAt(i);
				
				//kontroll per shkronja
				 if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
					 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te kete shkronja.");
	                 return;
	                }
				 
				 //kontroll per karakter speciale
				 for(char karakter : karakter_special) {
					 if(c == karakter) {
						 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te kete karaktere speciale.");
						 return;
					 }
				 }                                  //metoda add() vjen nga klasa List per UI FXML(lst) dhe ArrayList per listen listaNotave 
			}                                      //dhe perdoret per te shtuar nje element te ri ne liste (ListView dhe List)
			                                       //ne rastin tone shtojme el ne ListView dhe notat i vendosim ne nje list ku me pas do bejm veprime me to
			//shtojm te dhenat ne ListView
			String element = String.format("  %-25s %-15s %-10s %-12s", profesori, lenda, nota, data);
			lst.getItems().add(element); //shtojme el e ri ne ListView              
            listaNotave.add(notaVlere); //shtojme neten ne listen e notave

            //pastrim i fushave pas insertimit
            txtProfesor.clear();  //clear() eshte metodete e klases javaFx.scene.control per te pastruar fushta e textfields
            txtLenda.clear();
            txtNota.clear();
            txtData.clear();
        } catch (Exception e){//kap gabimet te tjera ne lidhje per JavaFX 
			e.printStackTrace();
		}
    }

    //metoda per te fshire nje element nga ListView dhe nga lista e notave
    public void fshiElement() {
        try {
        	//kontroll nese lista eshtë bosh
            if (lst.getItems().size() == 0) {
            	 shfaqAlert(AlertType.WARNING, "Gabim!", "Lista eshte bosh.Nuk ka asnje element per t'u fshire.");
                 return;
            }
            
            //marrim indeksin e elementit te zgjedhur
            int indeksElement = lst.getSelectionModel().getSelectedIndex();
            
            //kontrollojme nese perdoruesi nuk ka zgjedhur asnje element
            if (indeksElement == -1) {
            	shfaqAlert(AlertType.WARNING, "Gabim!", "Ju nuk keni zgjedhur nje element ne liste per t'u fshire.");
            	return;
               
            }
            
            //mqs gjitha eshte ne rregull fshijme nje element ne ListView
            lst.getItems().remove(indeksElement); //fshijme el qe ndodhet ne ListView me metodne remove() te klases List
            listaNotave.remove(indeksElement); //fshihe edhe noten qe ndodhet ne ArrayList

        } catch (Exception e){//kap gabimet te tjera ne lidhje per JavaFX 
			e.printStackTrace();
		}
    }
    
   
    //metoda per pastrimin e Listview dhe ArrayList
    public void pastroListView() {
    	//pstrim i ListView duke huqur te gjithe elementet
    	lst.getItems().clear();
    	
    	//pastrimi i ArrayList duket hequrte gjithe vlerat e listes
    	listaNotave.clear();
    	
    	//perdtesimi i ListView pas pastrimit te el te tabeles
    	lst.refresh();
    }
    
    
    //metode per te perditesur nje element ne ListView
    public void modifikoElement() {
    	try {
    	int indeksi = lst.getSelectionModel().getSelectedIndex();
    	
    	if(indeksi < 0) {
    		shfaqAlert(AlertType.WARNING, "Gabim!", "Ju nuk keni zgjedhur nje te dhene te listes per modifikim.Ju lumtemi zgjidhni nje per te bere modifikimin.");
    		return;
    	}
    		
    	//variabla per te marre dhe  kontrolluar inputin e perdoruesit 
		String profesori_i_ri= txtProfesor.getText();
		String lenda_e_re = txtLenda.getText();
		String nota_e_re = txtNota.getText();
		String data_e_re = txtData.getText();
		
		
		//kontroll nqs te gjitha fushat jane bosh
		if((profesori_i_ri == null || profesori_i_ri.length() == 0) && (lenda_e_re == null || lenda_e_re.length() == 0) && (nota_e_re == null || nota_e_re.length() == 0) && (data_e_re == null || data_e_re.length() == 0)) {
			shfaqAlert(AlertType.WARNING, "Gabim!", "Ju keni lene te gjitha fushat bosh.Ju lutemi plotesojini ato.");
    		return;
		}
		
		
		//kontroll per emrin e profesorit
		if (profesori_i_ri == null || profesori_i_ri.length() == 0) {
			shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Profesori nuk mund te lihet bosh.Ju lutemi plotesojeni.");
    		return;
		}

		//kontrollo per karaktere speciale dhe numra
		for (int i = 0; i < profesori_i_ri.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			char c = profesori_i_ri.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

			//kontrollon per numra
			if (c >= '0' && c <= '9') {
				shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Profesor nuk mund te kete numra.");
	    		return;
			}

			//kontrollon per karaktere speciale
			for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
				if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
					shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Profesor nuk mund te kete karaktere speciale.");
		    		return;
				}
			}
		}
		
		
		//kontroll per lenden 
		if (lenda_e_re == null || lenda_e_re.length() == 0) {
			shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Lenda nuk mund te lihet bosh.Ju lutemi plotesojeni.");
    		return;
		}

		//kontrollo per karaktere speciale 
		for (int i = 0; i < lenda_e_re.length(); i++) {
			char c = lenda_e_re.charAt(i);


			//kontrollon per karaktere speciale
			for (char karakter : karakter_special) {//for-each qe kontrollon element per element 
				if (c == karakter) {
					shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Lenda nuk mund te kete karaktere speciale.");
		    		return;
				}
			}
		}
		
		//kontroll per noten 
		if (nota_e_re == null || nota_e_re.length() == 0) {
			shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nota nuk mund te lihet bosh.Ju lutemi plotesojeni.");
    		return;
		}

		//kontrollo per karaktere speciale dhe shkronjat
		for (int i = 0; i < nota_e_re.length(); i++) {
			char c = nota_e_re.charAt(i);

			//kontroll per shkronja
			 if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				 shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nota nuk mund te kete shkronja.");
		    	 return;
                }
         
			//kontrollon per karaktere speciale
			for (char karakter : karakter_special) {
				if (c == karakter) {
					shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nota nuk mund te kete karaktere speciale.");
			    	return;
				}
			}
		}
		
		//kontroll per itervalin e notes
		double notaVlere = Double.parseDouble(nota_e_re);  //metoda perseDouble ben pjese ne klasen Double qe ben kthimin e nje String numer ne nje double per manipulim
		if(notaVlere < 4 || notaVlere > 10) {
			shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Nota mund te jete vetem mes 4 dhe 10.");
	    	return;
		}
		
		
		//kontroll nqs data eshte bosh ose me i madh se 10 karaktere
		if(data_e_re == null || data_e_re.length() == 0 || data_e_re.length() > 10) {
			shfaqAlert(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te jete bosh ose me e madhe se 10 karaktere.Ju le+utemi plotesojeni.");
	    	return;
		}
		
		//kontroll per shkronja dhe karaktere speciale
		for(int i = 0; i < data_e_re.length(); i++) {
			char c = data_e_re.charAt(i);
			
			//kontroll per shkronja
			 if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				 shfaqAlert(AlertType.WARNING, "Gabim!","Fusha Data nuk mund te kete shkronja.");
			     return;
                }
			 
			 //kontroll per karakter speciale
			 for(char karakter : karakter_special) {
				 if(c == karakter) {
					 shfaqAlert(AlertType.WARNING, "Gabim!","Fusha Data nuk mund te kete karaktere speciale.");
				     return;
				 }
			 }
		}
		
    		//nje string e re qe perfaqeson te dhenat e reja te perdoruesit
    		String perditesoElement = String.format("  %-25s %-15s %-10s %-12s", profesori_i_ri, lenda_e_re, nota_e_re, data_e_re);
    		
    		//perditesojm ListView duje vendosur el e ri ne pozicionin e zgjedhur
    		lst.getItems().set(indeksi, perditesoElement);
    		
    		//perditesojm listen e notave po ashtu ne pozicionin e zgjedhur 
    		notaVlere = Double.parseDouble(nota_e_re);
    		listaNotave.set(indeksi, notaVlere);
    	
    	
    	   //pastrimi i fushave
    	   txtProfesor.clear();
    	   txtLenda.clear();
    	   txtNota.clear();
    	   txtData.clear();
    	} catch (Exception e){//kap gabimet te tjera ne lidhje per JavaFX 
			e.printStackTrace();
		}
    }
    
    
    
    //metoda per te llogaritur mesataren nga lista e notave
    public void llogaritMesatare() {
        try {
        	//kontrollojme nese lista e notave eshte bosh
            if (listaNotave.size() == 0) {
            	txtMesatare.setText("0.00");
            	shfaqAlert(AlertType.WARNING, "Gabim!","Lista eshte bosh.Nuk ka asnje note per te llogaritur mesataren.");
			    return;
            }
            
            // Variabla per shumen e numrave 
            double shuma = 0.0;
            //tterojme neper listen e notave te ArrayList 
            for (double nota : listaNotave) {//me nje for-each  me nje variabel te perkoheshme double nota
                shuma += nota;
            }
            
            //llogarisim mesataren 
            double mesatarja = shuma / listaNotave.size();
            txtMesatare.setText(String.format("%.2f", mesatarja)); //nxjerrim mesataren me formatin me dy shifra pas presjes
            
        } catch (Exception e){//kap gabimet te tjera ne lidhje per JavaFX 
			e.printStackTrace();
		}
    }

    //metode per te dale nga programi
    public void dilNgaProgrami() {
    	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
        Stage stage = (Stage) txtProfesor.getScene().getWindow();
        stage.close();
        konfirmimAlert(AlertType.INFORMATION, "Informacion","Faleminderit qe perdoret kete aplikacion.");
        
    }

    //metoda per tu kthyre ne faqen Log In
    public void kthehuNeLogIn() {
        try {
        	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
            Stage stage_aktual = (Stage) txtNota.getScene().getWindow();
            stage_aktual.close();

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
            //i vendosim nje titull dritares 
            stage_i_ri.setTitle("Universiteti Luigj Gurakuqi");
            //shfaq dritaren 
            stage_i_ri.show(); 
        
        } catch (Exception e) {//kap gb dhe i shfaq ne konsole nese ka te JavaFX
            e.printStackTrace();
        }
    }
}
