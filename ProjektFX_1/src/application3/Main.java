package application3;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;


public class Main extends Application {//Main trashogohet (klasa femije) nga Application per te ndertuar aplikacionin JavaFX
	@Override     //metoda kryesore per te nisur aplikacionin JavaFX
	public void start(Stage primaryStage) {
		try {
			//ngarkon skedarin FXML per te krijuar nderfaqen grafike (GUI)
			Parent root = FXMLLoader.load(getClass().getResource("dritare_03.fxml"));
			//krijojme nje skene me permasa fikse 
			Scene scene = new Scene(root,719,492);
			
			//vendosje ikone ne dritaren kryesore 
			Image icon = new Image(getClass().getResourceAsStream("/android-icon-192x192.png"));
			primaryStage.getIcons().add(icon);
			
			//vendosim skenen per dritaren kryesore 
			primaryStage.setScene(scene);
			//vendosim titullin e dritares kryesore
			primaryStage.setTitle("Universiteti Luigj Gurakuqi");
			//shfaqim dritaren kryesore 
			primaryStage.show();
		
		} catch(Exception e) {//kap dhe shfaq te gjitha gb qe mund te kete JavaFX
			e.printStackTrace();
		}
	}
	
	//metoda kryesore qe nis ekzekutimin e aplikacionit JavaFX
	public static void main(String[] args) {
		launch(args);//me metoden launch te klases javafx.application.Application ngarkojem/nisim ekzekutimin
	}                //e aplikacionit duke thirru metoden start()
}
