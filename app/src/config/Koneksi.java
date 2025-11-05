/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class Koneksi {

    private static Connection connection;

    public static Connection getConnection() throws ClassNotFoundException {
        if (connection == null) {
            try {
                // URL koneksi PostgreSQL
                String url = "jdbc:postgresql://localhost:5432/pointofsale";
                String user = "postgres"; // ganti sesuai user PostgreSQL kamu
                String pass = "12345678";      // ganti sesuai password PostgreSQL kamu

                // Daftarkan driver PostgreSQL
                Class.forName("org.postgresql.Driver");

                // Buat koneksi
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi ke PostgreSQL berhasil!");
            } catch (SQLException ex) {
                Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Koneksi ke PostgreSQL gagal: " + ex.getMessage());
            }
        }
        return connection;
    }
}
