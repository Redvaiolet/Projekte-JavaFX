package application2;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;


public class Main extends Application {//Main trashogohet (klasa femije) nga Application per te ndertuar aplikacionin JavaFX
	@Override              //dritarja Sign In
	public void start(Stage primaryStage) {//metoda start per te nisur GUI-ne kryesore (Sign In)
		try {
			//ngarkon skedarin FXML qe permban përshkrimin e dritares se hyrjes (Sign In)
			Parent root = FXMLLoader.load(getClass().getResource("dritare_02.fxml"));
			//krijojme nje skene me permasa fikse 
			Scene scene = new Scene(root,600,400);
			
			//vendos ikonen e dritares duke perdorur nje imazh te ruajtur ne resurse -->tek src file
			Image icon = new Image(getClass().getResourceAsStream("/android-icon-192x192.png"));
			primaryStage.getIcons().add(icon);
			
			//vendos skemen e krijuar ne dritaren kryesore 
			primaryStage.setScene(scene);
			
			//i vendosim nje titull kesaj dritarjeje
			primaryStage.setTitle("Universiteti Luigj Gurakuqi");
			//shfaq dritare kryesore 
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//metoda kryesore qe nis ekzekutimin e aplikacionit JavaFX
	public static void main(String[] args) {
		launch(args);    //me metoden launch() te klases javafx.application.Application ngarkojem/nisim ekzekutimin                 
	}                    //e aplikacionit duke thirru metoden start()
}
