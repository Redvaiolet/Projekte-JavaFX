package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

public class KontrollTrenachart {
	
	//mbaj mend do me shume se 2 tren se ta qet komplet keq chartin

	@FXML
	private PieChart pelqyeshmeria;
	
	@FXML private Button ktheuUser;
	
    //metode per perditesimin e chart, do te behet edhe nga jashte ne kontrollUser
	public void perditesoChart() {
		try {
			//lista  per pie chart  (per popularitetin e trenave)
			ObservableList<PieChart.Data> lisatChart = FXCollections.observableArrayList();
			//thjeshte po kontrollon emrat e trenve duke i grupuar dhe shuma e tyre eshte populariteti
			//dhe i vendosim si numri i personave
			String query = "SELECT EmriTreni, COUNT(*) AS NrPersona FROM user GROUP BY EmriTreni";

			//lidhemi me databazen
			Connection conn = Databaze.methodConnect();
			//pergatisim nje komande per ekzekutim
			Statement stmt = conn.createStatement();
			//dhe ekzekutojme komanden e query
			ResultSet rs = stmt.executeQuery(query);

			//iterojme nepr tabelen e user te databazes
			while (rs.next()) {
				//marrim emrin e trenit nga kolona "EmriTreni" e rreshtit aktual
				String emriTreni = rs.getString("EmriTreni");
				//marrim numrin e personave (numer) nga kolona  NrPersona e rreshtit aktual
				int nrPersona = rs.getInt("NrPersona");
				
				//krijojme nje obj te ri PieChart.Data me emrin e trenit dhe numrin e personave,
                //dhe e shton ate ne listen lisatChart
				lisatChart.add(new PieChart.Data(emriTreni, nrPersona));
			}

			//vendosim si vlere legjende
			//listen e populluar lisatChart si burim te dhenash per grafikun pelqyeshmeria
			pelqyeshmeria.setData(lisatChart);
			pelqyeshmeria.setTitle("Pelqyeshmeria e Trenave");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	//inicializojme fxml
	public void initialize() {
		perditesoChart(); //e therret ne fillim vetem 1 here,kur ngarkohet FXML
		
		//me nje event hendeler dhe shpreheje lambda bejme ndryshimin ne scene
		ktheuUser.setOnAction(event -> {
			Main.stage4.show();
			Main.stage5.hide();
		});
	}

}
