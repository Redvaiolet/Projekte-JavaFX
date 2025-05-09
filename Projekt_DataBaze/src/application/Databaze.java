package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//lidhja e databazes me tabelat ne fxml
public class Databaze {
     
	public static Connection methodConnect() throws SQLException {
		String databasename = "railway";
		String username = "root";
		String password = "pokemon.X";
		String url = "jdbc:mysql://localhost:3306/" + databasename;
		
		return DriverManager.getConnection(url, username, password);
		
	}
}
