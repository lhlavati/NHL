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

    public static int i;
    public static int z;

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
                    System.out.println("");
                    ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                            + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
                    JOptionPane.showMessageDialog(null, "Tablica igrac prikazana!");
                    break;
                case 2:
                    System.out.println("");
                    ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                            + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
                    JOptionPane.showMessageDialog(null, "Tablica tim prikazana!");
                    break;
                case 3:
                    System.out.println("");
                    ispisiTablicu("SELECT c.sifra, c.rezultatDomacin, c.rezultatGost, c.datumUtakmice, d.ime AS domacin, b.ime AS gost\n"
                            + "FROM utakmica c INNER JOIN tim b ON b.sifra = c.domacin\n"
                            + "INNER JOIN tim d ON d.sifra = c.gost");
                    JOptionPane.showMessageDialog(null, "Tablica utakmica prikazana!");
                    break;
                case 4:
                    System.out.println("");
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

    public static void brisiIzBaze() {
        int obrisi;
        izlaz:
        while (true) {

            System.out.println("\n1. igrac\n2. tim\n3. utakmica\n4. ozljeda\n5. IZLAZ");
            izadi:
            switch (KontroleZaUnos.unosInt("Unesite redni broj tablice koju biste htjeli obrisati")) {
                case 1:

                    try {
                        System.out.println("");
                        ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                                + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
                        izraz = veza.prepareStatement("DELETE FROM igrac WHERE sifra = ?");
                        while (true) {
                            izraz.setInt(1, KontroleZaUnos.unosSifreIgraca("Unesite šifru reda kojeg bi htjeli obrisati"));
                            obrisi = JOptionPane.showConfirmDialog(null,
                                    "Jeste li sigurni da želite obrisati ovaj podatak?", "Brisanje podatka",
                                    JOptionPane.YES_NO_OPTION);
                            if (obrisi == 0) {
                                JOptionPane.showMessageDialog(null, "Uspješno obrisano (" + izraz.executeUpdate() + ")");
                                System.out.println("\n\n");
                                ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                                        + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
                                JOptionPane.showMessageDialog(null, "Tablica igrac prikazana!");
                                break izadi;
                            }
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ne možete obrisati parent row! Negdje se koristi ovaj red");
                    }

                    break;
                case 2:

                    try {
                        System.out.println("");
                        ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                                + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
                        izraz = veza.prepareStatement("DELETE FROM tim WHERE sifra = ?");
                        while (true) {
                            izraz.setInt(1, KontroleZaUnos.unosSifreTima("Unesite šifru reda kojeg bi htjeli obrisati"));
                            obrisi = JOptionPane.showConfirmDialog(null,
                                    "Jeste li sigurni da želite obrisati ovaj podatak?", "Brisanje podatka",
                                    JOptionPane.YES_NO_OPTION);
                            if (obrisi == 0) {
                                JOptionPane.showMessageDialog(null, "Uspješno obrisano (" + izraz.executeUpdate() + ")");
                                System.out.println("\n\n");
                                ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                                        + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
                                JOptionPane.showMessageDialog(null, "Tablica tim prikazana!");
                                break izadi;
                            }
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ne možete obrisati parent row! Negdje se koristi ovaj red");
                    }

                    break;
                case 3:

                    try {
                        System.out.println("");
                        ispisiTablicu("SELECT c.sifra, c.rezultatDomacin, c.rezultatGost, c.datumUtakmice, d.ime AS domacin, b.ime AS gost\n"
                                + "FROM utakmica c INNER JOIN tim b ON b.sifra = c.domacin\n"
                                + "INNER JOIN tim d ON d.sifra = c.gost");
                        izraz = veza.prepareStatement("DELETE FROM utakmica WHERE sifra = ?");
                        while (true) {
                            izraz.setInt(1, KontroleZaUnos.unosSifreUtakmice("Unesite šifru reda kojeg bi htjeli obrisati"));
                            obrisi = JOptionPane.showConfirmDialog(null,
                                    "Jeste li sigurni da želite obrisati ovaj podatak?", "Brisanje podatka",
                                    JOptionPane.YES_NO_OPTION);
                            if (obrisi == 0) {
                                JOptionPane.showMessageDialog(null, "Uspješno obrisano (" + izraz.executeUpdate() + ")");
                                System.out.println("\n\n");
                                ispisiTablicu("SELECT c.sifra, c.rezultatDomacin, c.rezultatGost, c.datumUtakmice, d.ime AS domacin, b.ime AS gost\n"
                                        + "FROM utakmica c INNER JOIN tim b ON b.sifra = c.domacin\n"
                                        + "INNER JOIN tim d ON d.sifra = c.gost");
                                JOptionPane.showMessageDialog(null, "Tablica utakmica prikazana!");
                                break izadi;
                            }
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ne možete obrisati parent row! Negdje se koristi ovaj red");
                    }

                    break;
                case 4:

                    try {
                        System.out.println("");
                        ispisiTablicu("SELECT e.sifra, e.opisIncidenta, e.opisOzljede, concat(a.ime, ' ', a.prezime) AS igrac\n"
                                + "FROM ozljeda e INNER JOIN igrac a ON a.sifra = e.igrac;");
                        izraz = veza.prepareStatement("DELETE FROM ozljeda WHERE sifra = ?");
                        while (true) {
                            izraz.setInt(1, KontroleZaUnos.unosSifreOzljede("Unesite šifru reda kojeg bi htjeli obrisati"));
                            obrisi = JOptionPane.showConfirmDialog(null,
                                    "Jeste li sigurni da želite obrisati ovaj podatak?", "Brisanje podatka",
                                    JOptionPane.YES_NO_OPTION);
                            if (obrisi == 0) {
                                JOptionPane.showMessageDialog(null, "Uspješno obrisano (" + izraz.executeUpdate() + ")");
                                System.out.println("\n\n");
                                ispisiTablicu("SELECT e.sifra, e.opisIncidenta, e.opisOzljede, concat(a.ime, ' ', a.prezime) AS igrac\n"
                                        + "FROM ozljeda e INNER JOIN igrac a ON a.sifra = e.igrac;");
                                JOptionPane.showMessageDialog(null, "Tablica ozljeda prikazana!");
                                break izadi;
                            }
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ne možete obrisati parent row! Negdje se koristi ovaj red");
                    }

                    break;
                case 5:
                    break izlaz;
                default:
                    JOptionPane.showMessageDialog(null, "Nevazeci broj!");
                    break;
            }

        }

    }

    public static void unesiUBazu() {

        izlaz:
        while (true) {
            System.out.println("\n1. igrac\n2. tim\n3. utakmica\n4. ozljeda\n5. IZLAZ");
            switch (KontroleZaUnos.unosInt("Unesite redni broj tablice u koju biste htjeli unijeti nove podatke")) {
                case 1:
                    try {
                        System.out.println("");
                        ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                                + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
                        izraz = veza.prepareStatement("INSERT INTO igrac (ime, prezime, pozicija, brojDresa, tim) VALUES (?, ?, ?, ?, ?)");
                        izraz.setString(1, KontroleZaUnos.unosString("Unesite ime"));
                        izraz.setString(2, KontroleZaUnos.unosString("Unesite prezime"));
                        izraz.setString(3, KontroleZaUnos.unosPozicije("Unesite poziciju"));
                        izraz.setInt(4, KontroleZaUnos.unosInt("Unesite broj dresa"));
                        System.out.println("");
                        ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                                + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
                        izraz.setInt(5, KontroleZaUnos.unosSifreTima("Unesite sifru željenog tima"));
                        JOptionPane.showMessageDialog(null, "Uspješno uneseno (" + izraz.executeUpdate() + ")");
                        System.out.println("");
                        ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                                + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
                        JOptionPane.showMessageDialog(null, "Tablica igrac prikazana!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:

                    try {
                        System.out.println("");
                        ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                                + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
                        izraz = veza.prepareStatement("INSERT INTO tim (ime, grad, trener, kapetan) VALUES (?, ?, ?, ?)");
                        izraz.setString(1, KontroleZaUnos.unosString("Unesite ime"));
                        izraz.setString(2, KontroleZaUnos.unosString("Unesite grad"));
                        izraz.setString(3, KontroleZaUnos.unosString("Unesite ime i prezime trenera"));
                        System.out.println("");
                        ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                                + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
                        izraz.setInt(4, KontroleZaUnos.unosSifreIgraca("Unesite sifru igraca kojeg zelite za Vaseg kapetana"));

                        JOptionPane.showMessageDialog(null, "Uspješno uneseno (" + izraz.executeUpdate() + ")");
                        System.out.println("");
                        ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                                + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
                        JOptionPane.showMessageDialog(null, "Tablica tim prikazana!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:

                    try {
                        System.out.println("");
                        ispisiTablicu("SELECT c.sifra, c.rezultatDomacin, c.rezultatGost, c.datumUtakmice, d.ime AS domacin, b.ime AS gost\n"
                                + "FROM utakmica c INNER JOIN tim b ON b.sifra = c.domacin\n"
                                + "INNER JOIN tim d ON d.sifra = c.gost");
                        izraz = veza.prepareStatement("INSERT INTO utakmica (rezultatDomacin, rezultatGost, datumUtakmice, domacin, gost) VALUES (?, ?, ?, ?, ?)");
                        izraz.setInt(1, KontroleZaUnos.rezultat("Unesite broj golova postignutih od strane domacina"));
                        izraz.setInt(2, KontroleZaUnos.rezultat("Unesite broj golova postignutih od strane gosta"));
                        izraz.setDate(3, KontroleZaUnos.unosDatum("Unesite datum utakmice u formatu: yyyy-MM-dd"));
                        System.out.println("");
                        ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                                + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
                        izraz.setInt(4, KontroleZaUnos.unosSifreTima("Unesite sifru tima koji je domacin"));
                        izraz.setInt(5, KontroleZaUnos.unosSifreTima("Unesite sifru tima koji je gost"));
                        JOptionPane.showMessageDialog(null, "Uspješno uneseno (" + izraz.executeUpdate() + ")");
                        System.out.println("");
                        ispisiTablicu("SELECT c.sifra, c.rezultatDomacin, c.rezultatGost, c.datumUtakmice, d.ime AS domacin, b.ime AS gost\n"
                                + "FROM utakmica c INNER JOIN tim b ON b.sifra = c.domacin\n"
                                + "INNER JOIN tim d ON d.sifra = c.gost");
                        JOptionPane.showMessageDialog(null, "Tablica utakmica prikazana!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:

                    try {
                        System.out.println("");
                        ispisiTablicu("SELECT e.sifra, e.opisIncidenta, e.opisOzljede, concat(a.ime, ' ', a.prezime) AS igrac\n"
                                + "FROM ozljeda e INNER JOIN igrac a ON a.sifra = e.igrac;");
                        izraz = veza.prepareStatement("INSERT INTO ozljeda (opisIncidenta, opisOzljede, igrac) VALUES (?, ?, ?)");
                        izraz.setString(1, KontroleZaUnos.unosString("Opisite incident"));
                        izraz.setString(2, KontroleZaUnos.unosString("Opisite tip ozljede"));
                        ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                                + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
                        izraz.setInt(3, KontroleZaUnos.unosSifreIgraca("Unesite sifru igraca koji se ozlijedio"));
                        JOptionPane.showMessageDialog(null, "Uspješno uneseno (" + izraz.executeUpdate() + ")");
                        System.out.println("");
                        ispisiTablicu("SELECT e.sifra, e.opisIncidenta, e.opisOzljede, concat(a.ime, ' ', a.prezime) AS igrac\n"
                                + "FROM ozljeda e INNER JOIN igrac a ON a.sifra = e.igrac;");
                        JOptionPane.showMessageDialog(null, "Tablica ozljeda prikazana!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    break izlaz;
                default:
                    JOptionPane.showMessageDialog(null, "Nevazeci broj!");
                    break;
            }
        }

    }

    public static void promjeniUBazi() {

        izlaz:
        while (true) {
            System.out.println("\n1. igrac\n2. tim\n3. utakmica\n4. ozljeda\n5. IZLAZ");
            switch (KontroleZaUnos.unosInt("Unesite tablicu koju biste htjeli promjeniti")) {
                case 1:
                    updateIgrac();
                    break;
                case 2:
                    updateTim();
                    break;
                case 3:
                    updateUtakmica();
                    break;
                case 4:
                    updateOzljeda();
                    break;
                case 5:
                    break izlaz;
                default:
                    JOptionPane.showMessageDialog(null, "Nevazeci broj!");
                    break;
            }
        }

    }

    private static void updateIgrac() {

        try {
            System.out.println("");
            ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                    + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
            i = KontroleZaUnos.unosSifreIgraca("Unesite sifru igraca kojeg biste htjeli promjeniti");

            izraz = veza.prepareStatement("UPDATE igrac SET ime = ? WHERE sifra = ?");
            izraz.setInt(2, i);
            izraz.setString(1, KontroleZaUnos.unosString("Unesite novo ime igraca"));

            PreparedStatement izraz1 = veza.prepareStatement("UPDATE igrac SET prezime = ? WHERE sifra = ?");
            izraz1.setInt(2, i);
            izraz1.setString(1, KontroleZaUnos.unosString("Unesite novo prezime igraca"));

            PreparedStatement izraz2 = veza.prepareStatement("UPDATE igrac SET pozicija = ? WHERE sifra = ?");
            izraz2.setInt(2, i);
            izraz2.setString(1, KontroleZaUnos.unosPozicije("Unesite novu poziciju igraca"));

            PreparedStatement izraz3 = veza.prepareStatement("UPDATE igrac SET brojDresa = ? WHERE sifra = ?");
            izraz3.setInt(2, i);
            izraz3.setInt(1, KontroleZaUnos.unosInt("Unesite novi broj dresa igraca"));

            PreparedStatement izraz4 = veza.prepareStatement("UPDATE igrac SET tim = ? WHERE sifra = ?");
            izraz4.setInt(2, i);
            System.out.println("");
            ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                    + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
            izraz4.setInt(1, KontroleZaUnos.unosSifreTima("Unesite sifru novog tima"));

            z = izraz.executeUpdate() + izraz1.executeUpdate() + izraz2.executeUpdate() + izraz3.executeUpdate() + izraz4.executeUpdate();
            JOptionPane.showMessageDialog(null, "Uspješno promjenjeno (" + z + ")");

            System.out.println("");
            ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                    + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
            JOptionPane.showMessageDialog(null, "Tablica igrac prikazana!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void updateTim() {

        try {
            System.out.println("");
            ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                    + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
            i = KontroleZaUnos.unosSifreTima("Unesite sifru tima kojeg biste htjeli promjeniti");

            izraz = veza.prepareStatement("UPDATE tim SET ime = ? WHERE sifra = ?");
            izraz.setInt(2, i);
            izraz.setString(1, KontroleZaUnos.unosString("Unesite novo ime tima"));

            PreparedStatement izraz1 = veza.prepareStatement("UPDATE tim SET grad = ? WHERE sifra = ?");
            izraz1.setInt(2, i);
            izraz1.setString(1, KontroleZaUnos.unosString("Unesite novi grad tima"));

            PreparedStatement izraz2 = veza.prepareStatement("UPDATE tim SET trener = ? WHERE sifra = ?");
            izraz2.setInt(2, i);
            izraz2.setString(1, KontroleZaUnos.unosString("Unesite novog trenera tima"));

            PreparedStatement izraz3 = veza.prepareStatement("UPDATE tim SET kapetan = ? WHERE sifra = ?");
            izraz3.setInt(2, i);
            ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                    + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
            izraz3.setInt(1, KontroleZaUnos.unosSifreIgraca("Unesite sifru novog kapetana (igraca)"));

            z = izraz.executeUpdate() + izraz1.executeUpdate() + izraz2.executeUpdate() + izraz3.executeUpdate();
            JOptionPane.showMessageDialog(null, "Uspješno promjenjeno (" + z + ")");

            System.out.println("");
            ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                    + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
            JOptionPane.showMessageDialog(null, "Tablica tim prikazana!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void updateUtakmica() {

        try {
            System.out.println("");
            ispisiTablicu("SELECT c.sifra, c.rezultatDomacin, c.rezultatGost, c.datumUtakmice, d.ime AS domacin, b.ime AS gost\n"
                    + "FROM utakmica c INNER JOIN tim b ON b.sifra = c.domacin\n"
                    + "INNER JOIN tim d ON d.sifra = c.gost");
            i = KontroleZaUnos.unosSifreUtakmice("Unesite sifru utakmice kojeg biste htjeli promjeniti");

            izraz = veza.prepareStatement("UPDATE utakmica SET rezultatDomacin = ? WHERE sifra = ?");
            izraz.setInt(2, i);
            izraz.setInt(1, KontroleZaUnos.rezultat("Unesite novi broj golova domacina"));

            PreparedStatement izraz1 = veza.prepareStatement("UPDATE utakmica SET rezultatGost = ? WHERE sifra = ?");
            izraz1.setInt(2, i);
            izraz1.setInt(1, KontroleZaUnos.rezultat("Unesite novi broj golova gosta"));

            PreparedStatement izraz2 = veza.prepareStatement("UPDATE utakmica SET datumUtakmice = ? WHERE sifra = ?");
            izraz2.setInt(2, i);
            izraz2.setDate(1, KontroleZaUnos.unosDatum("Unesite novi datum utakmice u formatu: yyyy-MM-dd"));

            PreparedStatement izraz3 = veza.prepareStatement("UPDATE utakmica SET domacin = ? WHERE sifra = ?");
            izraz3.setInt(2, i);
            System.out.println("");
            ispisiTablicu("SELECT b.sifra, b.ime, b.grad, b.trener, concat(a.ime, ' ', a.prezime) AS kapetan \n"
                    + "FROM tim b INNER JOIN igrac a ON a.sifra = b.kapetan;");
            izraz3.setInt(1, KontroleZaUnos.unosSifreTima("Unesite sifru tima kojeg zelite da bude domacin"));

            PreparedStatement izraz4 = veza.prepareStatement("UPDATE utakmica SET gost = ? WHERE sifra = ?");
            izraz4.setInt(2, i);
            izraz4.setInt(1, KontroleZaUnos.unosSifreTima("Unesite sifru tima kojeg zelite da bude gost"));

            z = izraz.executeUpdate() + izraz1.executeUpdate() + izraz2.executeUpdate() + izraz3.executeUpdate() + izraz4.executeUpdate();
            JOptionPane.showMessageDialog(null, "Uspješno promjenjeno (" + z + ")");

            System.out.println("");
            ispisiTablicu("SELECT c.sifra, c.rezultatDomacin, c.rezultatGost, c.datumUtakmice, d.ime AS domacin, b.ime AS gost\n"
                    + "FROM utakmica c INNER JOIN tim b ON b.sifra = c.domacin\n"
                    + "INNER JOIN tim d ON d.sifra = c.gost");
            JOptionPane.showMessageDialog(null, "Tablica utakmica prikazana!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void updateOzljeda() {

        try {
            System.out.println("");
            ispisiTablicu("SELECT e.sifra, e.opisIncidenta, e.opisOzljede, concat(a.ime, ' ', a.prezime) AS igrac\n"
                    + "FROM ozljeda e INNER JOIN igrac a ON a.sifra = e.igrac;");
            i = KontroleZaUnos.unosSifreOzljede("Unesite sifru ozljede kojeg biste htjeli promjeniti");

            izraz = veza.prepareStatement("UPDATE ozljeda SET opisIncidenta = ? WHERE sifra = ?");
            izraz.setInt(2, i);
            izraz.setString(1, KontroleZaUnos.unosString("Unesite novi opis incidenta"));

            PreparedStatement izraz1 = veza.prepareStatement("UPDATE ozljeda SET opisOzljede = ? WHERE sifra = ?");
            izraz1.setInt(2, i);
            izraz1.setString(1, KontroleZaUnos.unosString("Unesite novi opis ozljede"));

            PreparedStatement izraz2 = veza.prepareStatement("UPDATE ozljeda SET igrac = ? WHERE sifra = ?");
            izraz2.setInt(2, i);
            ispisiTablicu("SELECT a.sifra, a.ime, a.prezime, a.pozicija, a.brojDresa, b.ime AS tim\n"
                    + "FROM igrac a INNER JOIN tim b ON b.sifra = a.tim;");
            izraz2.setInt(1, KontroleZaUnos.unosSifreIgraca("Unesite sifru novog igraca koji se ozlijedio"));

            z = izraz.executeUpdate() + izraz1.executeUpdate() + izraz2.executeUpdate();
            JOptionPane.showMessageDialog(null, "Uspješno promjenjeno (" + z + ")");

            System.out.println("");
            ispisiTablicu("SELECT e.sifra, e.opisIncidenta, e.opisOzljede, concat(a.ime, ' ', a.prezime) AS igrac\n"
                    + "FROM ozljeda e INNER JOIN igrac a ON a.sifra = e.igrac;");
            JOptionPane.showMessageDialog(null, "Tablica ozljeda prikazana!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
