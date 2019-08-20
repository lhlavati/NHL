/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hlavati;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Luka
 */
public class Baza {

    public Baza() {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection veza = DriverManager.getConnection(
                    "jdbc:mariadb://localhost/NHL",
                    "edunova", "edunova");
            PreparedStatement izraz = veza.prepareStatement(
                    "SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime "
                  + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
            ResultSet rs = izraz.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("ime"));
            }
            rs.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Baza();
    }
}
