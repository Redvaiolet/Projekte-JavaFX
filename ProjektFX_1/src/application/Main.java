package application;
	
import javafx.application.Application;//import klasen kryesore per JavaFX qe përdoret per te nisur aplikacionet grafike
import javafx.stage.Stage;//import klasen Stage,e cila perfaqëson dritaren kryesore te aplikacionit
import javafx.scene.Parent;//import klasen Parent,qe eshte klasa baze per te gjithe komponentet e skenes
import javafx.scene.Scene;//importon klasen Scene,qe perfaqeson skenen grafike te dritares
import javafx.scene.image.Image;//import klasen Image per te vendosur ikona ne dritare
import javafx.fxml.FXMLLoader;//import FXMLLoader per te ngarkuar skedaret FXML, qe përcaktojne nderfaqen grafike


public class Main extends Application { //Main trashogohet (klasa femije) nga Application per te ndertuar aplikacionin JavaFX
	@Override          //dritare Log In
	public void start(Stage primaryStage) {//metode kryesore qe ekzekutohet ne JavaFX,merr si argument nje objekt Stage qe perfaqeson dritaren kryesore te aplikacionit
		try {                             
			//ngarkon skedarin FXML qe permban pershkrimin e nderfaqes grafike
			Parent root = FXMLLoader.load(getClass().getResource("dritatre_01.fxml"));
			
			//krojojme nje skene me permasa ne pixel qe do jete medhesia e dritares/faqes
			Scene scene = new Scene(root,600,400);
			
			//ngarkojm nje ikone per dritaren e aplikacionit
			Image icon = new Image(getClass().getResourceAsStream("/android-icon-192x192.png"));
			primaryStage.getIcons().add(icon);//me metoden add() te klases List shton ikonen ne dritaren e prgramit dhe me getIcon() marrim iazhin
			
			
			primaryStage.setScene(scene);//vendosim skenen e krijuaj ne dritaren kryesore me metoden setScene()
			
			primaryStage.setTitle("Universiteti Luigj Gurakuqi");//vendosim nje titull dritares
			primaryStage.show();//me metoden show() shfaqim dritaren ne ekran
		} catch(Exception e) { //kapim perjashtimet /gb
			e.printStackTrace();//i shfaqim gb ne konsole 
		}
	}
	
	public static void main(String[] args) {//metoda kryesore qe nis aplikacionin JavaFX
		launch(args);//me metoden launch te klases javafx.application.Application ngarkojem/nisim ekzekutimin
	}               //e aplikacionit duke thirrur metoden start()
}


//--> metoda load() e klases javafx.application.Application,perdoret per te ngarkuar te dhena ose per te inicializuar komponentet e UI (si skedare fxml, elemente vizuale, etj.)

//-->metoda getClass() e klases Object(dhe perdoret nga çdo klase ne JavaFX,pasi eshte nje metode e trasheguar nga klasa baze Object)
//Funksioni:Kthen objektin e tipit Class qe perfaqeson klasen e objektit aktual,perdoret per te marre informacion mbi tipin e klases ne kohen e ekzekutimit

//-->metoda getResource() e klasat ClassLoader ne JavaFX.Funksioni:perdoret per te ngarkuar burime (si skedare fxml, imazhe, dhe burime te tjera) nga nje rruge ne klasen ose paketen e aplikacionit

//-->metoda launch() e klases javafx.application.Application ngarkojem/nisim ekzekutimin e aplikacionit duke thirrur metoden start()

//-->metoda printStackTrace() e klases Throwable,perdoret per te shfaqur perjashtimin si nje gjurme ne ndihme te identifikimit te vendodhjes se problemit

//-->mtoda String.format() e klases String,perdoret per formatimin e nje vargu duket zevenendesuar vendet bosh me nje vlere te caktuar string

//-->metoda getItems() e klases ObservableList ose ComboBox/ListView ne avaFX.Funksioni:per te marre listen e elementeve te shtuar ne nje komponent UI si ComboBox ose ListView

//-->metoda set() e klasat ObservableList,Property(si IntegerProperty, StringProperty, etj.),dhe komponent të tjere në JavaFX.
//Funksioni:per te vendosur nje vlere te re per nje objekt,per azhurnimin e elementeve ne nje liste p.sh ListView

//-->metoda clear() e klases List,Set,Map(si ArrayList, HashSet, etj.) dhe ObservableList ne JavaFX
//Funksioni:per te hequr te gjitha elementet nga nje koleksion ose liste

//-->metoda setText() e klases TextField,Label,TextArea,dhe komponentë te tjerë te UI në JavaFX
//Finksioni:per te vendosur tekstin(string) ne nje komponent te UI (p.sh Label,TextField)

//-->mtoda getText() e klases TextField etj,Finksioni:per te marre tekstin(string) aktual nga nje komponent te UI (p.sh TextField/Label)

//-->metoda ObsevableList e klases javafx.collections.ObservableList.Funksioni:per te ruajtur nje list elementesh qe monitorohen per ndryshime 
//p.sh si shtimi,heqja ose modifikimi i elementeve,dhe perdoret per te perditesuar automatikisht UI ne JavaFX

//-->metoda observableArrayList() e klases javafx.collections.FXCollections.Funksioni:krijon nje ObservableList monitorohet per ndryshime,duke lejuar perditësimin automatik te UI ne JavaFX kur lista ndryshon 

//-->metoda refresh() e klases TableView,ListView,ose komponente qe përdorin modele te te dhënave.Funksioni:per te rifreskuar permbajtjen e nje TableView ose ListView UI,pas nje ndryshimi ne te dhenat e tij




