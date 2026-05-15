package com.app.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClearClientsRunner {
    public static void main(String[] args) {
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = Files.readString(Path.of("src/main/resources/sql/clear_clients.sql"));
            String[] parts = sql.split(";");
            for (String part : parts) {
                String s = part.trim();
                if (s.isEmpty()) continue;
                boolean hasResultSet = stmt.execute(s);
                if (hasResultSet) {
                    try (ResultSet rs = stmt.getResultSet()) {
                        if (rs.next()) {
                            System.out.println("Result: " + rs.getObject(1));
                        }
                    }
                } else {
                    int update = stmt.getUpdateCount();
                    System.out.println("Executed: " + (update >= 0 ? update + " rows affected" : "OK"));
                }
            }

            System.out.println("Done.");

        } catch (IOException e) {
            System.err.println("Failed to read SQL file: " + e.getMessage());
            e.printStackTrace();
            System.exit(2);
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            e.printStackTrace();
            System.exit(3);
        }
    }
}
