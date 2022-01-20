package com.example.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtlapDb {
    Connection conn;

    public EtlapDb() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb","root", "");
    }
    public List<Etlap> getEtlap() throws SQLException {
        List<Etlap> etlap = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM etlap;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()){
            int id = result.getInt("id");
            String cim = result.getString("nev");
            String leiras = result.getString("leiras");
            String kategoria = result.getString("kategoria");
            int ar = result.getInt("ar");
            Etlap film = new Etlap(id, cim,leiras, kategoria, ar);
            etlap.add(film);
        }
        return etlap;
    }
    public int etlapHozzaadasa(String nev, String leiras, String kategoria, int ar) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,nev);
        stmt.setString(2,leiras);
        stmt.setInt(3,ar);
        stmt.setString(4,kategoria);
        return stmt.executeUpdate();
    }
    public boolean etelTorlese(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
    public int szazalekosEmelesMindenre(int szazalek) throws SQLException {
        String sql = "UPDATE etlap SET etlap.ar = etlap.ar * (? / 100 + 1) ";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, szazalek);
        return stmt.executeUpdate();
    }
    public int szazalekosEmelesEgyEtelre(int szazalek, int id) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * (? / 100 + 1) WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, szazalek);
        stmt.setInt(2, id);
        return stmt.executeUpdate();
    }
}
