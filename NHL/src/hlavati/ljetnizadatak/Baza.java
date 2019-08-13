/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hlavati.ljetnizadatak;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Luka
 */
public class Baza {
    
    public static Connection getConnection() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mariadb://localhost/nhl","edunova","edunova");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
