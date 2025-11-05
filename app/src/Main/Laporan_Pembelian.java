package Main;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class Laporan_Pembelian extends javax.swing.JPanel {

    PreparedStatement ps;
    ResultSet rs;

    public Laporan_Pembelian() {
        initComponents();
        loadDataPembelian();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_pembelian = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_totalPembelian = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        tbl_pembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID Pembelian", "Tanggal Pembelian", "Nama Kasir", "Total Pembelian"}
        ));
        jScrollPane1.setViewportView(tbl_pembelian);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); 
        jLabel1.setText("LAPORAN PEMBELIAN");

        jLabel2.setText("Total Pembelian:");

        txt_totalPembelian.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 400, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_totalPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_totalPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>                        


    // ====== METHOD UNTUK LOAD DATA PEMBELIAN ======
    private void loadDataPembelian() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Pembelian");
        model.addColumn("Tanggal Pembelian");
        model.addColumn("Nama Kasir");
        model.addColumn("Total Pembelian");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Koneksi.getConnection();
            String sql = "SELECT p.pembelian_id, p.tanggal_pembelian, u.nama_user, "
                       + "p.total_pembelian "
                       + "FROM pembelian p "
                       + "JOIN users u ON p.user_id = u.user_id "
                       + "ORDER BY p.pembelian_id DESC";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("pembelian_id"),
                    rs.getDate("tanggal_pembelian"),
                    rs.getString("nama_user"),
                    rs.getInt("total_pembelian")
                });
            }

            tbl_pembelian.setModel(model);
            hitungTotalPembelian(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data pembelian: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception ex) {
                System.out.println("Error closing: " + ex.getMessage());
            }
        }
    }

    private void hitungTotalPembelian(DefaultTableModel model) {
        int total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += Integer.parseInt(model.getValueAt(i, 3).toString());
        }
        txt_totalPembelian.setText(String.valueOf(total));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_pembelian;
    private javax.swing.JTextField txt_totalPembelian;
    // End of variables declaration                   
}
