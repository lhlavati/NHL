/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hlavati.ljetnizadatak;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Luka
 */
public class Crud {

    public static Connection veza;
    public static PreparedStatement izraz;

    private static void ispisiTablicu(String upit) {

        try {
            PreparedStatement izraz0 = veza.prepareStatement(upit);
            ResultSet rs = izraz0.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            for (int i = 1; i <= columnsNumber; i++) {
                System.out.print(rsmd.getColumnName(i) + " | ");
            }
            System.out.println("");
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        System.out.print(" | ");
                    }
                    System.out.print(rs.getString(i));
                }
                System.out.println("");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void citajIzBaze() {

        izlaz:
        while (true) {
            System.out.println("\n1. igrac\n2. tim\n3. utakmica\n4. ozljeda\n5. IZLAZ");
            switch (KontroleZaUnos.unosInt("Unesite redni broj tablice koju biste htjeli čitati")) {
                case 1:
                    ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                            + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
                    JOptionPane.showMessageDialog(null, "Tablica igrac prikazana!");
                    break;
                case 2:
                    ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                            + "FROM tim b INNER JOIN igrac a ON b.sifra = a.tim\n"
                            + "WHERE a.sifra = 11 OR a.sifra = 23 OR a.sifra = 35;");
                    JOptionPane.showMessageDialog(null, "Tablica tim prikazana!");
                    break;
                case 3:
                    ispisiTablicu("SELECT c.sifra, c.rezultatDomacin, c.rezultatGost, c.datumUtakmice, d.ime AS domacin, b.ime AS gost\n"
                            + "FROM utakmica c INNER JOIN tim b ON b.sifra = c.domacin\n"
                            + "INNER JOIN tim d ON d.sifra = c.gost");
                    JOptionPane.showMessageDialog(null, "Tablica utakmica prikazana!");
                    break;
                case 4:
                    ispisiTablicu("SELECT e.sifra, e.opisIncidenta, e.opisOzljede, concat(a.ime, ' ', a.prezime) AS igrac\n"
                            + "FROM ozljeda e INNER JOIN igrac a ON a.sifra = e.igrac;");
                    JOptionPane.showMessageDialog(null, "Tablica ozljeda prikazana!");
                    break;
                case 5:
                    break izlaz;
                default:
                    JOptionPane.showMessageDialog(null, "Nevazeci broj!");
                    break;
            }
        }

    }
}
