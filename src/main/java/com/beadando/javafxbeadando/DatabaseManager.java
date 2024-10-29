package com.beadando.javafxbeadando;

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

    public List<List<Object>> getAllHelyszin() {
        List<List<Object>> records = new ArrayList<>();
        String sql = "SELECT * FROM helyszin";
        try (Statement stmt = connect().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                List<Object> record = new ArrayList<>();
                record.add(rs.getInt("id"));
                record.add(rs.getString("nev"));
                record.add(rs.getInt("megyeid"));
                records.add(record);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return records;
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

    public List<List<Object>> getAllMegye() {
        List<List<Object>> records = new ArrayList<>();
        String sql = "SELECT * FROM megye";
        try (Statement stmt = connect().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                List<Object> record = new ArrayList<>();
                record.add(rs.getInt("id"));
                record.add(rs.getString("nev"));
                record.add(rs.getString("regio"));
                records.add(record);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return records;
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
    public void addTorony(int id, int darab, int teljesitmeny, int kezdev, int helyszinid) {
        String sql = "INSERT INTO torony (id, darab, teljesitmeny, kezdev, helyszinid) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, darab);
            pstmt.setInt(3, teljesitmeny);
            pstmt.setInt(4, kezdev);
            pstmt.setInt(5, helyszinid);
            pstmt.executeUpdate();
            System.out.println("Torony added: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<List<Object>> getAllTorony() {
        List<List<Object>> records = new ArrayList<>();
        String sql = "SELECT * FROM torony";
        try (Statement stmt = connect().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                List<Object> record = new ArrayList<>();
                record.add(rs.getInt("id"));
                record.add(rs.getInt("darab"));
                record.add(rs.getInt("teljesitmeny"));
                record.add(rs.getInt("kezdev"));
                record.add(rs.getInt("helyszinid"));
                records.add(record);
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
