package com.example.etlap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EtlapDb {
    Connection conn;

    public EtlapDb() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/filmek","root", "");
    }
}
