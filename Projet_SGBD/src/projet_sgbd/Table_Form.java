/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_sgbd;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.*;

/**
 *
 * @author Ibrah
 */
public class Table_Form extends javax.swing.JFrame {

    static String database; 
    static Connection connection;
    static String table;
    
    /**
     * Creates new form Table_Form
     * @param database_
     * @param connection_
     * @param table_
     */
    public Table_Form(String database_, Connection connection_, String table_) {
        
        database = database_;
        connection = connection_;
        table = table_;

        initComponents();
        this.setLocationRelativeTo(null);
        
        pane_elements.setBackgroundAt(1, Color.WHITE);
        pane_elements.setForegroundAt(1, Color.BLUE);
        
        button_delete_record.setContentAreaFilled(false);
        button_delete_record.setBorder(null);
        button_exit.setBorder(null);
        button_exit.setContentAreaFilled(false);
        button_reduce.setContentAreaFilled(false);
        button_reduce.setBorder(null);
        button_save.setContentAreaFilled(false);
        button_save.setBorder(null);
        pane_elements.setBorder(null);
        
      
        
        getRootPane().setBackground(Color.WHITE);
        getContentPane().setBackground(Color.WHITE);
        getRootPane().setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, java.awt.SystemColor.textHighlight));
        
        displayRecords();
        displayStructure();
        displayComparators();
        
    }

    
    public static void displayStructure(){
        try{
            Vector<Object> column_names = new Vector<Object>();
            Vector<Object> data = new Vector<Object>();
            
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery("desc " + table);
            ResultSetMetaData meta = result.getMetaData();
            int columns = meta.getColumnCount();
            
            for(int i=1; i<=columns; i++){
                column_names.addElement(meta.getColumnName(i));
            }
            
            while(result.next()){
                Vector<Object> row = new Vector<Object>();
                
                for(int i=1; i<=columns; i++){
                    row.addElement(result.getObject(i));
                }
                data.addElement(row);
               ((DefaultTableModel) table_search.getModel()).addRow(new Object[]{true,result.getString(1), result.getString(2)});
               ((DefaultTableModel) table_criteria.getModel()).addRow(new Object[]{result.getString(1), "*",""});
            }
            

           
            DefaultTableModel model = new DefaultTableModel(data,column_names);
            table_structure.setModel(model);
            result.close();
            s.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(table_records, "Erreur lors de l'obtention de la structure de la table! Message d'erreur: " + e.getMessage());
        }
    }
    
    public static void displayComparators(){
        
        JComboBox comparators = new JComboBox();
        
        comparators.addItem("*");
        comparators.addItem("=");
        comparators.addItem(">");
        comparators.addItem("<");
        comparators.addItem("Comprend");
        comparators.addItem("Commence par");
        comparators.addItem("Se termine par");
        
       table_criteria.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comparators));
        
    }
    
    public static LinkedList<String> getColumns(){
        
        LinkedList<String> columns = new LinkedList<>();
        
        if((Boolean) table_search.getValueAt(0, 0) == true){
            
            for(int i=1; i<table_search.getRowCount(); i++){
                columns.add((String) table_search.getValueAt(i, 1));
            }
        }
        else{
            for(int i=1; i<table_search.getRowCount(); i++){
                if((Boolean) table_search.getValueAt(i, 0) == true){
                    columns.add((String) table_search.getValueAt(i, 1));
                }
            }
        }
        System.out.println(columns.toString());
        return columns;
    }

    public static LinkedList<String> getCriteria(){
        
        LinkedList<String> criteria = new LinkedList<String>();
        
        for(int i=1; i < table_criteria.getRowCount(); i++){
            String comparator;
            
            switch((String) table_criteria.getValueAt(i, 1)){
                case "Comprend":
                    comparator = "%%";
                    break;
                case "Commence par":
                    comparator = "%";
                    break;
                case "Se termine par":
                    comparator = "...%";
                    break;
                default:
                    comparator = (String) table_criteria.getValueAt(i, 1);
            }
            
            switch(comparator){
                case "%%":
                    criteria.add(table_criteria.getValueAt(i, 0) + " LIKE ('%" + table_criteria.getValueAt(i, 2) + "'%)");
                    break;
                case "%":
                    criteria.add(table_criteria.getValueAt(i, 0) + " LIKE ('%" + table_criteria.getValueAt(i, 2) + "')");
                    break;
                case "...%":
                    criteria.add(table_criteria.getValueAt(i, 0) + " LIKE ('" + table_criteria.getValueAt(i, 2) + "%')");
                    break;
                case "*":
                    continue;
                default:
                    criteria.add(table_criteria.getValueAt(i, 0) + " " + table_criteria.getValueAt(i, 1) + " " + table_criteria.getValueAt(i, 2));
            }
        }
        
        System.out.println(criteria);
        return criteria;
    } 
    
    public static String createQuery(LinkedList<String> columns, LinkedList<String> criteria){
        
        String query = "SELECT ";
        
        for(int i=0; i<columns.size();i++){
            
            query+= (i != columns.size()-1)? columns.get(i)+",":columns.get(i);
        }
        
        query+=" FROM " + table;
        
        if(!criteria.isEmpty()){
            query+= " WHERE ";
            for(int i=0; i<criteria.size(); i++){
                query+= (i != criteria.size() - 1) ? criteria.get(i)+" AND ":criteria.get(i);
            }
        }
        query+= ";";
        
        System.out.println(query);
        return query;
    }
    
    public static void displayResults(String query){
        try{
            Vector<Object> column_names = new Vector<Object>();
            Vector<Object> data = new Vector<Object>();
            
            Statement s = connection.createStatement();
            ResultSet result = s.executeQuery(query);
            ResultSetMetaData meta = result.getMetaData();
            int column_count = meta.getColumnCount();
            
            for(int i=1; i<=column_count; i++){
                column_names.add(meta.getColumnName(i));
            }
            
            while(result.next()){
                
                Vector<Object> row = new Vector<Object>();
                
                for(int i=1; i<=column_count; i++){
                    row.add(result.getObject(i));
                }
                data.addElement(row);
            }
            
            DefaultTableModel model = new DefaultTableModel(data, column_names);
            results f = new results(connection, model);
            f.setVisible(true);
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(tab_records, "Erreur lors de la collecte des données! Message d'erreur: " + e.getMessage());
        }
    }
    
    /*
    Returns the table records
        Params: none 
        Return values : none
    */
    public static void displayRecords(){
        try{
            Vector<Object> column_names = new Vector<Object>();
            Vector<Object> data = new Vector<Object>();
            
            Statement s = connection.createStatement(); //Statement creation
            ResultSet result = s.executeQuery("select * from " + table);
            ResultSetMetaData meta = result.getMetaData();
            int columns = meta.getColumnCount(); //Get total columns count
            
            for(int i=1; i<=columns; i++){
                column_names.addElement(meta.getColumnName(i)); //Get each column name fetched
            }
            
            while(result.next()){
                Vector<Object> row = new Vector<Object>(columns);
                
                for(int i=1; i<=columns; i++){
                row.addElement(result.getObject(i)); //The object represents the row raw data
                }
                data.addElement(row); //Add row fetched to the data vector
            }
            
            DefaultTableModel model = new DefaultTableModel(data,column_names);
            table_records.setModel(model); //Set the new table model
            result.close();
            s.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(table_records, "Erreur lors de la collecte des enregistrements! Message d'erreur: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pane_elements = new javax.swing.JTabbedPane();
        tab_records = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_records = new javax.swing.JTable();
        button_new_record = new javax.swing.JButton();
        button_delete_record = new javax.swing.JButton();
        button_save = new javax.swing.JButton();
        tab_structure = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_structure = new javax.swing.JTable();
        tab_settings = new javax.swing.JToolBar();
        tab_search = new javax.swing.JToolBar();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_search = new javax.swing.JTable();
        button_search = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_criteria = new javax.swing.JTable();
        button_reduce = new javax.swing.JButton();
        button_exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("pane"); // NOI18N
        setUndecorated(true);

        pane_elements.setBackground(new java.awt.Color(255, 255, 255));
        pane_elements.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pane_elements.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        pane_elements.setOpaque(true);

        tab_records.setBackground(new java.awt.Color(255, 255, 255));
        tab_records.setBorder(null);
        tab_records.setFloatable(false);
        tab_records.setRollover(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        table_records.setAutoCreateRowSorter(true);
        table_records.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        table_records.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        table_records.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table_records);

        button_new_record.setBackground(java.awt.SystemColor.textHighlight);
        button_new_record.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        button_new_record.setForeground(new java.awt.Color(255, 255, 255));
        button_new_record.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Add_15px.png"))); // NOI18N
        button_new_record.setText("Nouvel enregistrement");
        button_new_record.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        button_new_record.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        button_delete_record.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Trash_30px_blue.png"))); // NOI18N
        button_delete_record.setBorder(null);
        button_delete_record.setBorderPainted(false);

        button_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Save_30px_blue.png"))); // NOI18N
        button_save.setBorder(null);
        button_save.setBorderPainted(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(button_new_record)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(button_delete_record, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(button_save, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(115, Short.MAX_VALUE)
                .addComponent(button_new_record)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button_delete_record, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_save, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tab_records.add(jPanel1);

        pane_elements.addTab("Enregistrements", new javax.swing.ImageIcon(getClass().getResource("/icons/Records_15px.png")), tab_records); // NOI18N

        tab_structure.setBackground(new java.awt.Color(255, 255, 255));
        tab_structure.setRollover(true);

        table_structure.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        table_structure.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(table_structure);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
        );

        tab_structure.add(jPanel2);

        pane_elements.addTab("Structure", new javax.swing.ImageIcon(getClass().getResource("/icons/Structure_15px.png")), tab_structure); // NOI18N

        tab_settings.setBackground(new java.awt.Color(255, 255, 255));
        tab_settings.setRollover(true);
        pane_elements.addTab("Paramètres", new javax.swing.ImageIcon(getClass().getResource("/icons/SettingsBlue_15px.png")), tab_settings); // NOI18N

        tab_search.setRollover(true);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel1.setText("Choix des colonnes: ");

        table_search.setAutoCreateRowSorter(true);
        table_search.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        table_search.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Boolean(true), "Toutes", null}
            },
            new String [] {
                "Choix", "Colonne", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_search.setRowHeight(20);
        jScrollPane3.setViewportView(table_search);
        if (table_search.getColumnModel().getColumnCount() > 0) {
            table_search.getColumnModel().getColumn(0).setMinWidth(40);
            table_search.getColumnModel().getColumn(0).setPreferredWidth(40);
            table_search.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        button_search.setBackground(java.awt.SystemColor.textHighlight);
        button_search.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        button_search.setForeground(new java.awt.Color(255, 255, 255));
        button_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search_15px_WHITE.png"))); // NOI18N
        button_search.setText("Rechercher");
        button_search.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        button_search.setIconTextGap(10);
        button_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_searchActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel2.setText("Critères: ");

        table_criteria.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        table_criteria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"N'importe laquelle", "*", null}
            },
            new String [] {
                "Colonne", "Comparateur", "Valeur"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_criteria.setRowHeight(20);
        jScrollPane4.setViewportView(table_criteria);
        if (table_criteria.getColumnModel().getColumnCount() > 0) {
            table_criteria.getColumnModel().getColumn(1).setMinWidth(95);
            table_criteria.getColumnModel().getColumn(1).setPreferredWidth(95);
            table_criteria.getColumnModel().getColumn(1).setMaxWidth(95);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(button_search, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(button_search)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab_search.add(jPanel3);

        pane_elements.addTab("Recherche", new javax.swing.ImageIcon(getClass().getResource("/icons/Search_15px.png")), tab_search); // NOI18N

        button_reduce.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Reduce_30px.png"))); // NOI18N
        button_reduce.setAlignmentY(0.0F);
        button_reduce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_reduceActionPerformed(evt);
            }
        });

        button_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Close Window_30px.png"))); // NOI18N
        button_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pane_elements, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button_reduce, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_exit)))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_exit)
                    .addComponent(button_reduce, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pane_elements, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_reduceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_reduceActionPerformed
        // TODO add your handling code here:
        this.setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_button_reduceActionPerformed

    private void button_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_exitActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(rootPane, "Voulez-vous vraiment quitter ?");
        if(choice == 0){
            dispose();
        }
    }//GEN-LAST:event_button_exitActionPerformed

    private void button_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_searchActionPerformed
        // TODO add your handling code here:
        LinkedList<String> columns = getColumns();
        LinkedList<String> criteria = getCriteria();
        String query = createQuery(columns, criteria);
        displayResults(query);
    }//GEN-LAST:event_button_searchActionPerformed

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
            java.util.logging.Logger.getLogger(Table_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Table_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Table_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Table_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Table_Form(database,connection, table).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_delete_record;
    private javax.swing.JButton button_exit;
    private javax.swing.JButton button_new_record;
    private javax.swing.JButton button_reduce;
    private javax.swing.JButton button_save;
    private javax.swing.JButton button_search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane pane_elements;
    private static javax.swing.JToolBar tab_records;
    private javax.swing.JToolBar tab_search;
    private javax.swing.JToolBar tab_settings;
    private javax.swing.JToolBar tab_structure;
    private static javax.swing.JTable table_criteria;
    private static javax.swing.JTable table_records;
    private static javax.swing.JTable table_search;
    private static javax.swing.JTable table_structure;
    // End of variables declaration//GEN-END:variables
}
