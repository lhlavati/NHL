/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hlavati.ljetnizadatak;

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

    public static String unosPozicije(String poruka){
        String s;
        int i;
        while(true){
            s = unosString(poruka);
            if(s.length() > 2){
                JOptionPane.showMessageDialog(null, "Pozicija ne može imati više od 2 slova!");
            }else{
                try {
                    i = Integer.parseInt(s);
                    JOptionPane.showMessageDialog(null, "Pozicija ne može biti broj!");
                } catch (Exception e) {
                    return s;
                }
            }
        }
        
    }
}
