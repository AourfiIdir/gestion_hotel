package com.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.app.models.Hotel;

public class InitDb {
    private static final Hotel HOTEL = new Hotel("hotel1", "13 rue evezard", 5);
    private static long hotelId = -1L;

    public static void initDb() {
        try (
            Connection conn = DbConnection.getConnection();
            Statement stmt = conn.createStatement()
        ) {
            String sql = loadSchema();
            for (String query : sql.split(";")) {
                String trimmed = query.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }

            seedHotel(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Hotel getHotel() {
        return HOTEL;
    }

    public static long getHotelId() {
        return hotelId;
    }

    private static String loadSchema() throws IOException {
        InputStream inputStream = InitDb.class.getClassLoader().getResourceAsStream("sql/schema.sql");
        if (inputStream == null) {
            throw new IOException("Could not find resource: sql/schema.sql");
        }

        
        return InitDb.StringFromInputStream(inputStream);
    }
    public static String StringFromInputStream(InputStream is){
        StringBuilder str = new StringBuilder();
        try(
        BufferedReader bf = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        ){
            
            String line;
            while(( line = bf.readLine())!=null){
                str.append(line).append('\n');
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return str.toString();
        
    }

    private static void seedHotel(Connection conn) throws SQLException {
        String findHotelSql = "SELECT id FROM hotels WHERE nom = ? AND address = ? AND notation = ?";
        String insertHotelSql = "INSERT INTO hotels (nom, address, notation) VALUES (?, ?, ?)";

        try (PreparedStatement findStmt = conn.prepareStatement(findHotelSql)) {
            findStmt.setString(1, HOTEL.nom);
            findStmt.setString(2, HOTEL.address);
            findStmt.setInt(3, HOTEL.notation);

            try (ResultSet rs = findStmt.executeQuery()) {
                if (rs.next()) {
                    hotelId = rs.getLong("id");
                    return;
                }
            }
        }

        try (PreparedStatement insertStmt = conn.prepareStatement(insertHotelSql, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, HOTEL.nom);
            insertStmt.setString(2, HOTEL.address);
            insertStmt.setInt(3, HOTEL.notation);
            insertStmt.executeUpdate();

            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    hotelId = generatedKeys.getLong(1);
                    return;
                }
            }
        }

        throw new SQLException("Failed to seed hotel");
    }
}
