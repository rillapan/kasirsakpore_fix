package Main;

import config.Koneksi;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Laporan_Penjualan extends javax.swing.JPanel {

    PreparedStatement ps;
    ResultSet rs;

    public Laporan_Penjualan() {
        initComponents();
        loadDataPenjualan();
    }

  private void loadDataPenjualan() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Penjualan");
    model.addColumn("Tanggal");
    model.addColumn("Kasir");
    model.addColumn("Total Pembayaran");
    model.addColumn("Keuntungan");

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = Koneksi.getConnection();
        String sql = "SELECT p.penjualan_id, p.tanggal_penjualan, u.nama_user, "
                   + "p.total_pembayaran, p.keuntungan "
                   + "FROM penjualan p "
                   + "JOIN users u ON p.user_id = u.user_id "
                   + "ORDER BY p.penjualan_id DESC";

        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("penjualan_id"),
                rs.getDate("tanggal_penjualan"),
                rs.getString("nama_user"),
                rs.getInt("total_pembayaran"),
                rs.getInt("keuntungan")
            });
        }

        tbl_penjualan.setModel(model);
        hitungTotalLaba(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal memuat data penjualan: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            // conn jangan ditutup, biar bisa dipakai ulang
        } catch (Exception ex) {
            System.out.println("Error closing: " + ex.getMessage());
        }
    }
}



    private void hitungTotalLaba(DefaultTableModel model) {
        int totalLaba = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            totalLaba += Integer.parseInt(model.getValueAt(i, 4).toString());
        }
        txt_totalLaba.setText(String.valueOf(totalLaba));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_penjualan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_totalLaba = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LAPORAN PENJUALAN");
        add(jLabel1, java.awt.BorderLayout.NORTH);

        tbl_penjualan.setModel(new javax.swing.table.DefaultTableModel());
        jScrollPane1.setViewportView(tbl_penjualan);
        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();
        panelBottom.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Total Keuntungan: ");
        txt_totalLaba.setEditable(false);
        txt_totalLaba.setColumns(10);
        panelBottom.add(jLabel2);
        panelBottom.add(txt_totalLaba);
        add(panelBottom, java.awt.BorderLayout.SOUTH);
    }

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_penjualan;
    private javax.swing.JTextField txt_totalLaba;
}
