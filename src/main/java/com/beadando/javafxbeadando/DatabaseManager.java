package com.beadando.javafxbeadando;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:adatok.db";
    private static Connection connection;

    public static Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
                System.out.println("Connection to SQLite has been established.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection to SQLite has been closed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<String> executeCustomQuery(String sqlQuery) {
        List<String> records = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                int toronyId = rs.getInt("id");
                int darab = rs.getInt("darab");
                int teljesitmeny = rs.getInt("teljesitmeny");
                int kezdev = rs.getInt("kezdev");
                int helyszinId = rs.getInt("helyszinid");
                String helyszinNev = rs.getString("helyszin_nev");
                int megyeid = rs.getInt("megye_id");
                String megyeNev = rs.getString("megye_nev");
                String regio = rs.getString("regio");

                String record = String.format("Torony ID: %d, Darab: %d, Teljesítmény: %d, Kezdev: %d, " +
                                "Helyszín ID: %d, Helyszín Név: %s,Megye ID: %d,Megye Név: %s, Régió: %s",
                        toronyId, darab, teljesitmeny, kezdev, helyszinId, helyszinNev ,megyeid, megyeNev, regio);

                records.add(record);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return records;
    }

    public void addHelyszin(String nev, int megyeid) {
        String sql = "INSERT INTO helyszin (nev, megyeid) VALUES (?, ?)";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, nev);
            pstmt.setInt(2, megyeid);
            pstmt.executeUpdate();
            System.out.println("Helyszín added: " + nev);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Helyszin> getAllHelyszin() {
        List<Helyszin> helyszinList = new ArrayList<>();
        String sql = "SELECT * FROM helyszin";

        try (Statement stmt = connect().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Helyszin helyszin = new Helyszin();
                helyszin.setId(rs.getInt("id"));
                helyszin.setName(rs.getString("nev"));
                helyszin.setMegyeId(rs.getInt("megyeid"));

                helyszinList.add(helyszin);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Helyszin data: " + e.getMessage());
        }

        return helyszinList;
    }

    public void updateHelyszin(int id, String nev, int megyeid) {
        String sql = "UPDATE helyszin SET nev = ?, megyeid = ? WHERE id = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, nev);
            pstmt.setInt(2, megyeid);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("Helyszin updated: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteHelyszin(int id) {
        String sql = "DELETE FROM helyszin WHERE id = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Helyszin deleted with id: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addMegye( String nev, String regio) {
        String sql = "INSERT INTO megye (nev, regio) VALUES (?, ?)";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, nev);
            pstmt.setString(2, regio);
            pstmt.executeUpdate();
            System.out.println("Megye added: " + nev);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Megye> getAllMegye() {
        List<Megye> megyeList = new ArrayList<>();
        String sql = "SELECT id, nev AS name, regio AS region FROM megye";

        try (Statement stmt = connect().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {

                Megye megye = new Megye(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("region")
                );
                megyeList.add(megye);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return megyeList;
    }

    public void updateMegye(int id, String nev, String regio) {
        String sql = "UPDATE megye SET nev = ?, regio = ? WHERE id = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, nev);
            pstmt.setString(2, regio);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("Megye updated: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<Integer> getAllMegyeIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM megye";
        try (Statement stmt = connect().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ids;
    }
    public List<Integer> getAllHelyszinIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM helyszin";
        try (Statement stmt = connect().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ids;
    }

    public void deleteMegye(int id) {
        String sql = "DELETE FROM megye WHERE id = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Megye deleted with id: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTorony(int darab, int teljesitmeny, int kezdev, int helyszinid) {
        String sql = "INSERT INTO torony (darab, teljesitmeny, kezdev, helyszinid) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, darab);
            pstmt.setInt(2, teljesitmeny);
            pstmt.setInt(3, kezdev);
            pstmt.setInt(4, helyszinid);
            pstmt.executeUpdate();
            System.out.println("Torony added with auto-incremented ID.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Torony> getAllTorony() {
        List<Torony> records = new ArrayList<>();
        String sql = "SELECT * FROM torony";
        try (Statement stmt = connect().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Torony torony = new Torony(
                        rs.getInt("id"),
                        rs.getInt("darab"),
                        rs.getInt("teljesitmeny"),
                        rs.getInt("kezdev"),
                        rs.getInt("helyszinid")
                );
                records.add(torony);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return records;
    }
    public List<Integer> getAllToronyIds() {
        List<Integer> toronyIds = new ArrayList<>();
        String sql = "SELECT id FROM torony";
        try (Statement stmt = connect().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                toronyIds.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Torony IDs: " + e.getMessage());
        }
        return toronyIds;
    }

    public void updateTorony(int id, int darab, int teljesitmeny, int kezdev, int helyszinid) {
        String sql = "UPDATE torony SET darab = ?, teljesitmeny = ?, kezdev = ?, helyszinid = ? WHERE id = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, darab);
            pstmt.setInt(2, teljesitmeny);
            pstmt.setInt(3, kezdev);
            pstmt.setInt(4, helyszinid);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            System.out.println("Torony updated: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTorony(int id) {
        String sql = "DELETE FROM torony WHERE id = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Torony deleted with id: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}