/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_sgbd;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ibrah
 */
public class new_table extends javax.swing.JFrame {

    public static Connection connection;
    
    /**
     * Creates new form new_table
     */
    public new_table(Connection connection_) {
        initComponents();
        connection = connection_;
        button_exit.setBorder(null);
        button_reduce.setBorder(null);
        button_delete_text.setBorder(null);
        button_exit.setContentAreaFilled(false);
        button_reduce.setContentAreaFilled(false);
        button_delete_text.setContentAreaFilled(false);
        
        getRootPane().setBackground(Color.WHITE);
        getContentPane().setBackground(Color.WHITE);
        getRootPane().setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, java.awt.SystemColor.textHighlight));
        
        
        this.setLocationRelativeTo(null);
        
        displayTypes();
        setTablePopMenu();
    }
    
    public void setTablePopMenu(){
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Supprimer");
        Icon icon = new ImageIcon(getClass().getResource("/icons/Delete_15px.png"));
        item.setIcon(icon);
        item.setMnemonic(KeyEvent.VK_P);
        item.getAccessibleContext().setAccessibleDescription("Supprimer");
        
        menu.add(item);
        
        table_columns.setComponentPopupMenu(menu);
                
    }
    
    public boolean inputIsValid(){
        
        if(textbox_table_name.getText().equals("")){
            return false;
        }
        for(int i=0; i<table_columns.getRowCount(); i++){
            String value = (String) table_columns.getValueAt(i, 1);
            System.out.println("-"+value+"-");
            if(value == new String() || "".equals(value) || "string".equals(value)){
                return false;
            }
        }
        return true;
    }
    
    public void createTable(String table_name){
        try{
            PreparedStatement ps;
            String query = "CREATE TABLE `"+table_name+"`(";
            for(int i=0; i<table_columns.getRowCount(); i++){
                String name = (String) table_columns.getValueAt(i, 0);
                String type = (String) table_columns.getValueAt(i, 1);
                String primary_key = (boolean) table_columns.getValueAt(i, 2) == true ? "PRIMARY KEY":"";
                String not_null = (boolean) table_columns.getValueAt(i, 3) == true ? "NOT NULL":"";
                String unique = (boolean) table_columns.getValueAt(i, 4) == true ? "UNIQUE":"";
                unique = ("PRIMARY KEY".equals(primary_key))? "":unique;
                query += "`" + name + "` " + type + " " + not_null + " " +  primary_key + " " +  unique;
                query+= (i == table_columns.getRowCount()-1)? "":",";
            }
            query+=");";
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Table créée!");
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Erreur lors de la création de la table! Message d'erreur: " + e.getMessage());
        }
        
    }
    
    public void displayTypes(){
        
        JComboBox box_types = new JComboBox();
        box_types.setEditable(true);
        box_types.addItem("INT");
        box_types.addItem("INT()");
        box_types.addItem("FLOAT");
        box_types.addItem("TEXT");
        box_types.addItem("VARCHAR()");
        box_types.addItem("BOOLEAN");
        box_types.addItem("DECIMAL");
        box_types.addItem("DATETIME");
        box_types.addItem("BLOB");
        box_types.addItem("TIME");
        
        table_columns.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(box_types));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_reduce = new javax.swing.JButton();
        button_exit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_columns = new javax.swing.JTable();
        button_new_row = new javax.swing.JButton();
        textbox_table_name = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        button_delete_text = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        setUndecorated(true);
        setResizable(false);

        button_reduce.setBackground(new java.awt.Color(255, 255, 255));
        button_reduce.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Reduce_30px.png"))); // NOI18N
        button_reduce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_reduceActionPerformed(evt);
            }
        });

        button_exit.setBackground(new java.awt.Color(255, 255, 255));
        button_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Close Window_30px.png"))); // NOI18N
        button_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_exitActionPerformed(evt);
            }
        });

        table_columns.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        table_columns.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {" ", null, null,  new Boolean(true),  new Boolean(false), null, "0"}
            },
            new String [] {
                "Nom de la colonne", "Type de données", "Primary key", "Not null", "Unique", "Auto increment", "Default Expression"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_columns.setRowHeight(20);
        jScrollPane1.setViewportView(table_columns);

        button_new_row.setBackground(java.awt.SystemColor.textHighlight);
        button_new_row.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        button_new_row.setForeground(new java.awt.Color(255, 255, 255));
        button_new_row.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Add_15px.png"))); // NOI18N
        button_new_row.setText("Nouvelle ligne");
        button_new_row.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        button_new_row.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_new_rowActionPerformed(evt);
            }
        });

        textbox_table_name.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel1.setText("Nom de la table: ");

        jButton1.setBackground(java.awt.SystemColor.textHighlight);
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Checked Checkbox_15px.png"))); // NOI18N
        jButton1.setText("Valider ");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        button_delete_text.setBackground(new java.awt.Color(255, 255, 255));
        button_delete_text.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancel_25px.png"))); // NOI18N
        button_delete_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_delete_textActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textbox_table_name, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_delete_text, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(193, 193, 193)
                        .addComponent(button_new_row))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(button_reduce, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_exit)
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button_exit)
                    .addComponent(button_reduce, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(button_new_row)
                        .addComponent(button_delete_text))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(textbox_table_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton1)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_reduceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_reduceActionPerformed
        // TODO add your handling code here:
        setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_button_reduceActionPerformed

    private void button_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_exitActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(rootPane, "Voulez-vous quitter ?");
        if(choice == 0){
            dispose();
        }
    }//GEN-LAST:event_button_exitActionPerformed

    private void button_new_rowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_new_rowActionPerformed
        // TODO add your handling code here:
        ((DefaultTableModel) table_columns.getModel()).addRow(new Object[]{"","",false,false,false,false,""});
    }//GEN-LAST:event_button_new_rowActionPerformed

    private void button_delete_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_delete_textActionPerformed
        // TODO add your handling code here:
        textbox_table_name.setText("");
    }//GEN-LAST:event_button_delete_textActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(inputIsValid()){
            createTable(textbox_table_name.getText());
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Erreur de saisie!");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(new_table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(new_table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(new_table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(new_table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new new_table(connection).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_delete_text;
    private javax.swing.JButton button_exit;
    private javax.swing.JButton button_new_row;
    private javax.swing.JButton button_reduce;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_columns;
    private javax.swing.JTextField textbox_table_name;
    // End of variables declaration//GEN-END:variables
}