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
}
