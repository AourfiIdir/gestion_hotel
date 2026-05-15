package com.app.models.DAO;
import com.app.models.*;
import com.app.utils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Optional;
import java.util.Vector;


public class ChambreDao implements DAO<Chambre>{

	@Override
	public void insert(Chambre t) {
		String insertChambreSql = "MERGE INTO chambres (hotel_id, num, etage, type, prix) KEY (hotel_id, num) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DbConnection.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(insertChambreSql)) {
				stmt.setLong(1, InitDb.getHotelId());
				stmt.setInt(2, t.num);
				stmt.setInt(3, t.etage);
				stmt.setString(4, t.type);
				stmt.setDouble(5, t.prix);
				stmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Chambre t, String[] params) {
		if (params == null || params.length < 4) {
			throw new IllegalArgumentException("update requires four params: etage, type, prix, num");
		}

		String updateSql = "UPDATE chambres SET etage = ?, type = ?, prix = ?, num = ? WHERE hotel_id = ? AND num = ?";

		try (Connection conn = DbConnection.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
				stmt.setInt(1, Integer.parseInt(params[0]));
				stmt.setString(2, params[1]);
				stmt.setDouble(3, Double.parseDouble(params[2]));
				stmt.setInt(4, Integer.parseInt(params[3]));
				stmt.setLong(5, InitDb.getHotelId());
				stmt.setInt(6, t.num);
				stmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Chambre t) {
		String deleteSql = "DELETE FROM chambres WHERE hotel_id = ? AND num = ?";

		try (Connection conn = DbConnection.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
				stmt.setLong(1, InitDb.getHotelId());
				stmt.setInt(2, t.num);
				stmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Optional<Chambre> get(String s) {
		String sql = "SELECT etage, type, prix, num FROM chambres WHERE hotel_id = ? AND num = ? ORDER BY id LIMIT 1";

		try (Connection conn = DbConnection.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)){
			 stmt.setLong(1, InitDb.getHotelId());
			 stmt.setInt(2, Integer.parseInt(s));
			 ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return Optional.of(mapChambre(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}

	@Override
	public Vector<Chambre> getAll() {
		Vector<Chambre> chambres = new Vector<>();
		String sql = "SELECT etage, type, prix, num FROM chambres WHERE hotel_id = ? ORDER BY id";

		try (Connection conn = DbConnection.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, InitDb.getHotelId());
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					chambres.add(mapChambre(rs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chambres;
	}

	private Chambre mapChambre(ResultSet rs) throws SQLException {
		return new Chambre(rs.getInt("etage"), rs.getString("type"), rs.getDouble("prix"), rs.getInt("num"), InitDb.getHotel(), false);
	}
}
