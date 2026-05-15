package com.app.models.DAO;

import com.app.models.*;
import com.app.utils.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Vector;

public class ReservationDao implements DAO<Reservation> {

    @Override
    public void insert(Reservation r) {
        String insertSql = "INSERT INTO reservations (debut, fin, client_id, chambre_id, reserve_status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection()) {
            Long clientId = findClientId(conn, r.client);
            Long chambreId = findChambreId(conn, r.chambre);
            /* 
            if (clientId == null || chambreId == null) {
                throw new IllegalStateException("Client or Chambre not found in database");
            }
            */
           if(clientId == null){
                throw new IllegalStateException("Client not found in database");
           }else if(chambreId == null){
                throw new IllegalStateException("Chambre not found in database");
           }else if(clientId == null || chambreId == null) {
                throw new IllegalStateException("Client or Chambre not found in database");
            }
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setDate(1, Date.valueOf(r.debut));
                stmt.setDate(2, Date.valueOf(r.fin));
                stmt.setLong(3, clientId);
                stmt.setLong(4, chambreId);
                stmt.setString(5, r.reservé);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Reservation t, String[] params) {
        if (params == null || params.length == 0) {
            throw new IllegalArgumentException("update requires at least one parameter (new reserve_status or new dates)");
        }

        try (Connection conn = DbConnection.getConnection()) {
            Long clientId = findClientId(conn, t.client);
            Long chambreId = findChambreId(conn, t.chambre);

            if (clientId == null || chambreId == null) {
                throw new IllegalStateException("Client or Chambre not found in database");
            }

            if (params.length == 1) {
                String updateSql = "UPDATE reservations SET reserve_status = ? WHERE debut = ? AND fin = ? AND client_id = ? AND chambre_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setString(1, params[0]);
                    stmt.setDate(2, Date.valueOf(t.debut));
                    stmt.setDate(3, Date.valueOf(t.fin));
                    stmt.setLong(4, clientId);
                    stmt.setLong(5, chambreId);
                    stmt.executeUpdate();
                }
            } else {
                String updateSql = "UPDATE reservations SET debut = ?, fin = ?, reserve_status = ? WHERE debut = ? AND fin = ? AND client_id = ? AND chambre_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setDate(1, Date.valueOf(params[0]));
                    stmt.setDate(2, Date.valueOf(params[1]));
                    stmt.setString(3, params.length >= 3 ? params[2] : t.reservé);
                    stmt.setDate(4, Date.valueOf(t.debut));
                    stmt.setDate(5, Date.valueOf(t.fin));
                    stmt.setLong(6, clientId);
                    stmt.setLong(7, chambreId);
                    stmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Reservation t) {
        String deleteSql = "DELETE FROM reservations WHERE debut = ? AND fin = ? AND client_id = ? AND chambre_id = ?";

        try (Connection conn = DbConnection.getConnection()) {
            Long clientId = findClientId(conn, t.client);
            Long chambreId = findChambreId(conn, t.chambre);

            if (clientId == null || chambreId == null) {
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setDate(1, Date.valueOf(t.debut));
                stmt.setDate(2, Date.valueOf(t.fin));
                stmt.setLong(3, clientId);
                stmt.setLong(4, chambreId);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Reservation> get(String s) {
        String sql = "SELECT r.debut, r.fin, r.reserve_status, c.nom, c.prenom, ch.etage, ch.type, ch.prix, ch.num "
                + "FROM reservations r "
                + "JOIN clients c ON r.client_id = c.id "
                + "JOIN chambres ch ON r.chambre_id = ch.id "
                + "WHERE c.nom = ? ORDER BY r.id LIMIT 1";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, s);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapReservation(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Vector<Reservation> getAll() {
        Vector<Reservation> res = new Vector<>();
        String sql = "SELECT r.debut, r.fin, r.reserve_status, c.nom, c.prenom, ch.etage, ch.type, ch.prix, ch.num "
                + "FROM reservations r "
                + "JOIN clients c ON r.client_id = c.id "
                + "JOIN chambres ch ON r.chambre_id = ch.id ORDER BY r.id";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                res.add(mapReservation(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    private Reservation mapReservation(ResultSet rs) throws SQLException {
        java.time.LocalDate debut = rs.getDate("debut").toLocalDate();
        java.time.LocalDate fin = rs.getDate("fin").toLocalDate();

        Client client = new Client(rs.getString("nom"), rs.getString("prenom"), InitDb.getHotel());
        Chambre chambre = new Chambre(rs.getInt("etage"), rs.getString("type"), rs.getDouble("prix"), rs.getInt("num"), InitDb.getHotel());

        Reservation reservation = new Reservation(debut, fin, chambre, client);
        reservation.reservé = rs.getString("reserve_status");
        return reservation;
    }

    private Long findClientId(Connection conn, Client client) throws SQLException {
        String sql = "SELECT id FROM clients WHERE hotel_id = ? AND nom = ? AND prenom = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, InitDb.getHotelId());
            stmt.setString(2, client.nom);
            stmt.setString(3, client.prenom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getLong("id");
            }
        }
        return null;
    }

    private Long findChambreId(Connection conn, Chambre chambre) throws SQLException {
        String sql = "SELECT id FROM chambres WHERE hotel_id = ? AND num = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, InitDb.getHotelId());
            stmt.setInt(2, chambre.num);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getLong("id");
            }
        }
        return null;
    }

}