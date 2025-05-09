package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.AudioClip;

public class Kontroll1 {
	//fusha FXML te lidhura me perberesit grafike nga skedari FXML
    @FXML private RadioButton admin; //radioButton per zgjedhjen e Adminit -->Log In fxml
    @FXML private RadioButton user;//radioButton per zgjedhjen e User-it --> user fxml
    
    @FXML private Button enter; //dhe dy butona njeri per hyrej ne aplikacion
    @FXML private Button exit; //tjetri per mbylljen e aplikacionit

    
    //deklarim i nje objekt ToggleGroup me emrin 'zgjedhja',ku grupi do te perdoret per menaxhimin e radiobutonave 'admin' dhe 'user',
    //duke siguruar qe vetem njeri prej tyre mund te zgjidhet ne te njejten kohe
    private ToggleGroup zgjedhja;

    //metode ndihmese per te shfaqur Alert (dritare njoftimi)
    //merr si parametra tipin e njoftimit(AlertType),titullin e dritares(String title) dhe permbajtjen e mesazhit (String content)
    private void showAlert(AlertType alertType, String title, String content) {
    	//krijojme nje objekt AudioClip per te luajtur nje tingull
    	//dhe luajtur ate kur showAlert aktifizohet
        AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Background.wav");
        clip.play();

        //krijohet dhe shfaqet nje Alert ne varesi te tipit (INFORMATION, WARNING, etj.)
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();//ndalon derisa perdoruesi ta mbylle alertin
    }

    //inicializim -->perdoret per konfigurimin fillestar te kontrollerit
    //dhe togglegroup
    public void initialize() {
    	//krijojme nje togglegroup 
        zgjedhja = new ToggleGroup();
        //i vendosm dy butonat ne togglegroup qe vetem nje te jete aktiv vetem njeri prej tyre 
        user.setToggleGroup(zgjedhja);
        admin.setToggleGroup(zgjedhja);
        
        //me nje action event bejme
        enter.setOnAction(event -> {
            
            	//nqs butoni user eshte zgjedhuar hapim dritaren User
                if (user.isSelected()) {
                    openStage4();
                //nqs butoni admin eshte zgjedhur hapim dritaren Log In
                } else if (admin.isSelected()) {
                    openStage2();
                } else {
                //nqs nuk zgjidhet asnjera
                    showAlert(AlertType.WARNING, "Gabim!", "Ju lutemi zgjidhni nje nga opsionet per te vazhduar");
                }});
        
       //kur butoni 'exit' klikohet therret metoden 'exit()' qe ben mbylljen e aplikacionit
       exit.setOnAction(event ->exit());
    }

    // metoda qe do te hape dritaren 4 -->User
    public void openStage4() {
        Main.k4.refreshtabelaUser(); // <-- rifreskojme tabelen me trenat pas cdo ndryshimi te bere nga admini
        //shfaqim dritaren e katert 
        Main.stage4.show();
       //fshehim dritaren aktuale -->me metoden hide()
        Main.stage1.hide();
        //thirret metoda reset per te pastruar radiobutonat 
        reset();
    }

    // metoda qe do te hape dritaren 2 -->Log In
    public void openStage2() {
    	//shfaqim dritaren e dyte 
        Main.stage2.show();
        //fshehim dritaren aktuale 
        Main.stage1.hide();
      //thirret metoda reset per te pastruar radiobutonat
        reset();
    }
    
    //metode per te mylluar aplikacionin 
    public void exit() {
    	//mbyllim dritaren kryesore me metoden close();
    	Main.stage1.close();
    	//shfaqim nje mesazh
    	showAlert(AlertType.INFORMATION, "Informacion", "Faleminderit qe perdoret kete aplikacion.");
    	
    }

    //metode per te bere reset butonave (kthimi ne gjendej fillestare)
    private void reset() {
        zgjedhja.selectToggle(null); //ku asnje radiobuton nuk eshte zgjedhuar
    }
}

