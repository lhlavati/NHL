/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hlavati.ljetnizadatak;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 *
 * @author Luka
 */
public class KontroleZaUnos {
    
    public static PreparedStatement izrazS;
    public static ResultSet rs;
    public static int i;

    public static int unosInt(String poruka) {

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
        while (true) {
            s = unosString(poruka);
            if (s.length() > 2) {
                JOptionPane.showMessageDialog(null, "Pozicija ne može imati više od 2 slova!");
            } else if (s.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(null, "Pozicija ne može sadržavati broj!");
            } else {
                return s;
            }
        }

    }

    public static int rezultat(String poruka) {

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

    public static int unosSifreIgraca(String poruka) {

        while (true) {

            try {

                i = unosInt(poruka);
                izrazS = Crud.veza.prepareStatement("SELECT count(*) AS broj FROM igrac WHERE sifra = ?");
                izrazS.setInt(1, i);
                rs = izrazS.executeQuery();
                rs.next();
                if (rs.getInt("broj") != 0) {
                    return i;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Pogrešan unos!\nMolimo odaberite vazecu sifru igraca!");
                    continue;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0;

        }

    }

    public static int unosSifreTima(String poruka) {

        while (true) {

            try {

                i = unosInt(poruka);
                izrazS = Crud.veza.prepareStatement("SELECT count(*) AS broj FROM tim WHERE sifra = ?");
                izrazS.setInt(1, i);
                rs = izrazS.executeQuery();
                rs.next();
                if (rs.getInt("broj") != 0) {
                    return i;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Pogrešan unos!\nMolimo odaberite vazecu sifru tima!");
                    continue;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0;

        }

    }

    public static int unosSifreUtakmice(String poruka) {

        while (true) {

            try {

                i = unosInt(poruka);
                izrazS = Crud.veza.prepareStatement("SELECT count(*) AS broj FROM utakmica WHERE sifra = ?");
                izrazS.setInt(1, i);
                rs = izrazS.executeQuery();
                rs.next();
                if (rs.getInt("broj") != 0) {
                    return i;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Pogrešan unos!\nMolimo odaberite vazecu sifru utakmica!");
                    continue;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0;

        }

    }

    public static int unosSifreOzljede(String poruka) {

        while (true) {

            try {

                i = unosInt(poruka);
                izrazS = Crud.veza.prepareStatement("SELECT count(*) AS broj FROM ozljeda WHERE sifra = ?");
                izrazS.setInt(1, i);
                rs = izrazS.executeQuery();
                rs.next();
                if (rs.getInt("broj") != 0) {
                    return i;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Pogrešan unos!\nMolimo odaberite vazecu sifru ozljede!");
                    continue;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0;

        }

    }

}
