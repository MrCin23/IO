package pl.lodz.p.ias.io.raportowanie.query;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class GeneralReportQuery {
    private String url;
    private String user;
    private String password;

    public GeneralReportQuery() {
        String propertiesFile = "src/main/resources/application.properties";
        String name = null;
        String lastName = null;
        // Załaduj ustawienia z pliku properties
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Failed to load properties file.");
            e.printStackTrace();
        }
        url = properties.getProperty("spring.datasource.url");
        user = properties.getProperty("spring.datasource.username");
        password = properties.getProperty("spring.datasource.password");
    }

    public Set<String> nameSurnameQuery(Long userId) {

        String query = "SELECT first_name, last_name FROM account WHERE id = ?";
        Set<String> set = new HashSet<String>();
//        String query2 = "SELECT count(*) FROM users";

        // Połączenie z bazą danych
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Ustawienie parametru ID w zapytaniu SQL
            pstmt.setLong(1, userId);

            // Wykonanie zapytania
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Pobranie wyniku z kolumny "name"
                    set.add(rs.getString("first_name"));
                    set.add(rs.getString("last_name"));
                } else {
                    System.out.println("User with ID " + userId + " not found.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    public int countUsers() {
        String query = "SELECT count(*) FROM account";
        int count = 0;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                } else {
                    System.out.println("No users found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
