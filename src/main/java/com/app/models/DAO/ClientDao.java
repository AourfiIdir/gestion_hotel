package com.app.models.DAO;
import com.app.models.*;
import com.app.utils.*;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class ClientDao implements DAO<Client>{

    @Override
    public void insert(Client t) {
        String insertClientSql = "INSERT INTO clients (hotel_id, nom, prenom) VALUES (?, ?, ?)";

        try (Connection conn = DbConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(insertClientSql)) {
                stmt.setLong(1, InitDb.getHotelId());
                stmt.setString(2, t.nom);
                stmt.setString(3, t.prenom);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Client t, String[] params) {
        if (params == null || params.length < 2) {
            throw new IllegalArgumentException("update requires at least two params: newNom and newPrenom");
        }

        String updateSql = "UPDATE clients SET nom = ?, prenom = ? WHERE hotel_id = ? AND nom = ? AND prenom = ?";

        try (Connection conn = DbConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                stmt.setString(1, params[0]);
                stmt.setString(2, params[1]);
                stmt.setLong(3, InitDb.getHotelId());
                stmt.setString(4, t.nom);
                stmt.setString(5, t.prenom);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Client t) {
        String deleteSql = "DELETE FROM clients WHERE hotel_id = ? AND nom = ? AND prenom = ?";

        try (Connection conn = DbConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setLong(1, InitDb.getHotelId());
                stmt.setString(2, t.nom);
                stmt.setString(3, t.prenom);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Client> get() {
        String sql = "SELECT nom, prenom FROM clients ORDER BY id LIMIT 1";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return Optional.of(mapClient(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT nom, prenom FROM clients ORDER BY id";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clients.add(mapClient(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }

    private Client mapClient(ResultSet rs) throws SQLException {
        return new Client(rs.getString("nom"), rs.getString("prenom"), InitDb.getHotel());
    }

    /* 
    private String getNom(Client client) {
        try {
            Field field = Client.class.getDeclaredField("nom");
            field.setAccessible(true);
            return (String) field.get(client);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to read Client.nom", e);
        }
    }
        */
    
}