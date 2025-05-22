package restoranpemesanan.dao;

import restoranpemesanan.koneksi.Koneksi;
import restoranpemesanan.entity.Pesanan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoPesanan {
    private Connection connection;

    public DaoPesanan() {
        try {
            connection = Koneksi.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tambahPesanan(Pesanan pesanan) throws SQLException {
        String sql = "INSERT INTO pesanan (nama_makanan, jumlah, harga) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, pesanan.getNamaMakanan());
        ps.setInt(2, pesanan.getJumlah());
        ps.setDouble(3, pesanan.getHarga());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            pesanan.setId(rs.getInt(1));
        }
        rs.close();
        ps.close();
    }

    public ArrayList<Pesanan> getAllPesanan() throws SQLException {
        ArrayList<Pesanan> listPesanan = new ArrayList<>();
        String sql = "SELECT * FROM pesanan";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Pesanan pesanan = new Pesanan(
                rs.getInt("id"),
                rs.getString("nama_makanan"),
                rs.getInt("jumlah"),
                rs.getDouble("harga")
            );
            listPesanan.add(pesanan);
        }
        rs.close();
        ps.close();
        return listPesanan;
    }

    public void updatePesanan(Pesanan pesanan) throws SQLException {
        String sql = "UPDATE pesanan SET nama_makanan = ?, jumlah = ?, harga = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, pesanan.getNamaMakanan());
        ps.setInt(2, pesanan.getJumlah());
        ps.setDouble(3, pesanan.getHarga());
        ps.setInt(4, pesanan.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void hapusPesanan(int id) throws SQLException {
        String sql = "DELETE FROM pesanan WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
}