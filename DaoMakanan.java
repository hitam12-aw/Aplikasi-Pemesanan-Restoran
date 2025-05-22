package restoranpemesanan.dao;

import restoranpemesanan.koneksi.Koneksi;
import restoranpemesanan.entity.Makanan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoMakanan {
    private Connection connection;

    public DaoMakanan() {
        try {
            connection = Koneksi.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tambahMakanan(Makanan makanan) throws SQLException {
        String sql = "INSERT INTO makanan (nama, harga) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, makanan.getNama());
        ps.setDouble(2, makanan.getHarga());
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<Makanan> getAllMakanan() throws SQLException {
        ArrayList<Makanan> listMakanan = new ArrayList<>();
        String sql = "SELECT * FROM makanan";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Makanan makanan = new Makanan(
                rs.getInt("id"),
                rs.getString("nama"),
                rs.getDouble("harga")
            );
            listMakanan.add(makanan);
        }
        rs.close();
        ps.close();
        return listMakanan;
    }

    public void updateMakanan(Makanan makanan) throws SQLException {
        String sql = "UPDATE makanan SET nama = ?, harga = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, makanan.getNama());
        ps.setDouble(2, makanan.getHarga());
        ps.setInt(3, makanan.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void hapusMakanan(int id) throws SQLException {
        String sql = "DELETE FROM makanan WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
}