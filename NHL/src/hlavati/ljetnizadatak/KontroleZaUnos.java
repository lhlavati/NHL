/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hlavati.ljetnizadatak;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 *
 * @author Luka
 */
public class KontroleZaUnos {

    public static int unosInt(String poruka) {

        int i;
        while (true) {
            try {
                i = Integer.parseInt(JOptionPane.showInputDialog(poruka));
                if (i <= 0) {
                    JOptionPane.showMessageDialog(null, "Broj mora biti veći od nule");
                    continue;
                }
                return i;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Obavezan unos broja");
            }
        }

    }

    public static String unosString(String poruka) {

        String s;
        while (true) {

            s = JOptionPane.showInputDialog(poruka);
            if (s.trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Obavezan unos");
                continue;
            }
            return s.trim();
        }

    }

    public static String unosPozicije(String poruka) {
        String s;
        int i;
        while (true) {
            s = unosString(poruka);
            if (s.length() > 2) {
                JOptionPane.showMessageDialog(null, "Pozicija ne može imati više od 2 slova!");
            } else {
                try {
                    i = Integer.parseInt(s);
                    JOptionPane.showMessageDialog(null, "Pozicija ne može biti broj!");
                } catch (Exception e) {
                    return s;
                }
            }
        }

    }

    public static int rezultat(String poruka) {

        int i;
        while (true) {
            try {
                i = Integer.parseInt(JOptionPane.showInputDialog(poruka));
                if (i < 0) {
                    JOptionPane.showMessageDialog(null, "Rezultat ne može biti negativan");
                    continue;
                }
                return i;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Obavezan unos broja");
            }
        }

    }

    public static java.sql.Date unosDatum(String poruka) {

        while (true) {

            try {

                return java.sql.Date.valueOf(JOptionPane.showInputDialog(poruka));
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Obavezan unos u formatu: yyyy-MM-dd\nPrimjer na današnjem datumu: " + java.sql.Date.valueOf(LocalDate.now()));
            }

        }

    }

}
