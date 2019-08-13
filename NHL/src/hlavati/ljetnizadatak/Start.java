/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hlavati.ljetnizadatak;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Luka
 */
public class Start {

    private Connection veza;

    public Start() {
        veza = Baza.getConnection();
        JOptionPane.showMessageDialog(null, "Preporuka: Console-u stavite preko cijelog ekrana radi boljeg pregleda! :)");

        izlaz:
        while (true) {
            izbornik();
            switch (KontroleZaUnos.unosInt("Unesite redni broj stavke:")) {
                case 1:
                    urlEra();
                    break;
                case 2:
                    urlGit();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Doviđenja!");
                    break izlaz;
                default:
                    JOptionPane.showMessageDialog(null, "Nevazeci broj!");
                    break;
            }

        }
    }

    private void izbornik() {

        System.out.println("################### IZBORNIK ###################");
        System.out.println("##  1. URL ERA dijagrama		      ##");
        System.out.println("##  2. URL GitHub koda	                      ##");
        System.out.println("##  3. Čitanje svih podataka odabrane tablice ##");
        System.out.println("##  4. Unos svih podataka u odabranu tablicu  ##");
        System.out.println("##  5. Promjena podataka u odabranoj tablici  ##");
        System.out.println("##  6. Brisanje podataka u odabranoj tablici  ##");
        System.out.println("##  7. Izlaz				      ##");
        System.out.println("################################################");

    }

    private void urlEra() {

        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/lhlavati/NHL/blob/master/ERA/ERA%20NHL.png"));
            JOptionPane.showMessageDialog(null, "URL uspješno otvoren!");
        } catch (IOException ex) {
            Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void urlGit() {
        izlaz:
        while (true) {
            System.out.println(
                    "\n1. Start.java\n OPIS: Start klasa koja sadrži metode urlGit, urlEra, izbornik, konstruktor i main\n\n"
                    + "2. Baza.java\n OPIS: Jednostavna konekcija sa bazom\n\n3. KontroleZaUnos.java\n OPIS: Metode koje sluze"
                    + " kao kontrola prilikom unosa i promjene u bazi\n\n4. Crud.java\n OPIS: Sve metode koje su omogućile"
                    + " CRUD baze\n\n5. IZLAZ\n");
            switch (KontroleZaUnos.unosInt("Unesite redni broj klase koju zelite otvoriti")) {
                case 1:
                    
                    break;
                case 5:
                    break izlaz;
                default:
                    JOptionPane.showMessageDialog(null, "Nevazeci broj!");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new Start();
    }

}
