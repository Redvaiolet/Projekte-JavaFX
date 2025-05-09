package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;

public class Main extends Application { ////Main trashogohet (klasa femije) nga Application per te ndertuar aplikacionin JavaFX

    //variabla globale per stages 
    public static Stage stage1; //Home Page
    public static Stage stage2; //Log In Page
    public static Stage stage3; //Admin
    public static Stage stage4; //User
    public static Stage stage5; //chart

    //objekte qe do te perdoren per te kaluar kontrollerin dhe per ti dhene atij perparesi
    public static Kontroll1 k1; //Home Page
    public static kontrollLogIn k2; //Log In
    public static KontrollAdmin k3; //Admin
    public static kontrollUser k4; //User
    public static KontrollTrenachart k5; //chart

    @Override
    public void start(Stage primaryStage) {  //metode kryesore qe ekzekutohet ne JavaFX,merr si argument objektet Stage qe perfaqesojne dritaret kryesore te aplikacionit 
        try {
        	 //ngarkojme ikonat per secilen dritare
            Image iconHome = new Image(getClass().getResourceAsStream("/how-do-diesel-electric-trains-work-and-why-do-they-need-turbochargers-2880x1920-removebg-preview.png"));
            Image iconLogin = new Image(getClass().getResourceAsStream("/360_F_227450952_KQCMShHPOPebUXklULsKsROk5AvN6H1H-removebg-preview.png"));
            Image iconAdmin = new Image(getClass().getResourceAsStream("/360_F_227450952_KQCMShHPOPebUXklULsKsROk5AvN6H1H-removebg-preview.png"));
            Image iconUser = new Image(getClass().getResourceAsStream("/14797767.png"));

        	
        	
            // per dritaren 1 --> Home Page
            //ngarkon skedarin FXML qe permban pershkrimin e nderfaqes grafike
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("Dritare_01.fxml"));
            Parent root1 = loader1.load();
            //krojojme nje skene me permasa ne pixel qe do jete medhesia e dritares/faqes
            Scene scene1 = new Scene(root1, 773, 520);

            stage1 = primaryStage; //vendosim stage1 si primaryStage --> kjo do hapet e para si scene
            stage1.setScene(scene1);//vendosim skenen -->permasat e dritares  me metoden setScene()
            stage1.setTitle("Home Page"); // vendosim titullin
            stage1.getIcons().add(iconHome); // ikona e pare -->me metoden add() te klases List shton ikonen ne dritaren e prgramit dhe me getIcons() marrim iazhin
            stage1.show(); // shfaqim vetem dritaren e pare me metoden show()

            // per dritaren 2 --> Log In
            //kopje si e para 
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("logIn.fxml"));
            Parent root2 = loader2.load();
            Scene scene2 = new Scene(root2, 560, 463);

            stage2 = new Stage(); //krijojme nje Stage te ri per LogIn
            stage2.setScene(scene2);//vendosim skenen -->permasat e dritares 
            stage2.setTitle("Log In");//vendosim titullin 
            stage2.getIcons().add(iconLogin); //vendosim ikonene
            
            // per dritaren 3 --> Admin
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("admin.fxml"));
            Parent root3 = loader3.load();
            Scene scene3 = new Scene(root3, 1393, 592);

            stage3 = new Stage(); //krijojme nje Stage te ri per Admin
            stage3.setScene(scene3);//vendosim skenen 
            stage3.setTitle("Admin");//vendsom titullin 
            stage3.getIcons().add(iconAdmin);//vendosim ikonen
            
            // per dritaren 4 --> User
            FXMLLoader loader4 = new FXMLLoader(getClass().getResource("user.fxml"));
            Parent root4 = loader4.load();
            Scene scene4 = new Scene(root4, 1495, 540);

            stage4 = new Stage(); //krijojme nje Stage te ri per User
            stage4.setScene(scene4);
            stage4.setTitle("User");
            stage4.getIcons().add(iconUser);
            
            //per dritaren 5 --> Chart
            FXMLLoader loader5 = new FXMLLoader(getClass().getResource("chart.fxml"));
            Parent root5 = loader5.load();
            Scene scene5 = new Scene(root5, 838, 520);

            stage5 = new Stage(); //krijojme nje Stage te ri per User
            stage5.setScene(scene5);
            stage5.setTitle("Chart");
            stage5.getIcons().add(iconUser);
            
            //do kapim kontrollerin dhe do i japim perparesi --> kur te hapet dritarja/fxml ahetehere vetem ai kontroller qe eshte i lidhur me te ka komanden ne 
            k1 = loader1.getController();                       //scene
            k2 = loader2.getController();
            k3 = loader3.getController();
            k4 = loader4.getController();
            k5 = loader5.getController();

        } catch (Exception e) { //kapim perjashtimet /gb
            e.printStackTrace(); //i shfaqim gb ne konsole 
        }
    }

    public static void main(String[] args) {//metoda kryesore qe nis aplikacionin JavaFX
        launch(args); //me metoden launch te klases javafx.application.Application ngarkojem/nisim ekzekutimin
    }                 //e aplikacionit duke thirrur metoden start()
}

