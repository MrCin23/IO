package pl.lodz.p.ias.io.raportowanie.query;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class SummaryReportQuery {
    private String url;
    private String user;
    private String password;

    public SummaryReportQuery() {
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

    public String resourcesQuery() {
        String query = "SELECT resource_name, resource_type, resource_quantity FROM resource";
        String result = "[Resource name, resource type, resource quantity]\n";

        // Połączenie z bazą danych
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Wykonanie zapytania
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result += rs.getString("resource_name") + ", " + rs.getString("resource_type") + ", " + rs.getInt("resource_quantity") + "\n";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String warehousesQuery() {
        String query = "SELECT warehouse_name, location FROM warehouse";
        String result = "[Warehouse name, location]\n";

        // Połączenie z bazą danych
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Wykonanie zapytania
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result += rs.getString("warehouse_name") + ", " + rs.getString("location") + "\n";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String productsQuery() {
        String query = "SELECT product_symbol, description, price FROM product";
        String result = "[Product symbol, description, price]\n";

        // Połączenie z bazą danych
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Wykonanie zapytania
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result += rs.getString("product_symbol") + ", " + rs.getString("description") + ", " + rs.getDouble("price") + "\n";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String rolesQuery() {
        String query = "SELECT id, role_name FROM role";
        String result = "[ID, role name]\n";

        // Połączenie z bazą danych
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Wykonanie zapytania
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result += rs.getInt("id") + ", " + rs.getString("role_name") + "\n";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String volunteerGroupsQuery() {
        String query = "SELECT id, name FROM volunteer_group";
        String result = "[ID, name]\n";

        // Połączenie z bazą danych
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Wykonanie zapytania
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result += rs.getInt("id") + ", " + rs.getString("name") + "\n";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
