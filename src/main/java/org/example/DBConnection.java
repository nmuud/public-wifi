package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/RE/aajava/zero-wifi/zerowifi.db";
    private static Connection conn = null;

    /**
     * SQLite 데이터베이스 연결 생성
     *
     * @return Connection 객체, 연결 실패 시 null 반환
     */
    public static Connection DBConnect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
