package com.beadando.javafxbeadando;

import javafx.collections.FXCollections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:adatok.db";
    private static Connection connection;

    // Establish a connection to the database
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

    // Close the database connection
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
                // Extract data from ResultSet using the correct column names
                int toronyId = rs.getInt("id"); // Extracting the torony ID (assuming it's the first column)
                int darab = rs.getInt("darab"); // Extracting the number of towers
                int teljesitmeny = rs.getInt("teljesitmeny"); // Extracting the power of the tower
                int kezdev = rs.getInt("kezdev"); // Extracting the year of installation
                int helyszinId = rs.getInt("helyszinid"); // Assuming helyszinid is in the torony table
                String helyszinNev = rs.getString("helyszin_nev"); // Extracting the location name (from alias)

                String megyeNev = rs.getString("megye_nev"); // Extracting the county name (from alias)
                String regio = rs.getString("regio"); // Extracting the region (from alias)

                // Create a concatenated string for each record
                String record = String.format("Torony ID: %d, Darab: %d, Teljesítmény: %d, Kezdev: %d, " +
                                "Helyszín ID: %d, Helyszín Név: %s,Megye Név: %s, Régió: %s",
                        toronyId, darab, teljesitmeny, kezdev, helyszinId, helyszinNev , megyeNev, regio);

                // Add the record to the list
                records.add(record);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return records;
    }



    // Helyszin CRUD Methods
    public void addHelyszin(int id, String nev, int megyeid) {
        String sql = "INSERT INTO helyszin (id, nev, megyeid) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nev);
            pstmt.setInt(3, megyeid);
            pstmt.executeUpdate();
            System.out.println("Helyszin added: " + nev);
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
                // Create a new Helyszin object for each row
                Helyszin helyszin = new Helyszin();
                helyszin.setId(rs.getInt("id"));
                helyszin.setName(rs.getString("nev"));
                helyszin.setMegyeId(rs.getInt("megyeid"));

                // Add the Helyszin object to the list
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
            System.out.println("Helyszin updated: " + nev);
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

    // Megye CRUD Methods
    public void addMegye(int id, String nev, String regio) {
        String sql = "INSERT INTO megye (id, nev, regio) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nev);
            pstmt.setString(3, regio);
            pstmt.executeUpdate();
            System.out.println("Megye added: " + nev);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Megye> getAllMegye() {
        List<Megye> megyeList = new ArrayList<>();
        String sql = "SELECT id, nev AS name, regio AS region FROM megye"; // Adjusted the SQL to fetch specific fields

        try (Statement stmt = connect().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Create a new Megye object for each row
                Megye megye = new Megye(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("region")
                );
                megyeList.add(megye); // Add the object to the list
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return megyeList; // Return the list of Megye objects
    }

    public void updateMegye(int id, String nev, String regio) {
        String sql = "UPDATE megye SET nev = ?, regio = ? WHERE id = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, nev);
            pstmt.setString(2, regio);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("Megye updated: " + nev);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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

    // Torony CRUD Methods
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
